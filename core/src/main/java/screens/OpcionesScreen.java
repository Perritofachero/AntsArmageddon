package screens;

import Gameplay.Gestores.GestorAudio;
import Gameplay.Gestores.GestorRutas;
import Gameplay.Gestores.Logicos.GestorScreen;
import Gameplay.Gestores.Visuales.GestorAssets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.principal.AntsArmageddon;
import hud.FabricaBotones;

public class OpcionesScreen extends ScreenMenus {

    private Slider sliderMusica;
    private Slider sliderSFX;
    private BitmapFont fuente;

    public OpcionesScreen(AntsArmageddon juego) { super(juego); }

    @Override
    protected void construirUI() {

        fuente = GestorAssets.get(GestorRutas.FONT_VIDA, BitmapFont.class);

        Label.LabelStyle estiloTexto = new Label.LabelStyle(fuente, null);

        Label labTitulo = new Label("OPCIONES", estiloTexto);
        labTitulo.setAlignment(Align.center);

        Label labMusica = new Label("Volumen Musica:", estiloTexto);
        labMusica.setAlignment(Align.center);

        Label labSFX = new Label("Volumen SFX:", estiloTexto);
        labSFX.setAlignment(Align.center);

        Label muteText = new Label("Mute", estiloTexto);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        sliderMusica = new Slider(0f, 1f, 0.01f, false, skin);
        sliderMusica.setValue(GestorAudio.volumenMusica);

        sliderSFX = new Slider(0f, 1f, 0.01f, false, skin);
        sliderSFX.setValue(GestorAudio.volumenSFX);

        sliderMusica.addListener(e -> {
            GestorAudio.setVolumenMusica(sliderMusica.getValue());
            return false;
        });

        sliderSFX.addListener(e -> {
            GestorAudio.setVolumenSFX(sliderSFX.getValue());
            return false;
        });

        ImageButton btnMute = FabricaBotones.OPC1.crearBoton(
            GestorRutas.ATLAS_BOTONES, GestorRutas.SONIDO_CLICK_BOTON,
            () -> { GestorAudio.setVolumenMusica(0); GestorAudio.setVolumenSFX(0);
                sliderMusica.setValue(0); sliderSFX.setValue(0); }
        );

        ImageButton btnTutorial = FabricaBotones.OPCIONES.crearBoton(
            GestorRutas.ATLAS_BOTONES, GestorRutas.SONIDO_CLICK_BOTON,
            () -> GestorScreen.setScreen(new TutorialScreen(juego))
        );

        /*ImageButton btnOPC = FabricaBotones.OPC1.crearBoton(
            GestorRutas.ATLAS_BOTONES, GestorRutas.SONIDO_CLICK_BOTON,
            EventosBoton.descomponerSheet("explosion.png", 12, 1, "atlasDescompuestos/")

        );*/

        ImageButton btnVolver = FabricaBotones.VOLVER.crearBoton(
            GestorRutas.ATLAS_BOTONES, GestorRutas.SONIDO_CLICK_BOTON,
            () -> GestorScreen.setScreen(new MenuScreen(juego))
        );

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(labTitulo).padBottom(30).row();

        table.add(labMusica).padBottom(5).row();
        table.add(sliderMusica).width(250).padBottom(15).row();

        table.add(labSFX).padBottom(5).row();
        table.add(sliderSFX).width(250).padBottom(25).row();

        table.add(muteText).padBottom(5).row();
        table.add(btnMute).padBottom(30).row();

        table.add(btnTutorial).padBottom(40).row();

        //table.add(btnOPC).padBottom(40).row();

        table.add(btnVolver).padBottom(10).row();

        escenario.addActor(table);
    }
}
