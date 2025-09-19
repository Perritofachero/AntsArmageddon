package hud;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import managers.GestorAssets;
import utils.Constantes;

public enum FabricaBotones {
    OPC1("opc_up", "opc_over", "opc_down"),
    SONIDO("sonido_up", "sonido_over", "sonido_down"),
    VOLVER("volver_up", "volver_over", "volver_down"),
    JUGAR("jugar_up", "jugar_over", "jugar_down"),
    OPCIONES("opciones_up", "opciones_over", "opciones_down"),
    SALIR("salir_up", "salir_over", "salir_down");

    private final String up;
    private final String over;
    private final String down;

    FabricaBotones(String up, String over, String down) {
        this.up = up;
        this.over = over;
        this.down = down;
    }

    public ImageButton crearBoton(Runnable evento) {
        TextureAtlas atlas = GestorAssets.get(Constantes.ATLAS_BOTONES, TextureAtlas.class);
        ImageButton.ImageButtonStyle estilo = new ImageButton.ImageButtonStyle();

        estilo.up   = new TextureRegionDrawable(atlas.findRegion(up));
        estilo.over = new TextureRegionDrawable(atlas.findRegion(over));
        estilo.down = new TextureRegionDrawable(atlas.findRegion(down));

        ImageButton boton = new ImageButton(estilo);

        boton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (evento != null) {
                    evento.run();
                }
            }
        });

        return boton;
    }

    public static void agregarSonido(ImageButton boton, Sound sonido) {
        boton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sonido.play();
            }
        });

    }
}
