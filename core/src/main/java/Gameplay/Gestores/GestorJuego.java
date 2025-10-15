package Gameplay.Gestores;

import Fisicas.Camara;
import Fisicas.Fisica;
import Fisicas.Mapa;
import Gameplay.Movimientos.Movimiento;
import Gameplay.Movimientos.MovimientoMelee;
import Gameplay.Movimientos.MovimientoRango;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.principal.Jugador;
import entidades.Entidad;
import entidades.personajes.BarraCarga;
import entidades.personajes.Personaje;
import entidades.personajes.PowerUps.CajaVida;
import entidades.personajes.PowerUps.PowerUp;
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
    private GestorSpawn gestorSpawn;

    private int turnoAnterior = -1;

    public GestorJuego(List<Jugador> jugadores, GestorColisiones gestorColisiones,
                       GestorProyectiles gestorProyectiles, GestorSpawn gestorSpawn, Fisica fisica) {
        this.jugadores.addAll(jugadores);
        this.gestorColisiones = gestorColisiones;
        this.gestorProyectiles = gestorProyectiles;

        this.gestorTurno = new GestorTurno(new ArrayList<>(this.jugadores));

        this.gestorFisica = new GestorFisica(fisica, gestorColisiones);
        this.gestorEntidades = new GestorEntidades(gestorFisica, gestorColisiones);
        this.gestorSpawn = gestorSpawn;

        for (Jugador jugador : this.jugadores) {
            for (Personaje personaje : jugador.getPersonajes()) {
                this.gestorEntidades.agregarEntidad(personaje);
            }
        }

    }

    public void actualizar(float delta, Mapa mapa) {
        gestorTurno.correrContador(delta);
        revisarPersonajesMuertos();

        gestorEntidades.actualizar(delta);
        gestorProyectiles.actualizar(delta);

        int turnoActual = gestorTurno.getTurnoActual();
        if (turnoActual != turnoAnterior) {
            turnoAnterior = turnoActual;
            generarPowerUp();
        }
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

    private void generarPowerUp() {
        Vector2 spawnPower = gestorSpawn.generarSpawnPowerUp(8f);
        if (spawnPower != null) {
            PowerUp nuevoPower = new CajaVida(spawnPower.x, spawnPower.y, gestorColisiones);
            agregarEntidad(nuevoPower);
            System.out.println("PowerUp generado en: " + spawnPower);
        }
    }

    public void renderDebug(ShapeRenderer shapeRenderer, Camara camara) {
        gestorEntidades.renderDebug(shapeRenderer, camara);
    }

    public void renderEntidades(SpriteBatch batch) { gestorEntidades.render(batch); }
    public void renderProyectiles(SpriteBatch batch) { gestorProyectiles.render(batch); }

    public void renderPersonajes(Hud hud) {
        for (Jugador jugador : jugadores) {
            for (Personaje personaje : jugador.getPersonajes()) {
                personaje.render(RecursosGlobales.batch);
                hud.mostrarVida(personaje);
            }
        }
    }

    public Personaje getPersonajeActivo() {
        Jugador activo = getJugadorActivo();
        if (activo != null && !activo.getPersonajes().isEmpty()) return activo.getPersonajeActivo();
        return null;
    }

    public void agregarEntidad(Entidad entidad) {
        gestorEntidades.agregarEntidad(entidad);
    }

    public Jugador getJugadorActivo() { return gestorTurno.getJugadorActivo(); }
    public int getTurnoActual() { return gestorTurno.getTurnoActual(); }
    public float getTiempoActual() { return gestorTurno.getTiempoActual(); }
    public List<Jugador> getJugadores() { return jugadores; }
    public GestorColisiones getGestorColisiones() { return gestorColisiones; }
}

