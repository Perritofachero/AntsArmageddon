package screens;

import Fisicas.Camara;
import Gameplay.Gestores.GestorColisiones;
import Gameplay.Gestores.GestorProyectiles;
import Gameplay.Gestores.GestorTurno;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.principal.Jugador;
import entidades.Personaje;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.principal.AntsArmageddon;
import entidades.Roca;
import entradas.ControlesJugador;
import hud.Hud;
import utils.Constantes;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {

    private AntsArmageddon juego;
    private AssetManager assetManager;

    private Stage escenario;
    private SpriteBatch batch;
    private Hud hud;
    private Sprite spriteMapa;
    private Camara camaraPersonaje;

    private GestorColisiones gestorColisiones;
    private GestorProyectiles gestorProyectiles;
    private GestorTurno gestorTurno;

    private List<Jugador> jugadores = new ArrayList<>();
    private List<ControlesJugador> controles = new ArrayList<>();

    public GameScreen(AntsArmageddon juego, AssetManager assetManager) {
        this.juego = juego;
        this.assetManager = assetManager;
    }

    @Override
    public void show() {
        FitViewport viewport = new FitViewport(Constantes.RESOLUCION_ANCHO, Constantes.RESOLUCION_ALTO);
        escenario = new Stage(viewport);
        batch = new SpriteBatch();
        hud = new Hud();

        spriteMapa = new Sprite(assetManager.get(Constantes.FONDO_JUEGO_PRUEBA, Texture.class));
        spriteMapa.setPosition(0, 0);

        camaraPersonaje = new Camara(
            Constantes.RESOLUCION_ANCHO,
            Constantes.RESOLUCION_ALTO,
            spriteMapa.getWidth(),
            spriteMapa.getHeight()
        );

        gestorColisiones = new GestorColisiones();
        gestorProyectiles = new GestorProyectiles(gestorColisiones);

        Jugador jugador1 = new Jugador(new ArrayList<>());
        jugador1.getPersonajes().add(new Personaje("prueba.png", gestorColisiones, gestorProyectiles, 300, 100));

        Jugador jugador2 = new Jugador(new ArrayList<>());
        jugador2.getPersonajes().add(new Personaje("hormiga.png", gestorColisiones, gestorProyectiles, 50, 350));

        jugadores.add(jugador1);
        jugadores.add(jugador2);

        // --- Crear controles ---
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
        gestorTurno.correrContador(delta);
        Jugador jugadorActivo = gestorTurno.getJugadorActivo();
        Personaje personajeActivo = jugadorActivo.getPersonajeActivo();
        ControlesJugador controlActivo = controles.get(gestorTurno.getTurnoActual());

        Gdx.input.setInputProcessor(controlActivo);
        controlActivo.procesarEntrada();

        personajeActivo.mover(controlActivo.getX(), controlActivo.getY(), delta);

        if (controlActivo.getMovimientoSeleccionado() >= 0) {
            personajeActivo.usarMovimiento(controlActivo.getMovimientoSeleccionado());
            controlActivo.resetMovimientoSeleccionado();
        }

        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camaraPersonaje.seguirPersonaje(personajeActivo);
        camaraPersonaje.getCamera().update();
        batch.setProjectionMatrix(camaraPersonaje.getCamera().combined);

        batch.begin();
        spriteMapa.draw(batch);

        for (Jugador j : jugadores) {
            for (Personaje p : j.getPersonajes()) {
                p.render(batch);
                hud.mostrarVida(batch, p);
            }
        }

        gestorProyectiles.actualizar(delta);
        gestorProyectiles.render(batch);

        hud.mostrarContador(batch, gestorTurno.getTiempoActual());
        batch.end();

        escenario.act(delta);
        escenario.draw();
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

