package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.principal.AntsArmageddon;
import hud.EventosBoton;
import hud.FabricaBotones;
import managers.GestorAssets;
import utils.Constantes;
import utils.RecursosGlobales;

public class OpcionesScreen implements Screen {

    private final AntsArmageddon juego;
    private Stage escenario;
    private FitViewport viewport;
    private OrthographicCamera camara;
    private Texture texturaFondo;
    private Sprite spriteFondo;

    public OpcionesScreen(AntsArmageddon juego){
        this.juego = juego;

    }

    @Override
    public void show() {
        camara = new OrthographicCamera();
        viewport = new FitViewport(Constantes.RESOLUCION_ANCHO, Constantes.RESOLUCION_ALTO, camara);
        escenario = new Stage(viewport);
        Gdx.input.setInputProcessor(escenario);

        texturaFondo = GestorAssets.get(Constantes.FONDO_PANTALLA, Texture.class);
        spriteFondo = new Sprite(texturaFondo);

        spriteFondo.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());

        construirMenuOpciones();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f,   0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        RecursosGlobales.batch.begin();
        spriteFondo.draw(RecursosGlobales.batch);
        RecursosGlobales.batch.end();

        escenario.act(delta);
        escenario.draw();
    }

    public void construirMenuOpciones(){

        ImageButton opc1 = FabricaBotones.OPC1.crearBoton(() -> System.out.println("Boton opc1 presionado"));
        ImageButton sonido = FabricaBotones.SONIDO.crearBoton(() -> System.out.println("Boton opc1 presionado"));
        ImageButton volver = FabricaBotones.VOLVER.crearBoton(EventosBoton.salirMenuOpciones(juego));

        Sound sonidoClick = GestorAssets.get(Constantes.SONIDO_CLICK, Sound.class);

        FabricaBotones.agregarSonido(opc1, sonidoClick);
        FabricaBotones.agregarSonido(sonido, sonidoClick);
        FabricaBotones.agregarSonido(volver, sonidoClick);

        Table table = new Table();
        table.setFillParent(true);
        table.top().right();
        table.add(opc1).pad(10).row();
        table.add(sonido).pad(10).row();
        table.add(volver).pad(10).row();

        escenario.addActor(table);
    }

    @Override public void resize(int ancho, int alto) { viewport.update(ancho, alto, true); }
    @Override public void dispose() { escenario.dispose(); }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }

}
