package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.principal.AntsArmageddon;
import hud.EventosBoton;
import hud.FabricaBotones;
import managers.ScreenManager;
import utils.Constantes;

public class PauseScreen extends ScreenMenus {

    private final GameScreen gameScreen;

    public PauseScreen(AntsArmageddon juego, GameScreen gameScreen) {
        super(juego);
        this.gameScreen = gameScreen;
    }

    @Override
    protected void construirUI() {
        Image fondoTransparente = new Image(new Texture(Gdx.files.internal(Constantes.FONDO_PANTALLA)));
        fondoTransparente.setColor(0, 0, 0, 0.6f);
        fondoTransparente.setFillParent(true);
        escenario.addActor(fondoTransparente);

        ImageButton botonReanudar = FabricaBotones.OPC1.crearBoton(Constantes.ATLAS_BOTONES, Constantes.SONIDO_CLICK,
            () -> ScreenManager.setScreen(gameScreen));

        ImageButton botonVolver = FabricaBotones.VOLVER.crearBoton(Constantes.ATLAS_BOTONES, Constantes.SONIDO_CLICK,
            EventosBoton.irMenuOpciones(juego));

        Table tabla = new Table();
        tabla.setFillParent(true);
        tabla.center();
        tabla.defaults().pad(20);
        tabla.add(botonReanudar).pad(10).row();
        tabla.add(botonVolver).pad(10).row();

        escenario.addActor(tabla);
    }
}
