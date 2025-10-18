package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.principal.AntsArmageddon;
import hud.EventosBoton;
import hud.FabricaBotones;
import managers.GestorAssets;
import managers.ScreenManager;
import utils.Constantes;

public class PauseScreen extends ScreenMenus {

    public PauseScreen(AntsArmageddon juego) {
        super(juego);
    }

    @Override
    protected void construirUI() {
        // Fondo semitransparente (oscurece el juego de fondo)
        Image fondoTransparente = new Image(new Texture(Gdx.files.internal(Constantes.FONDO_PANTALLA)));
        fondoTransparente.setColor(0, 0, 0, 0.6f);
        fondoTransparente.setFillParent(true);
        escenario.addActor(fondoTransparente);

        Sound sonidoClick = GestorAssets.get(Constantes.SONIDO_CLICK, Sound.class);
        // --- Botones ---
        ImageButton botonReanudar = FabricaBotones.OPC1.crearBoton(sonidoClick, () -> {
            // 🔹 Reanuda el juego volviendo a la pantalla anterior (GameScreen)
            ScreenManager.setScreen(new GameScreen(juego));
        });

        ImageButton botonVolver = FabricaBotones.VOLVER.crearBoton(sonidoClick, EventosBoton.salirMenuOpciones(juego));

        // --- Layout centrado ---
        Table tabla = new Table();
        tabla.setFillParent(true);
        tabla.center();
        tabla.defaults().pad(20);
        tabla.add(botonReanudar).pad(10).row();
        tabla.add(botonVolver).pad(10).row();

        escenario.addActor(tabla);
    }
}
