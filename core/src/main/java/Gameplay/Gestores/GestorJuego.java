package Gameplay.Gestores;

import Fisicas.Borde;
import Fisicas.Camara;
import Fisicas.Fisica;
import Fisicas.Mapa;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.principal.Jugador;
import entidades.personajes.Personaje;
import entradas.ControlesJugador;
import hud.Hud;
import managers.ScreenManager;
import screens.GameOverScreen;
import utils.RecursosGlobales;

import java.util.ArrayList;
import java.util.List;

public class GestorJuego {

    private List<Jugador> jugadores = new ArrayList<>();
    private GestorTurno gestorTurno;
    private GestorColisiones gestorColisiones;
    private GestorProyectiles gestorProyectiles;
    private GestorEntidades gestorEntidades;
    private Borde borde;

    public GestorJuego(List<Jugador> jugadores, GestorColisiones gestorColisiones, GestorProyectiles gestorProyectiles, Borde borde) {
        this.jugadores.addAll(jugadores);
        this.gestorColisiones = gestorColisiones;
        this.gestorProyectiles = gestorProyectiles;
        this.borde = borde;

        this.gestorTurno = new GestorTurno(new ArrayList<>(this.jugadores));

        this.gestorEntidades = new GestorEntidades();

        for (Jugador jugadorAux : this.jugadores) {
            for (Personaje personajeAux : jugadorAux.getPersonajes()) {
                this.gestorColisiones.agregarObjeto(personajeAux);
                this.gestorEntidades.agregarEntidad(personajeAux);
            }
        }
    }

    public void actualizar(float delta, Fisica fisica, Mapa mapa) {
        gestorTurno.correrContador(delta);
        revisarPersonajesMuertos();
        gestorEntidades.actualizar(delta, fisica, mapa);
        gestorProyectiles.actualizar(delta, mapa);
    }

    public void procesarEntradaJugador(ControlesJugador control, float delta) {
        if (control == null || getJugadorActivo() == null) return;

        control.procesarEntrada();
        Personaje activo = getPersonajeActivo();

        if (activo != null) {
            if (control.getX() != 0 || control.getY() != 0) {
                activo.mover(control.getX(), delta);
                activo.ocultarMirilla();
            }

            if (control.getSaltar()) {
                activo.saltar();
            }
            if (control.getApuntarDir() != 0) {
                activo.apuntar(control.getApuntarDir());
            }
            if (control.getMovimientoSeleccionado() >= 0) {
                activo.usarMovimiento(control.getMovimientoSeleccionado());
                control.resetMovimientoSeleccionado();
            }
        }
    }

    public void renderPersonajes(Hud hud) {
        for (Jugador jugadorAux : jugadores) {
            for (Personaje personajeAux : jugadorAux.getPersonajes()) {
                personajeAux.render(RecursosGlobales.batch);
                hud.mostrarVida(personajeAux);
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

    public void renderEntidades(SpriteBatch batch) { gestorEntidades.render(batch); }

    public void renderDebugEntidades(ShapeRenderer sr, Camara camara) { gestorEntidades.renderDebug(sr, camara); }

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
    public GestorColisiones getGestorColisiones() { return this.gestorColisiones; }
    public void renderProyectiles(SpriteBatch batch) { gestorProyectiles.render(batch); }
    public Borde getMapa() { return borde; }
}
