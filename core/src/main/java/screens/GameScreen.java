package screens;

import Habilidades.Proyectil;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.principal.AntsArmageddon;
import entidades.Personaje;
import utils.ControlesPersonaje;
import utils.GestorDeColisiones;
import utils.Hud;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private final AntsArmageddon juego;

    private Stage escenario;
    private FitViewport viewport;
    private OrthographicCamera camara;

    private SpriteBatch batch;
    private Hud hud;

    private Personaje jugador1, jugador2;
    private ControlesPersonaje control1, control2;

    private boolean turno = true;
    private final int TIEMPO_TURNO = 7;
    private int contador = TIEMPO_TURNO;
    private float tiempoAcumulado = 0f;

    ArrayList<Proyectil> proyectiles;
    GestorDeColisiones gestor = new GestorDeColisiones();

    public GameScreen(AntsArmageddon juego){
        this.juego = juego;
    }

    @Override
    public void show() {
        camara = new OrthographicCamera();
        viewport = new FitViewport(800, 400, camara);
        escenario = new Stage(viewport);

        jugador1 = new Personaje("Buddy.png", gestor, 50, 80, 50, false);
        jugador2 = new Personaje("hormiga.png", gestor, 350, 80, 50, true);

        control1 = new ControlesPersonaje(jugador1);
        control2 = new ControlesPersonaje(jugador2);

        proyectiles = new ArrayList<>();

        gestor.agregarObjeto(jugador1);
        gestor.agregarObjeto(jugador2);

        if(turno){
            Gdx.input.setInputProcessor(control1);
        }else{
            Gdx.input.setInputProcessor(control2);
        }

        hud = new Hud();
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {

        if (turno) {
            jugador1.mover(control1.getX(), control1.getY(), delta);

            if(control1.getProyectilDisparado()){
                Proyectil proyectil = jugador1.atacar();
                proyectiles.add(proyectil);
                gestor.agregarObjeto(proyectil);
                control1.setProyectilDisparado(false);
            }
        } else {
            jugador2.mover(control2.getX(), control2.getY(), delta);

            if(control2.getProyectilDisparado()){
                Proyectil proyectil = jugador2.atacar();
                proyectiles.add(proyectil);
                gestor.agregarObjeto(proyectil);

                control2.setProyectilDisparado(false);
            }
        }

        tiempoAcumulado += delta;
        if (tiempoAcumulado >= 1f) {
            contador--;
            if (contador <= 0) {
                contador = TIEMPO_TURNO;
                turno = !turno;

                if(turno){
                    Gdx.input.setInputProcessor(control1);
                    jugador2.getMirilla().ocultarMirilla();
                }else{
                    Gdx.input.setInputProcessor(control2);
                    jugador1.getMirilla().ocultarMirilla();
                }
            }
            tiempoAcumulado = 0f;
        }

        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        jugador1.render(batch);
        hud.mostrarVida(batch, jugador1);
        jugador2.render(batch);
        hud.mostrarVida(batch, jugador2);
        hud.mostrarContador(batch, contador);

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

    @Override
    public void resize(int ancho, int alto) {
        viewport.update(ancho, alto, true);
    }

    @Override
    public void dispose() {
        escenario.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

}
