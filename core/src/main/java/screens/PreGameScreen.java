//Hacer que las hormigas sean un atlas y convertirlas en botones
//para seleccionarlas en este menu.

//Crear boton random, botones de tiempo por turno y frecuencia power ups

//Resoluciones: personajes 50x50, random 64x64 o un poco mas grande tal vez, pero se puede reescalar
//Volver y jugar estan perfectos pero un poco aplastados, arreglar eso.

package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.principal.AntsArmageddon;
import hud.FabricaBotones;
import managers.GestorAssets;
import managers.ScreenManager;
import utils.Constantes;
import java.util.*;
import java.util.List;
import java.util.function.Function;

public class PreGameScreen extends ScreenMenus {

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
        rootTable.setDebug(true);
        escenario.addActor(rootTable);

        rootTable.add(crearTitulo()).colspan(2).center().padBottom(20).row();
        rootTable.add(crearPanelIzquierdo()).width(Constantes.RESOLUCION_ANCHO / 3f).top().left();
        rootTable.add(crearPanelDerecho()).width(Constantes.RESOLUCION_ANCHO * 2 / 3f).top().right();
    }

    private Table crearPanelIzquierdo() {
        Table panel = new Table();
        panel.defaults().pad(10);
        panel.setDebug(true);

        cargarTexturasMapas();

        imagenMapa = new Image(texturasMapas.get(indiceMapa));
        imagenMapa.setScaling(Scaling.fit);

        Table marcoMapa = new Table();
        marcoMapa.setDebug(true);
        marcoMapa.add(imagenMapa).width(300).height(200);
        panel.add(marcoMapa).padBottom(10).row();

        final var skin = new Skin(Gdx.files.internal("uiskin.json"));
        selectorMapa = new SelectBox<>(skin);
        selectorMapa.setItems("Mapa 1", "Mapa 2", "Mapa 3");
        panel.add(selectorMapa).width(250).row();

        final var sonidoClick = GestorAssets.get(Constantes.SONIDO_CLICK, Sound.class);
        final var atlasBotones = GestorAssets.get(Constantes.ATLAS_BOTONES, TextureAtlas.class);
        final var atlasOpciones = GestorAssets.get(Constantes.ATLAS_OPCIONES, TextureAtlas.class);

        ImageButton botonRandom = FabricaBotones.RANDOM.crearBoton(atlasBotones, sonidoClick, () -> {
            indiceMapa = new Random().nextInt(texturasMapas.size());
            selectorMapa.setSelectedIndex(indiceMapa);
            imagenMapa.setDrawable(new TextureRegionDrawable(new TextureRegion(texturasMapas.get(indiceMapa))));
        });
        panel.add(botonRandom).row();

        Table opcionesTable = new Table();
        opcionesTable.defaults().pad(5);
        opcionesTable.setDebug(true);

        Label labelTiempo = new Label("Tiempo por turno", skin);
        ImageButton botonTiempo = crearBotonCiclicoOpciones(
            atlasOpciones, sonidoClick,
            new String[]{"15_up", "25_up", "30_up"},
            new String[]{"15_over", "25_over", "30_over"}
        );
        opcionesTable.add(labelTiempo).right().padRight(10);
        opcionesTable.add(botonTiempo).size(65).row();

        Label labelPower = new Label("Frecuencia power-ups (turnos)", skin);
        ImageButton botonPowerUps = crearBotonCiclicoOpciones(
            atlasOpciones, sonidoClick,
            new String[]{"1_up", "2_up", "3_up"},
            new String[]{"1_over", "2_over", "3_over"}
        );
        opcionesTable.add(labelPower).right().padRight(10);
        opcionesTable.add(botonPowerUps).size(65).row();

        panel.add(opcionesTable).row();
        configurarEventosSelector();
        return panel;
    }

    private void cargarTexturasMapas() {
        texturasMapas = new ArrayList<>();
        Collections.addAll(texturasMapas,
            GestorAssets.get(Constantes.MAPA_1, Texture.class),
            GestorAssets.get(Constantes.MAPA_2, Texture.class),
            GestorAssets.get(Constantes.MAPA_3, Texture.class)
        );
    }

    private void configurarEventosSelector() {
        selectorMapa.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                indiceMapa = selectorMapa.getSelectedIndex();
                imagenMapa.setDrawable(new TextureRegionDrawable(new TextureRegion(texturasMapas.get(indiceMapa))));
            }
        });
    }

    private Table crearPanelDerecho() {
        Table panel = new Table();
        panel.defaults().pad(10);
        panel.setDebug(true);

        Table panelSuperior = crearPanelEquipos();
        Table panelInferior = crearPanelInferior();

        panel.add(panelSuperior).expand().fill().padBottom(100).row();
        panel.add(panelInferior).bottom().height(120).fillX();
        return panel;
    }

    private Table crearPanelEquipos() {
        Table panel = new Table();
        panel.defaults().pad(20);
        panel.setDebug(true);

        final var skin = new Skin(Gdx.files.internal("uiskin.json"));
        final var sonidoClick = GestorAssets.get(Constantes.SONIDO_CLICK, Sound.class);
        final var atlasHormigas = GestorAssets.get(Constantes.ATLAS_CUADRO_PERSONAJES, TextureAtlas.class);

        Function<String, Table> crearEquipo = nombreJugador -> {
            Table contenedor = new Table();
            contenedor.setDebug(true);
            contenedor.add(new Label(nombreJugador, skin)).colspan(6).center().padBottom(10);
            contenedor.row();
            for (int i = 0; i < 6; i++) {
                contenedor.add(crearBotonHormiga(atlasHormigas, sonidoClick)).size(65).pad(5);
            }
            return contenedor;
        };

        panel.add(crearEquipo.apply("Jugador 1")).center().row();
        panel.add(crearEquipo.apply("Jugador 2")).center();
        return panel;
    }

    private Table crearPanelInferior() {
        Table panel = new Table();
        panel.defaults().pad(10);
        panel.setDebug(true);

        final var sonidoClick = GestorAssets.get(Constantes.SONIDO_CLICK, Sound.class);
        final var atlasBotones = GestorAssets.get(Constantes.ATLAS_BOTONES, TextureAtlas.class);

        ImageButton botonVolver = FabricaBotones.VOLVER.crearBoton(atlasBotones, sonidoClick,
            () -> ScreenManager.setScreen(new MenuScreen(juego)));
        ImageButton botonJugar = FabricaBotones.JUGAR.crearBoton(atlasBotones, sonidoClick,
            () -> ScreenManager.setScreen(new GameScreen(juego)));

        panel.add(botonVolver).width(150).height(55).padRight(20);
        panel.add(botonJugar).width(150).height(55);
        return panel;
    }

    private Image crearTitulo() {
        Image titulo = new Image(GestorAssets.get(Constantes.PNG_1, Texture.class));
        titulo.setDebug(true);
        return titulo;
    }

    private ImageButton crearBotonHormiga(TextureAtlas atlas, Sound sonido) {
        TextureRegionDrawable[] up = {
            new TextureRegionDrawable(atlas.findRegion("Cuadro_HO_Up")),
            new TextureRegionDrawable(atlas.findRegion("Cuadro_HG_Up")),
            new TextureRegionDrawable(atlas.findRegion("Cuadro_HE_Up"))
        };
        TextureRegionDrawable[] over = {
            new TextureRegionDrawable(atlas.findRegion("Cuadro_HO_Over")),
            new TextureRegionDrawable(atlas.findRegion("Cuadro_HG_Over")),
            new TextureRegionDrawable(atlas.findRegion("Cuadro_HE_Over"))
        };
        TextureRegionDrawable vacio = new TextureRegionDrawable(atlas.findRegion("Cuadro_Vacio"));

        ImageButton boton = new ImageButton(new ImageButton.ImageButtonStyle());
        boton.setDebug(true);
        boton.getStyle().imageUp = up[0];
        boton.getStyle().imageOver = over[0];

        final int[] indice = {0};
        final boolean[] visible = {true};

        boton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (button == Input.Buttons.LEFT) {
                    if (!visible[0]) {
                        visible[0] = true;
                    } else {
                        indice[0] = (indice[0] + 1) % up.length;
                    }
                    boton.getStyle().imageUp = up[indice[0]];
                    boton.getStyle().imageOver = over[indice[0]];
                } else if (button == Input.Buttons.RIGHT) {
                    visible[0] = false;
                    boton.getStyle().imageUp = vacio;
                    boton.getStyle().imageOver = vacio;
                }
                if (sonido != null) sonido.play();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        return boton;
    }

    private ImageButton crearBotonCiclicoOpciones(TextureAtlas atlas, Sound sonido, String[] ups, String[] overs) {
        TextureRegionDrawable[] up = Arrays.stream(ups)
            .map(r -> new TextureRegionDrawable(atlas.findRegion(r)))
            .toArray(TextureRegionDrawable[]::new);
        TextureRegionDrawable[] over = Arrays.stream(overs)
            .map(r -> new TextureRegionDrawable(atlas.findRegion(r)))
            .toArray(TextureRegionDrawable[]::new);

        ImageButton boton = new ImageButton(new ImageButton.ImageButtonStyle());
        boton.setDebug(true);
        boton.getStyle().imageUp = up[0];
        boton.getStyle().imageOver = over[0];

        final int[] indice = {0};

        boton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (button == Input.Buttons.LEFT) {
                    indice[0] = (indice[0] + 1) % up.length;
                    boton.getStyle().imageUp = up[indice[0]];
                    boton.getStyle().imageOver = over[indice[0]];
                    if (sonido != null) sonido.play();
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        return boton;
    }
}
