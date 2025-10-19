package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.principal.AntsArmageddon;
import hud.FabricaBotones;
import managers.GestorAssets;
import managers.ScreenManager;
import partida.ConfiguracionPartida;
import utils.Constantes;
import java.util.*;
import java.util.List;

public class PreGameScreen extends ScreenMenus {

    private ConfiguracionPartida configuracion = new ConfiguracionPartida();

    private Table rootTable;
    private Image imagenMapa;
    private SelectBox<String> selectorMapa;
    private List<Texture> texturasMapas;
    private int indiceMapa = 0;

    public PreGameScreen(AntsArmageddon juego) {
        super(juego);
    }

    @Override
    protected void construirUI() {
        rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.defaults().pad(10);
        escenario.addActor(rootTable);

        rootTable.add(crearTitulo()).colspan(2).center().padBottom(20).row();
        rootTable.add(crearPanelIzquierdo()).width(Constantes.RESOLUCION_ANCHO / 3f).top().left();
        rootTable.add(crearPanelDerecho()).width(Constantes.RESOLUCION_ANCHO * 2 / 3f).top().right();

        inicializarConfiguracionPorDefecto();
    }

    private Table crearPanelIzquierdo() {
        Table panel = new Table();
        panel.defaults().pad(10);

        cargarTexturasMapas();

        imagenMapa = new Image(texturasMapas.get(indiceMapa));
        imagenMapa.setScaling(Scaling.fit);

        final var skin = new Skin(Gdx.files.internal("uiskin.json"));
        selectorMapa = new SelectBox<>(skin);
        selectorMapa.setItems("Mapa 1", "Mapa 2", "Mapa 3", "Mapa 4");

        configurarEventosSelector();

        ImageButton botonRandom = FabricaBotones.RANDOM.crearBoton(
            Constantes.ATLAS_BOTONES,
            Constantes.SONIDO_CLICK,
            () -> {
                indiceMapa = new Random().nextInt(texturasMapas.size());
                selectorMapa.setSelectedIndex(indiceMapa);
                imagenMapa.setDrawable(new TextureRegionDrawable(new TextureRegion(texturasMapas.get(indiceMapa))));
                configuracion.setMapa(indiceMapa);
            }
        );

        Table opciones = crearOpcionesConfigurables(skin);

        panel.add(imagenMapa).width(300).height(200).padBottom(10).row();
        panel.add(selectorMapa).width(250).row();
        panel.add(botonRandom).row();
        panel.add(opciones).row();

        return panel;
    }

    private Table crearOpcionesConfigurables(Skin skin) {
        Table opciones = new Table();
        opciones.defaults().pad(5);

        Label labelTiempo = new Label("Tiempo por turno", skin);
        ImageButton botonTiempo = crearBotonTiempo();

        Label labelPower = new Label("Frecuencia power-ups (turnos)", skin);
        ImageButton botonPowerUps = crearBotonPowerUps();

        opciones.add(labelTiempo).right().padRight(10);
        opciones.add(botonTiempo).size(65).row();

        opciones.add(labelPower).right().padRight(10);
        opciones.add(botonPowerUps).size(65).row();

        return opciones;
    }

    private ImageButton crearBotonTiempo() {
        return FabricaBotones.crearBotonCiclico(
            Constantes.ATLAS_OPCIONES, Constantes.SONIDO_CLICK,
            new String[]{"15_up", "25_up", "30_up"},
            new String[]{"15_over", "25_over", "30_over"},
            indice -> configuracion.setTiempoTurnoPorIndice(indice)
        );
    }

    private ImageButton crearBotonPowerUps() {
        return FabricaBotones.crearBotonCiclico(
            Constantes.ATLAS_OPCIONES, Constantes.SONIDO_CLICK,
            new String[]{"1_up", "2_up", "3_up"},
            new String[]{"1_over", "2_over", "3_over"},
            indice -> configuracion.setFrecuenciaPowerUpsPorIndice(indice)
        );
    }

    private void configurarEventosSelector() {
        selectorMapa.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                indiceMapa = selectorMapa.getSelectedIndex();
                imagenMapa.setDrawable(new TextureRegionDrawable(new TextureRegion(texturasMapas.get(indiceMapa))));
                configuracion.setMapa(indiceMapa);
            }
        });
    }

    private void cargarTexturasMapas() {
        texturasMapas = new ArrayList<>();
        Collections.addAll(texturasMapas,
            GestorAssets.get(Constantes.MAPA_1, Texture.class),
            GestorAssets.get(Constantes.MAPA_2, Texture.class),
            GestorAssets.get(Constantes.MAPA_3, Texture.class),
            GestorAssets.get(Constantes.MAPA_4, Texture.class)
        );
    }

    private Table crearPanelDerecho() {
        Table panel = new Table();
        Table panelSuperior = crearPanelEquipos();
        Table panelInferior = crearPanelInferior();

        panel.add(panelSuperior).expand().fill().padBottom(100).row();
        panel.add(panelInferior).bottom().height(120).fillX();
        return panel;
    }

    private Table crearPanelEquipos() {
        Table panel = new Table();
        final var skin = new Skin(Gdx.files.internal("uiskin.json"));

        panel.add(crearPanelEquipo("Jugador 1", 1, skin)).center().row();
        panel.add(crearPanelEquipo("Jugador 2", 2, skin)).center();

        return panel;
    }

    private Table crearPanelEquipo(String nombre, int jugador, Skin skin) {
        Table contenedor = new Table();
        contenedor.add(new Label(nombre, skin)).colspan(6).center().padBottom(10).row();

        for (int i = 0; i < 6; i++) {
            final int slot = i;
            ImageButton botonHormiga = FabricaBotones.crearBotonHormiga(
                Constantes.ATLAS_CUADRO_PERSONAJES,
                Constantes.SONIDO_CLICK,
                indiceHormiga -> configuracion.setHormiga(jugador, slot, indiceHormiga)
            );
            contenedor.add(botonHormiga).size(65).pad(5);
        }

        return contenedor;
    }

    private Table crearPanelInferior() {
        Table panel = new Table();

        ImageButton botonVolver = FabricaBotones.VOLVER.crearBoton(
            Constantes.ATLAS_BOTONES,
            Constantes.SONIDO_CLICK,
            () -> ScreenManager.setScreen(new MenuScreen(juego))
        );

        ImageButton botonJugar = FabricaBotones.JUGAR.crearBoton(
            Constantes.ATLAS_BOTONES,
            Constantes.SONIDO_CLICK,
            () -> ScreenManager.setScreen(new GameScreen(juego, configuracion))
        );

        panel.add(botonVolver).width(150).height(55).padRight(20);
        panel.add(botonJugar).width(150).height(55);
        return panel;
    }

    private Image crearTitulo() {
        Image titulo = new Image(GestorAssets.get(Constantes.PNG_1, Texture.class));
        titulo.setDebug(true);
        return titulo;
    }

    private void inicializarConfiguracionPorDefecto() {
        configuracion.setMapa(0);
        configuracion.setTiempoTurnoPorIndice(0);
        configuracion.setFrecuenciaPowerUpsPorIndice(0);

        for (int i = 0; i < 6; i++) {
            configuracion.setHormiga(1, i, 0);
            configuracion.setHormiga(2, i, 0);
        }
    }

}
