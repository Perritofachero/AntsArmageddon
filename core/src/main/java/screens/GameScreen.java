package screens;

import Fisicas.Borde;
import Fisicas.Fisica;
import Fisicas.Mapa;
import Gameplay.Gestores.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.principal.Jugador;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.principal.AntsArmageddon;
import entidades.personajes.tiposPersonajes.HormigaExploradora;
import entidades.personajes.tiposPersonajes.HormigaGuerrera;
import entidades.personajes.tiposPersonajes.HormigaObrera;
import entidades.personajes.Personaje;
import entidades.PowerUps.CajaVida;
import entidades.PowerUps.PowerUp;
import entradas.ControlesJugador;
import hud.Hud;
import managers.GestorAssets;
import managers.ScreenManager;
import partida.ConfiguracionPartida;
import utils.Constantes;
import utils.RecursosGlobales;
import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {

    private AntsArmageddon juego;
    private ConfiguracionPartida configuracion;

    private GestorSpawn gestorSpawn;
    private Stage escenario;
    private Hud hud;
    private Sprite spriteMapa;
    private Mapa mapa;

    private GestorJuego gestorJuego;

    private List<ControlesJugador> controles = new ArrayList<>();
    private int turnoAnterior = -1;

    public GameScreen(AntsArmageddon juego, ConfiguracionPartida configuracion) {
        this.juego = juego;
        this.configuracion = configuracion;
    }

    @Override
    public void show() {
        FitViewport viewport = new FitViewport(Constantes.RESOLUCION_ANCHO, Constantes.RESOLUCION_ALTO);
        escenario = new Stage(viewport);
        hud = new Hud();

        int indiceMapa = configuracion.getIndiceMapa();
        String mapaPath = switch (indiceMapa) {
            case 0 -> Constantes.MAPA_1;
            case 1 -> Constantes.MAPA_2;
            case 2 -> Constantes.MAPA_3;
            case 3 -> Constantes.MAPA_4;
            default -> Constantes.MAPA_1;
        };

        spriteMapa = new Sprite(GestorAssets.get(Constantes.FONDO_JUEGO, Texture.class));

        mapa = new Mapa(mapaPath);
        gestorSpawn = new GestorSpawn(mapa);
        gestorSpawn.precalcularPuntosValidos(16f, 16f);

        GestorColisiones gestorColisiones = new GestorColisiones(mapa);
        Fisica fisica = new Fisica();
        GestorFisica gestorFisica = new GestorFisica(fisica, gestorColisiones);
        GestorProyectiles gestorProyectiles = new GestorProyectiles(gestorColisiones, gestorFisica);
        Borde borde = new Borde(gestorColisiones);

        int totalHormigas = Math.max(
            configuracion.getEquipoJugador1().size(),
            configuracion.getEquipoJugador2().size()
        );
        List<Vector2> spawns = gestorSpawn.generarVariosSpawnsPersonajes(totalHormigas * 2, 16f, 16f, 60f);

        Jugador jugador1 = crearJugadorDesdeConfig(configuracion.getEquipoJugador1(), spawns.subList(0, totalHormigas), gestorColisiones, gestorProyectiles);
        Jugador jugador2 = crearJugadorDesdeConfig(configuracion.getEquipoJugador2(), spawns.subList(totalHormigas, totalHormigas * 2), gestorColisiones, gestorProyectiles);

        List<Jugador> jugadores = List.of(jugador1, jugador2);

        ControlesJugador control1 = new ControlesJugador();
        ControlesJugador control2 = new ControlesJugador();
        jugador1.setControlesJugador(control1);
        jugador2.setControlesJugador(control2);
        controles.add(control1);
        controles.add(control2);

        gestorJuego = new GestorJuego(jugadores, gestorColisiones, gestorProyectiles, gestorSpawn, fisica,
            configuracion.getTiempoTurno(), configuracion.getFrecuenciaPowerUps()
        );

        int turnoInicial = gestorJuego.getTurnoActual();
        Gdx.input.setInputProcessor(controles.get(turnoInicial));
        turnoAnterior = turnoInicial;
    }

    private Jugador crearJugadorDesdeConfig(
        List<String> nombresHormigas,
        List<Vector2> posiciones,
        GestorColisiones gestorColisiones,
        GestorProyectiles gestorProyectiles
    ) {
        Jugador jugador = new Jugador(new ArrayList<>());

        for (int i = 0; i < nombresHormigas.size(); i++) {
            String tipo = nombresHormigas.get(i);
            if (tipo == null) continue;

            Vector2 pos = posiciones.get(i);

            switch (tipo) {
                case "Cuadro_HO_Up" -> jugador.agregarPersonaje(new HormigaObrera(gestorColisiones, gestorProyectiles, pos.x, pos.y));
                case "Cuadro_HG_Up" -> jugador.agregarPersonaje(new HormigaGuerrera(gestorColisiones, gestorProyectiles, pos.x, pos.y));
                case "Cuadro_HE_Up" -> jugador.agregarPersonaje(new HormigaExploradora(gestorColisiones, gestorProyectiles, pos.x, pos.y));
            }
        }

        return jugador;
    }

    @Override
    public void render(float delta) {
        gestorJuego.actualizar(delta, mapa);
        procesarEntradaJugador(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            ScreenManager.setScreen(new PauseScreen(juego, this));
        }

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

        if (activo != null) hud.mostrarBarraCarga(activo);

        gestorJuego.renderDebug(RecursosGlobales.shapeRenderer, RecursosGlobales.camaraPersonaje);

        escenario.act(delta);
        escenario.draw();

        actualizarTurno();
    }

    private void actualizarTurno() {
        int turnoActual = gestorJuego.getTurnoActual();
        if (turnoActual != turnoAnterior && turnoActual >= 0 && turnoActual < controles.size()) {
            controles.get(turnoAnterior).reset();
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
