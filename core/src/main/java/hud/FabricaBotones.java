package hud;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import utils.Constantes;

public class FabricaBotones {

    public static ImageButton crearBoton(AssetManager assetManager, String regionImagenUp,
    String regionImagenOver, String regionImagenDown, Runnable evento) {

        TextureAtlas atlas = assetManager.get(Constantes.ATLAS_BOTONES, TextureAtlas.class);
        ImageButton.ImageButtonStyle estilo = new ImageButton.ImageButtonStyle();

        estilo.up = new TextureRegionDrawable(atlas.findRegion(regionImagenUp));
        estilo.over = new TextureRegionDrawable(atlas.findRegion(regionImagenOver));
        estilo.down = new TextureRegionDrawable(atlas.findRegion(regionImagenDown));

        ImageButton boton = new ImageButton(estilo);

        boton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(evento != null) {
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

    /*Hacer botones un enum, con todos los datos de los botones ya creados*/

}
