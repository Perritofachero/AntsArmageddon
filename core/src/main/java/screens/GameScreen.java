package screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.principal.Jugador;
import entidades.Proyectil;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.principal.AntsArmageddon;
import entidades.Personaje;
import entradas.ControlesJugador;
import logica.GestorDeColisiones;
import hud.Hud;
import logica.GestorTurno;
import utils.Constantes;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private final AntsArmageddon juego;

    private Stage escenario;
    private FitViewport viewport;
    private OrthographicCamera camara;
    private SpriteBatch batch;
    private Hud hud;

    private Texture texturaMapa;
    private Sprite spriteMapa;

    private ArrayList<Jugador> jugadores = new ArrayList<>();
    private ArrayList<ControlesJugador> controles = new ArrayList<>();
    private GestorDeColisiones gestorColisiones = new GestorDeColisiones();

    private ArrayList<Proyectil> proyectiles;

    private GestorTurno gestorTurno;

    private AssetManager assetManager;

    /*
    Sprites y logica de dibujo de balas y personaje tienen que estar en sus clases particulares.
    Por ahora lo dejamos aca para ir probandolos.
     */
    public GameScreen(AntsArmageddon juego, AssetManager assetManager){
        this.juego = juego;
        this.assetManager = assetManager;

    }

    @Override
    public void show() {
        camara = new OrthographicCamera();
        viewport = new FitViewport(Constantes.RESOLUCION_ANCHO, Constantes.RESOLUCION_ALTO, camara);
        escenario = new Stage(viewport);

        texturaMapa = assetManager.get(Constantes.FONDO_GAMEPLAY, Texture.class);
        spriteMapa = new Sprite(texturaMapa);

        crearJugadoresYControles();

        proyectiles = new ArrayList<>();

        hud = new Hud();
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {

        gestorTurno.correrContador(delta);

        Jugador jugadorActivo = gestorTurno.getJugadorActivo();
        Personaje personajeActivo = jugadorActivo.getPersonajeActivo();

        int turno = gestorTurno.getTurnoActual();

        ControlesJugador controlActivo = controles.get(turno);
        Gdx.input.setInputProcessor(controlActivo);
        controlActivo.procesarEntrada();
        personajeActivo.mover(controlActivo.getX(), controlActivo.getY(), delta);

        if(controlActivo.getProyectilDisparado()){
            Proyectil proyectilDisparado = personajeActivo.atacar();
            proyectiles.add(proyectilDisparado);
            gestorColisiones.agregarObjeto(proyectilDisparado);
            controlActivo.setProyectilDisparado(false);
        }

        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        spriteMapa.draw(batch);

        for(int x = 0; x < jugadores.size(); x++){
            Jugador jugadorAux = jugadores.get(x);
            for(int y = 0; y < jugadorAux.getPersonajes().size(); y++){
                Personaje personajeAux = jugadorAux.getPersonajes().get(y);
                personajeAux.render(batch);
                hud.mostrarVida(batch, personajeAux);
            }
        }

        hud.mostrarContador(batch, gestorTurno.getTiempoActual());

        for(int i = 0; i < proyectiles.size(); i++){
            Proyectil proyectil = proyectiles.get(i);
            proyectil.moverProyectil(delta);
            if(proyectil.getActivo()){
                proyectil.render(batch);
            }
        }
        batch.end();

        escenario.act(delta);
        escenario.draw();
    }

    public void crearJugadoresYControles(){

        gestorColisiones = new GestorDeColisiones();

        ArrayList<Personaje> personajesJugador1 = new ArrayList<>();
        personajesJugador1.add(new Personaje("buddy.png", gestorColisiones, 550, 350));

        ArrayList<Personaje> personajesJugador2 = new ArrayList<>();
        personajesJugador2.add(new Personaje("hormiga.png", gestorColisiones, 50, 350));

        Jugador jugador1 = new Jugador(personajesJugador1);
        Jugador jugador2 = new Jugador(personajesJugador2);

        jugadores.add(jugador1);
        jugadores.add(jugador2);

        ControlesJugador control1 = new ControlesJugador(jugador1.getPersonajeActivo());
        ControlesJugador control2 = new ControlesJugador(jugador2.getPersonajeActivo());

        controles.add(control1);
        controles.add(control2);

        for(int x = 0; x < jugadores.size(); x++){
            Jugador jugadorAux = jugadores.get(x);
            for(int y = 0; y < jugadorAux.getPersonajes().size(); y++){
                gestorColisiones.agregarObjeto(jugadorAux.getPersonajeIndice(y));
            }
        }

        gestorTurno = new GestorTurno(jugadores);
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
