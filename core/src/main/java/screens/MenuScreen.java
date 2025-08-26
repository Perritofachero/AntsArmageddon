package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.principal.AntsArmageddon;
import hud.EventosBoton;
import hud.FabricaBotones;
import utils.Constantes;

public class MenuScreen implements Screen {

    private final AntsArmageddon juego;
    private AssetManager assetManager;

    private Stage escenario;
    private FitViewport viewport;
    private OrthographicCamera camara;
    private Texture texturaFondo;
    private Sprite spriteFondo;
    private SpriteBatch batch;

    public MenuScreen(AntsArmageddon juego, AssetManager assetManager){
        this.assetManager = assetManager;
        this.juego = juego;

    }

    @Override
    public void show() {
        camara = new OrthographicCamera();
        viewport = new FitViewport(Constantes.RESOLUCION_ANCHO, Constantes.RESOLUCION_ALTO, camara);
        escenario = new Stage(viewport);
        Gdx.input.setInputProcessor(escenario);

        texturaFondo = assetManager.get(Constantes.FONDO_JUEGO, Texture.class);
        spriteFondo = new Sprite(texturaFondo);

        spriteFondo.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());

        batch = new SpriteBatch();

        construirMenu();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        spriteFondo.draw(batch);
        batch.end();

        escenario.act(delta);
        escenario.draw();

    }

    public void construirMenu() {
        ImageButton jugar = FabricaBotones.crearBoton(assetManager, "jugar_up", "jugar_over", "jugar_down", EventosBoton.irJuego(juego));
        ImageButton opciones = FabricaBotones.crearBoton(assetManager, "opciones_up", "opciones_over", "opciones_down", EventosBoton.irMenuOpciones(juego));
        ImageButton salir = FabricaBotones.crearBoton(assetManager, "salir_up", "salir_over", "salir_down", EventosBoton.salirJuego());

        Sound sonidoClick = assetManager.get(Constantes.SONIDO_BOTONES, Sound.class);

        FabricaBotones.agregarSonido(jugar, sonidoClick);
        FabricaBotones.agregarSonido(opciones, sonidoClick);
        FabricaBotones.agregarSonido(salir, sonidoClick);

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(jugar).pad(10).row();
        table.add(opciones).pad(10).row();
        table.add(salir).pad(10).row();

        escenario.addActor(table);
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
