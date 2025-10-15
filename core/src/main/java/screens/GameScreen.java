package screens;

import Fisicas.Borde;
import Fisicas.Fisica;
import Fisicas.Mapa;
import Gameplay.Gestores.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.principal.Jugador;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.principal.AntsArmageddon;
import entidades.personajes.HormigaExploradora;
import entidades.personajes.HormigaGuerrera;
import entidades.personajes.HormigaObrera;
import entidades.personajes.Personaje;
import entidades.personajes.PowerUps.CajaVida;
import entidades.personajes.PowerUps.PowerUp;
import entradas.ControlesJugador;
import hud.Hud;
import managers.GestorAssets;
import utils.Constantes;
import utils.RecursosGlobales;
import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {

    private AntsArmageddon juego;
    private GestorSpawn gestorSpawn;
    private Stage escenario;
    private Hud hud;
    private Sprite spriteMapa;
    private Mapa mapa;

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
        hud = new Hud();

        spriteMapa = new Sprite(GestorAssets.get(Constantes.FONDO_JUEGO, Texture.class));
        spriteMapa.setPosition(0, 0);

        mapa = new Mapa(Constantes.MAPA_4);
        gestorSpawn = new GestorSpawn(mapa);
        gestorSpawn.precalcularPuntosValidos(16f, 16f);

        GestorColisiones gestorColisiones = new GestorColisiones(mapa);
        Fisica fisica = new Fisica();
        GestorFisica gestorFisica = new GestorFisica(fisica, gestorColisiones);
        GestorProyectiles gestorProyectiles = new GestorProyectiles(gestorColisiones, gestorFisica);
        Borde borde = new Borde(gestorColisiones);

        // Generar spawns válidos para los personajes
        List<Vector2> spawns = gestorSpawn.generarVariosSpawnsPersonajes(4, 16f, 16f, 60f);

        Jugador jugador1 = new Jugador(new ArrayList<>());
        Jugador jugador2 = new Jugador(new ArrayList<>());

        jugador1.agregarPersonaje(new HormigaExploradora(gestorColisiones, gestorProyectiles,
            spawns.get(0).x, spawns.get(0).y));
        jugador1.agregarPersonaje(new HormigaGuerrera(gestorColisiones, gestorProyectiles,
            spawns.get(1).x, spawns.get(1).y));

        jugador2.agregarPersonaje(new HormigaObrera(gestorColisiones, gestorProyectiles,
            spawns.get(2).x, spawns.get(2).y));
        jugador2.agregarPersonaje(new HormigaObrera(gestorColisiones, gestorProyectiles,
            spawns.get(3).x, spawns.get(3).y));

        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador1);
        jugadores.add(jugador2);

        ControlesJugador control1 = new ControlesJugador();
        ControlesJugador control2 = new ControlesJugador();
        jugador1.setControlesJugador(control1);
        jugador2.setControlesJugador(control2);
        controles.add(control1);
        controles.add(control2);

        gestorJuego = new GestorJuego(jugadores, gestorColisiones, gestorProyectiles, gestorSpawn, fisica);

        PowerUp cajaVida = new CajaVida(600, 500, gestorColisiones);
        gestorJuego.agregarEntidad(cajaVida);

        // Spawn de PowerUp (cae desde arriba)
        /*Vector2 spawnPower = gestorSpawn.generarSpawnPowerUp(8f);
        if (spawnPower != null) {
            PowerUp cajaVida = new CajaVida(spawnPower.x, spawnPower.y, gestorColisiones);
            gestorJuego.agregarEntidad(cajaVida);
        }*/

        int turnoInicial = gestorJuego.getTurnoActual();
        Gdx.input.setInputProcessor(controles.get(turnoInicial));
        turnoAnterior = turnoInicial;
    }


    @Override
    public void render(float delta) {
        gestorJuego.actualizar(delta, mapa);
        procesarEntradaJugador(delta);

        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Personaje activo = gestorJuego.getPersonajeActivo();
        if (activo != null) {
            RecursosGlobales.camaraPersonaje.seguirPersonaje(activo);
            RecursosGlobales.camaraPersonaje.getCamera().update();
        }

        RecursosGlobales.batch.setProjectionMatrix(RecursosGlobales.camaraPersonaje.getCamera().combined);
        RecursosGlobales.batch.begin();
        RecursosGlobales.batch.enableBlending();

        spriteMapa.draw(RecursosGlobales.batch);
        mapa.render();

        gestorJuego.renderEntidades(RecursosGlobales.batch);
        gestorJuego.renderPersonajes(hud);
        gestorJuego.renderProyectiles(RecursosGlobales.batch);

        hud.mostrarContador(gestorJuego.getTiempoActual(), RecursosGlobales.camaraPersonaje);

        RecursosGlobales.batch.end();

        gestorJuego.renderDebug(RecursosGlobales.shapeRenderer, RecursosGlobales.camaraPersonaje);

        escenario.act(delta);
        escenario.draw();

        actualizarTurno();
    }

    private void actualizarTurno() {
        int turnoActual = gestorJuego.getTurnoActual();
        if (turnoActual != turnoAnterior && turnoActual >= 0 && turnoActual < controles.size()) {
            Gdx.input.setInputProcessor(controles.get(turnoActual));
            turnoAnterior = turnoActual;
        }
    }

    private void procesarEntradaJugador(float delta) {
        ControlesJugador control = controles.get(gestorJuego.getTurnoActual());
        gestorJuego.procesarEntradaJugador(control, delta);
    }

    @Override
    public void resize(int width, int height) {
        RecursosGlobales.camaraPersonaje.getViewport().update(width, height, true);
        escenario.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        escenario.dispose();
        hud.dispose();
        spriteMapa.getTexture().dispose();
        for (Jugador j : gestorJuego.getJugadores()) {
            j.getPersonajes().forEach(Personaje::dispose);
        }
    }
}
