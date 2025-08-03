package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.principal.AntsArmageddon;
import hud.EventosBoton;
import hud.FabricaBotones;
import utils.Constantes;

public class OpcionesScreen implements Screen {

    private final AntsArmageddon juego;

    private Stage escenario;
    private FitViewport viewport;
    private OrthographicCamera camara;

    private Texture texturaFondo;
    private Sprite spriteFondo;

    private SpriteBatch batch;

    private ImageButton opc1, opc2, volver;

    public OpcionesScreen(AntsArmageddon juego){
        this.juego = juego;
    }

    @Override
    public void show() {
        camara = new OrthographicCamera();
        viewport = new FitViewport(Constantes.ANCHO, Constantes.ALTO, camara);
        escenario = new Stage(viewport);
        Gdx.input.setInputProcessor(escenario);

        texturaFondo = new Texture(Gdx.files.internal("fondoPantalla.png"));
        spriteFondo = new Sprite(texturaFondo);

        spriteFondo.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());

        batch = new SpriteBatch();

        construirMenuOpciones();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f,   0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        spriteFondo.draw(batch);
        batch.end();

        escenario.act(delta);
        escenario.draw();
    }

    public void construirMenuOpciones(){
        ImageButton opc1 = FabricaBotones.crearBoton("Opc.png");
        ImageButton opc2 = FabricaBotones.crearBoton("Sonido.png");
        ImageButton volver = FabricaBotones.crearBoton("Volver.png");

        FabricaBotones.agregarEventos(opc1, () -> {System.out.println("Presionado opc1");});
        FabricaBotones.agregarEventos(opc2, () -> {System.out.println("Presionado opc2");});
        FabricaBotones.agregarEventos(volver, EventosBoton.salirMenuOpciones(juego));

        Table table = new Table();
        table.setFillParent(true);
        table.top().right();
        table.add(opc1).pad(10).row();
        table.add(opc2).pad(10).row();
        table.add(volver).pad(10).row();

        escenario.addActor(table);
    }



    @Override
    public void resize(int ancho, int alto) { viewport.update(ancho, alto, true); }

    @Override
    public void dispose() { escenario.dispose(); }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

}
