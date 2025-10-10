package Gameplay.Gestores;

import Fisicas.Borde;
import Fisicas.Camara;
import Fisicas.Fisica;
import Fisicas.Mapa;
import Gameplay.Movimientos.Movimiento;
import Gameplay.Movimientos.MovimientoMelee;
import Gameplay.Movimientos.MovimientoRango;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.principal.Jugador;
import entidades.Entidad;
import entidades.personajes.BarraCarga;
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
    private GestorFisica gestorFisica;
    private Fisica fisica;
    private Borde borde;

    public GestorJuego(List<Jugador> jugadores, GestorColisiones gestorColisiones,
                       GestorProyectiles gestorProyectiles, Borde borde, Fisica fisica) {
        this.jugadores.addAll(jugadores);
        this.gestorColisiones = gestorColisiones;
        this.gestorProyectiles = gestorProyectiles;
        this.fisica = fisica;
        this.borde = borde;

        this.gestorTurno = new GestorTurno(new ArrayList<>(this.jugadores));
        this.gestorEntidades = new GestorEntidades();

        this.gestorFisica = new GestorFisica(fisica, gestorColisiones);

        for (Jugador jugadorAux : this.jugadores) {
            for (Personaje personajeAux : jugadorAux.getPersonajes()) {
                this.gestorColisiones.agregarObjeto(personajeAux);
                this.gestorEntidades.agregarEntidad(personajeAux);
            }
        }
    }

    public void actualizar(float delta, Mapa mapa) {
        gestorTurno.correrContador(delta);
        revisarPersonajesMuertos();

        for (Entidad entidad : gestorEntidades.getEntidades()) {
            if (entidad instanceof Personaje personaje) {
                gestorFisica.aplicarFisica(personaje, delta);
            }
            entidad.actualizar(delta);
        }

        gestorProyectiles.actualizar(delta);
    }

    public void procesarEntradaJugador(ControlesJugador control, float delta) {
        if (control == null) return;

        Personaje activo = getPersonajeActivo();
        if (activo == null) return;

        control.procesarEntrada();

        float x = control.getX();
        float y = control.getY();
        if (x != 0 || y != 0) {
            activo.mover(x, y, delta);
            activo.ocultarMirilla();
        }

        if (control.getSaltar()) activo.saltar();
        if (control.getApuntarDir() != 0) activo.apuntar(control.getApuntarDir());

        activo.setMovimientoSeleccionado(control.getMovimientoSeleccionado());
        Movimiento movimientoActual = activo.getMovimientoSeleccionado();
        BarraCarga barra = activo.getBarraCarga();

        if (movimientoActual instanceof MovimientoRango) {
            //System.out.print("Ejecutando un movimiento rango");
            if (barra != null) {
                if (control.getDisparoPresionado()) barra.start();
                if (control.getDisparoLiberado()) {
                    activo.usarMovimiento();
                    barra.reset();
                    control.resetDisparoLiberado();
                }
                barra.update(delta);
            }
        }

        else if (movimientoActual instanceof MovimientoMelee) {
            if (control.getDisparoLiberado()) {
                activo.usarMovimiento();
                control.resetDisparoLiberado();
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
                if (!personaje.getActivo()) muertos.add(personaje);
            }
            for (Personaje personaje : muertos) jugador.removerPersonaje(personaje);
            if (!jugador.estaVivo()) jugadoresSinPersonajes.add(jugador);
        }
        jugadores.removeAll(jugadoresSinPersonajes);

        if (jugadores.size() <= 1) {
            ScreenManager.setScreen(new GameOverScreen(ScreenManager.returnJuego()));
        }
    }

    public void renderEntidades(SpriteBatch batch) { gestorEntidades.render(batch); }
    public void renderDebugEntidades(ShapeRenderer sr, Camara camara) { gestorEntidades.renderDebug(sr, camara); }
    public void renderProyectiles(SpriteBatch batch) { gestorProyectiles.render(batch); }

    public Personaje getPersonajeActivo() {
        Jugador activo = getJugadorActivo();
        if (activo != null && !activo.getPersonajes().isEmpty()) return activo.getPersonajeActivo();
        return null;
    }

    public Jugador getJugadorActivo() { return gestorTurno.getJugadorActivo(); }
    public int getTurnoActual() { return gestorTurno.getTurnoActual(); }
    public float getTiempoActual() { return gestorTurno.getTiempoActual(); }
    public List<Jugador> getJugadores() { return jugadores; }
    public GestorProyectiles getGestorProyectiles() { return gestorProyectiles; }
    public GestorColisiones getGestorColisiones() { return gestorColisiones; }
    public Borde getMapa() { return borde; }
}

