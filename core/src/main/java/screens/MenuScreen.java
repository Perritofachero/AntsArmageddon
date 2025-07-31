package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.principal.AntsArmageddon;
import utils.EventosBoton;
import utils.FabricaBotones;

public class MenuScreen implements Screen {

    private final AntsArmageddon juego;

    private Stage escenario;
    private FitViewport viewport;
    private OrthographicCamera camara;

    private ImageButton jugar, opciones, salir;

    public MenuScreen(AntsArmageddon juego){
        this.juego = juego;
    }

    @Override
    public void show() {
        camara = new OrthographicCamera();
        viewport = new FitViewport(800, 400, camara);
        escenario = new Stage(viewport);
        Gdx.input.setInputProcessor(escenario);

        jugar = FabricaBotones.crearBoton("jugar.png");
        opciones = FabricaBotones.crearBoton("opciones.png");
        salir = FabricaBotones.crearBoton("salir.png");

        FabricaBotones.agregarEventos(jugar, () -> {System.out.println("Presionado Jugado");});
        FabricaBotones.agregarEventos(opciones, EventosBoton.irMenuOpciones(juego));
        FabricaBotones.agregarEventos(salir, EventosBoton.salirDelJuego());

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(jugar).width(150).height(50).pad(10).row();
        table.add(opciones).width(150).height(50).pad(10).row();
        table.add(salir).width(150).height(50).pad(10);

        escenario.addActor(table);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
