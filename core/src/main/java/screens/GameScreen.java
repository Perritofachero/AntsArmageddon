package screens;

import Fisicas.Camara;
import Fisicas.Mapa;
import Gameplay.Gestores.GestorColisiones;
import Gameplay.Gestores.GestorJuego;
import Gameplay.Gestores.GestorProyectiles;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.principal.Jugador;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.principal.AntsArmageddon;
import entidades.personajes.HormigaObrera;
import entidades.personajes.Personaje;
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

    private GestorJuego gestorJuego;
    private List<ControlesJugador> controles = new ArrayList<>();
    private int turnoAnterior = -1;

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

        spriteMapa = new Sprite(GestorAssets.get(Constantes.FONDO_JUEGO_PRUEBA, Texture.class));
        spriteMapa.setPosition(0, 0);

        camaraPersonaje = new Camara(
            Constantes.RESOLUCION_ANCHO,
            Constantes.RESOLUCION_ALTO,
            Constantes.RESOLUCION_ANCHO_MAPA,
            Constantes.RESOLUCION_ALTO_MAPA
        );

        GestorColisiones gestorColisiones = new GestorColisiones();
        GestorProyectiles gestorProyectiles = new GestorProyectiles(gestorColisiones);
        Mapa mapa = new Mapa(gestorColisiones);

        Jugador jugador1 = new Jugador(new ArrayList<>());
        jugador1.agregarPersonaje(new HormigaObrera(gestorColisiones, gestorProyectiles, 200, 200));
        jugador1.agregarPersonaje(new HormigaObrera(gestorColisiones, gestorProyectiles, 250, 200));

        Jugador jugador2 = new Jugador(new ArrayList<>());
        jugador2.agregarPersonaje(new HormigaObrera(gestorColisiones, gestorProyectiles, 400, 350));
        jugador2.agregarPersonaje(new HormigaObrera(gestorColisiones, gestorProyectiles, 450, 350));

        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador1);
        jugadores.add(jugador2);

        ControlesJugador control1 = new ControlesJugador();
        ControlesJugador control2 = new ControlesJugador();
        jugador1.setControlesJugador(control1);
        jugador2.setControlesJugador(control2);
        controles.add(control1);
        controles.add(control2);

        gestorJuego = new GestorJuego(jugadores, gestorColisiones, gestorProyectiles, mapa);

        int turnoInicial = gestorJuego.getTurnoActual();
        controles.get(turnoInicial).setPersonaje(gestorJuego.getPersonajeActivo());
        Gdx.input.setInputProcessor(controles.get(turnoInicial));
        turnoAnterior = turnoInicial;
    }

    @Override
    public void render(float delta) {
        gestorJuego.actualizar(delta);
        procesarEntradaJugador(delta);

        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Personaje activo = gestorJuego.getPersonajeActivo();
        if (activo != null) {
            camaraPersonaje.seguirPersonaje(activo);
            camaraPersonaje.getCamera().update();
        }

        batch.setProjectionMatrix(camaraPersonaje.getCamera().combined);
        batch.begin();
        spriteMapa.draw(batch);

        gestorJuego.renderPersonajes(batch, hud);
        gestorJuego.renderProyectiles(batch);

        hud.mostrarContador(batch, gestorJuego.getTiempoActual(), camaraPersonaje);
        batch.end();

        gestorJuego.getMapa().draw(shapeRenderer, camaraPersonaje);
        escenario.act(delta);
        escenario.draw();

        actualizarTurno();
    }

    private void actualizarTurno() {
        int turnoActual = gestorJuego.getTurnoActual();
        if (turnoActual != turnoAnterior) {
            ControlesJugador control = controles.get(turnoActual);
            control.setPersonaje(gestorJuego.getPersonajeActivo());
            Gdx.input.setInputProcessor(control);

            turnoAnterior = turnoActual;
        }
    }

    private void procesarEntradaJugador(float delta) {
        ControlesJugador control = controles.get(gestorJuego.getTurnoActual());
        gestorJuego.procesarEntradaJugador(control, delta);
    }

    @Override
    public void resize(int width, int height) {
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
        for (Jugador j : gestorJuego.getJugadores()) {
            j.getPersonajes().forEach(Personaje::dispose);
        }
    }
}

