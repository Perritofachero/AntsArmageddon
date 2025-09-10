package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.principal.Jugador;
import entidades.Personaje;
import entidades.Proyectil;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.principal.AntsArmageddon;
import entradas.ControlesJugador;
import logica.Camara;
import logica.GestorDeColisiones;
import hud.Hud;
import logica.GestorProyectiles;
import logica.GestorTurno;
import utils.Constantes;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {

    private final AntsArmageddon juego;
    private AssetManager assetManager;

    private Stage escenario;
    private FitViewport viewport;
    private SpriteBatch batch;
    private Hud hud;

    private Sprite spriteMapa;

    private Camara camaraPersonaje;
    private GestorDeColisiones gestorColisiones;
    private GestorTurno gestorTurno;
    private GestorProyectiles gestorProyectiles;

    private List<Jugador> jugadores = new ArrayList<>();
    private List<ControlesJugador> controles = new ArrayList<>();

    public GameScreen(AntsArmageddon juego, AssetManager assetManager){
        this.juego = juego;
        this.assetManager = assetManager;
    }

    @Override
    public void show() {
        viewport = new FitViewport(Constantes.RESOLUCION_ANCHO, Constantes.RESOLUCION_ALTO);
        escenario = new Stage(viewport);
        batch = new SpriteBatch();
        hud = new Hud();

        spriteMapa = new Sprite(assetManager.get(Constantes.FONDO_JUEGO_PRUEBA, Texture.class));
        spriteMapa.setPosition(0, 0);

        // Cámara
        camaraPersonaje = new Camara(
            Constantes.RESOLUCION_ANCHO,
            Constantes.RESOLUCION_ALTO,
            spriteMapa.getWidth(),
            spriteMapa.getHeight()
        );

        viewport = new FitViewport(Constantes.RESOLUCION_ANCHO, Constantes.RESOLUCION_ALTO, camaraPersonaje.getCamera());
        escenario = new Stage(viewport);

        crearJugadoresYControles();

        gestorProyectiles = new GestorProyectiles(gestorColisiones);
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

        if (controlActivo.getProyectilDisparado()) {
            Proyectil proyectil = personajeActivo.atacar();
            gestorProyectiles.agregar(proyectil);
            controlActivo.setProyectilDisparado(false);
        }

        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camaraPersonaje.seguirPersonaje(personajeActivo);
        batch.setProjectionMatrix(camaraPersonaje.getCamera().combined);

        batch.begin();

        spriteMapa.draw(batch);

        for (Jugador jugador : jugadores) {
            for (Personaje personaje : jugador.getPersonajes()) {
                personaje.render(batch);
                hud.mostrarVida(batch, personaje);
            }
        }

        gestorProyectiles.actualizar(delta);
        gestorProyectiles.render(batch);

        hud.mostrarContador(batch, gestorTurno.getTiempoActual());

        batch.end();

        escenario.act(delta);
        escenario.draw();
    }

    private void crearJugadoresYControles() {
        gestorColisiones = new GestorDeColisiones();

        Jugador jugador1 = new Jugador(new ArrayList<>());
        jugador1.getPersonajes().add(new Personaje("prueba.png", gestorColisiones, 550, 350));

        Jugador jugador2 = new Jugador(new ArrayList<>());
        jugador2.getPersonajes().add(new Personaje("hormiga.png", gestorColisiones, 50, 350));

        jugadores.add(jugador1);
        jugadores.add(jugador2);

        controles.add(new ControlesJugador(jugador1.getPersonajeActivo()));
        controles.add(new ControlesJugador(jugador2.getPersonajeActivo()));

        for (Jugador jugador : jugadores) {
            for (Personaje personaje : jugador.getPersonajes()) {
                gestorColisiones.agregarObjeto(personaje);
            }
        }

        gestorTurno = new GestorTurno((ArrayList<Jugador>) jugadores);
    }



    @Override
    public void resize(int ancho, int alto) { viewport.update(ancho, alto, true); }

    @Override
    public void dispose() { escenario.dispose(); batch.dispose(); }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

}
