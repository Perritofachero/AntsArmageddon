package screens;

import Gameplay.Gestores.GestorRutas;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.principal.AntsArmageddon;
import hud.EventosBoton;
import hud.FabricaBotones;
import managers.GestorAssets;
import managers.ScreenManager;

public class PauseScreen extends ScreenMenus {

    private final GameScreen gameScreen;

    public PauseScreen(AntsArmageddon juego, GameScreen gameScreen) {
        super(juego);
        this.gameScreen = gameScreen;
    }

    @Override
    protected void construirUI() {
        Image fondoTransparente = new Image(GestorAssets.get(GestorRutas.FONDO_PANTALLA, Texture.class));
        fondoTransparente.setColor(0, 0, 0, 0.6f);
        fondoTransparente.setFillParent(true);
        escenario.addActor(fondoTransparente);

        ImageButton botonReanudar = FabricaBotones.OPC1.crearBoton(
            GestorRutas.ATLAS_BOTONES,
            GestorRutas.SONIDO_CLICK,
            () -> ScreenManager.setScreen(gameScreen)
        );

        ImageButton botonVolver = FabricaBotones.VOLVER.crearBoton(
            GestorRutas.ATLAS_BOTONES,
            GestorRutas.SONIDO_CLICK,
            EventosBoton.irMenuOpciones(juego)
        );

        Table tabla = new Table();
        tabla.setFillParent(true);
        tabla.center();
        tabla.defaults().pad(20);
        tabla.add(botonReanudar).pad(10).row();
        tabla.add(botonVolver).pad(10).row();

        escenario.addActor(tabla);
    }

}
