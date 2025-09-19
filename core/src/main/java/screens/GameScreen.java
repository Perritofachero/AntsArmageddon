package screens;

import Fisicas.Camara;
import Fisicas.Mapa;
import Gameplay.Gestores.GestorColisiones;
import Gameplay.Gestores.GestorProyectiles;
import Gameplay.Gestores.GestorTurno;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.principal.Jugador;
import entidades.Personaje;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.principal.AntsArmageddon;
import entidades.Proyectil;
import entidades.Roca;
import entradas.ControlesJugador;
import hud.Hud;
import managers.GestorAssets;
import utils.Constantes;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {

    private AntsArmageddon juego;

    private Stage escenario;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Hud hud;
    private Sprite spriteMapa;
    private Camara camaraPersonaje;

    private GestorColisiones gestorColisiones;
    private GestorProyectiles gestorProyectiles;
    private GestorTurno gestorTurno;
    private Mapa mapa;

    private List<Jugador> jugadores = new ArrayList<>();
    private List<ControlesJugador> controles = new ArrayList<>();

    public GameScreen(AntsArmageddon juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        FitViewport viewport = new FitViewport(Constantes.RESOLUCION_ANCHO, Constantes.RESOLUCION_ALTO);
        escenario = new Stage(viewport);
        batch = new SpriteBatch();
        hud = new Hud();
        shapeRenderer = new ShapeRenderer();

        gestorColisiones = new GestorColisiones();
        gestorProyectiles = new GestorProyectiles(gestorColisiones);

        mapa = new Mapa(gestorColisiones);

        spriteMapa = new Sprite(GestorAssets.get(Constantes.FONDO_JUEGO_PRUEBA, Texture.class));
        spriteMapa.setPosition(0, 0);

        camaraPersonaje = new Camara(
            Constantes.RESOLUCION_ANCHO,
            Constantes.RESOLUCION_ALTO,
            Constantes.RESOLUCION_ANCHO_MAPA,
            Constantes.RESOLUCION_ALTO_MAPA
        );

        Jugador jugador1 = new Jugador(new ArrayList<>());
        jugador1.getPersonajes().add(new Personaje(GestorAssets.get("prueba.png", Texture.class), gestorColisiones, gestorProyectiles, 200, 200));

        Jugador jugador2 = new Jugador(new ArrayList<>());
        jugador2.getPersonajes().add(new Personaje(GestorAssets.get("hormiga.png", Texture.class), gestorColisiones, gestorProyectiles, 400, 350));

        jugadores.add(jugador1);
        jugadores.add(jugador2);

        controles.add(new ControlesJugador(jugador1.getPersonajeActivo()));
        controles.add(new ControlesJugador(jugador2.getPersonajeActivo()));

        for (Jugador j : jugadores) {
            for (Personaje p : j.getPersonajes()) {
                gestorColisiones.agregarObjeto(p);
            }
        }

        gestorTurno = new GestorTurno((ArrayList<Jugador>) jugadores);
    }

    @Override
    public void render(float delta) {
        actualizarTurno(delta);
        procesarEntradaJugador();
        actualizarPersonajeActivo(delta);

        limpiarPantalla();
        actualizarCamara();

        batch.setProjectionMatrix(camaraPersonaje.getCamera().combined);
        batch.begin();

        dibujarMapa();
        dibujarPersonajes();
        actualizarYDibujarProyectiles(delta);
        hud.mostrarContador(batch, gestorTurno.getTiempoActual(), camaraPersonaje);
        batch.end();

        mapa.draw(shapeRenderer, camaraPersonaje);

        escenario.act(delta);
        escenario.draw();
    }

    private void actualizarTurno(float delta) {
        gestorTurno.correrContador(delta);
    }

    private void procesarEntradaJugador() {
        ControlesJugador controlActivo = controles.get(gestorTurno.getTurnoActual());
        Gdx.input.setInputProcessor(controlActivo);
        controlActivo.procesarEntrada();

        Personaje personajeActivo = gestorTurno.getJugadorActivo().getPersonajeActivo();
        if (controlActivo.getMovimientoSeleccionado() >= 0) {
            personajeActivo.usarMovimiento(controlActivo.getMovimientoSeleccionado());
            controlActivo.resetMovimientoSeleccionado();
        }
    }

    private void actualizarPersonajeActivo(float delta) {
        Personaje personajeActivo = gestorTurno.getJugadorActivo().getPersonajeActivo();
        ControlesJugador controlActivo = controles.get(gestorTurno.getTurnoActual());
        personajeActivo.mover(controlActivo.getX(), controlActivo.getY(), delta);
    }

    private void limpiarPantalla() {
        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void actualizarCamara() {
        camaraPersonaje.seguirPersonaje(gestorTurno.getJugadorActivo().getPersonajeActivo());
        camaraPersonaje.getCamera().update();
    }

    private void dibujarMapa() {
        spriteMapa.draw(batch);
    }

    private void dibujarPersonajes() {
        for (Jugador j : jugadores) {
            for (Personaje p : j.getPersonajes()) {
                p.render(batch);
                hud.mostrarVida(batch, p);
            }
        }
    }

    private void actualizarYDibujarProyectiles(float delta) {
        gestorProyectiles.actualizar(delta);
        gestorProyectiles.render(batch);
    }

    @Override public void resize(int width, int height) {
        camaraPersonaje.getViewport().update(width, height, true);
        escenario.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        escenario.dispose();
        hud.dispose();
        spriteMapa.getTexture().dispose();
        gestorProyectiles.dispose();
        for (Jugador j : jugadores) {
            for (Personaje p : j.getPersonajes()) {
                p.dispose();
            }
        }
    }
}

