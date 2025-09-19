package Gameplay.Gestores;

import Fisicas.Mapa;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.principal.Jugador;
import entidades.personajes.Personaje;
import entradas.ControlesJugador;
import hud.Hud;
import managers.ScreenManager;
import screens.GameOverScreen;

import java.util.ArrayList;
import java.util.List;

public class GestorJuego {

    private List<Jugador> jugadores = new ArrayList<>();
    private GestorTurno gestorTurno;
    private GestorColisiones gestorColisiones;
    private GestorProyectiles gestorProyectiles;
    private Mapa mapa;

    public GestorJuego(List<Jugador> jugadores, GestorColisiones gestorColisiones, GestorProyectiles gestorProyectiles, Mapa mapa) {
        this.jugadores.addAll(jugadores);
        this.gestorColisiones = gestorColisiones;
        this.gestorProyectiles = gestorProyectiles;
        this.mapa = mapa;

        this.gestorTurno = new GestorTurno(new ArrayList<>(this.jugadores));

        for (Jugador j : this.jugadores) {
            for (Personaje p : j.getPersonajes()) {
                this.gestorColisiones.agregarObjeto(p);
            }
        }
    }

    public void actualizar(float delta) {
        gestorTurno.correrContador(delta);
        revisarPersonajesMuertos();
        gestorProyectiles.actualizar(delta);
    }

    public void procesarEntradaJugador(ControlesJugador control, float delta) {
        if (control == null || getJugadorActivo() == null) return;

        control.procesarEntrada();
        Personaje activo = getPersonajeActivo();

        if (activo != null) {
            activo.mover(control.getX(), control.getY(), delta);

            if (control.getMovimientoSeleccionado() >= 0) {
                activo.usarMovimiento(control.getMovimientoSeleccionado());
                control.resetMovimientoSeleccionado();
            }
        }
    }

    public void renderPersonajes(SpriteBatch batch, Hud hud) {
        for (Jugador j : jugadores) {
            for (Personaje p : j.getPersonajes()) {
                p.render(batch);
                hud.mostrarVida(batch, p);
            }
        }
    }

    private void revisarPersonajesMuertos() {
        List<Jugador> jugadoresSinPersonajes = new ArrayList<>();

        for (Jugador jugador : jugadores) {
            List<Personaje> muertos = new ArrayList<>();
            for (Personaje personaje : jugador.getPersonajes()) {
                if (!personaje.getActivo()) { muertos.add(personaje); }
            }

            for (Personaje personaje : muertos) {
                jugador.removerPersonaje(personaje);
            }

            if (!jugador.estaVivo()) {
                jugadoresSinPersonajes.add(jugador);
            }
        }

        jugadores.removeAll(jugadoresSinPersonajes);

        if (jugadores.size() <= 1) { ScreenManager.setScreen(new GameOverScreen(ScreenManager.returnJuego())); }
    }

    public Personaje getPersonajeActivo() {
        Jugador activo = getJugadorActivo();
        if (activo != null && !activo.getPersonajes().isEmpty()) {
            return activo.getPersonajeActivo();
        }
        return null;
    }

    public Jugador getJugadorActivo() { return gestorTurno.getJugadorActivo(); }
    public int getTurnoActual() { return gestorTurno.getTurnoActual(); }
    public float getTiempoActual() { return gestorTurno.getTiempoActual(); }
    public List<Jugador> getJugadores() { return jugadores; }
    public GestorProyectiles getGestorProyectiles() { return gestorProyectiles; }
    public void renderProyectiles(SpriteBatch batch) { gestorProyectiles.render(batch); }
    public Mapa getMapa() { return mapa; }
}
