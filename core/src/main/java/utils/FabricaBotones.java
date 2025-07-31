package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.net.http.WebSocket;
import java.util.HashMap;

public class FabricaBotones {

    private static final HashMap<String, Texture> texturaCache = new HashMap<>();
    private static final Sound sonidoClick = Gdx.audio.newSound(Gdx.files.internal("sonido_click.mp3"));

    public static ImageButton crearBoton(String nombreImagen) {
        Texture textura = texturaCache.get(nombreImagen);
        if (textura == null) {
            textura = new Texture(Gdx.files.internal(nombreImagen));
            texturaCache.put(nombreImagen, textura);
        }
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(textura));
        return new ImageButton(drawable);
    }

    public static void agregarEventos(ImageButton boton, Runnable evento){
        boton.addListener(agregarHover());
        boton.addListener(agregarSonido());

        boton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(evento != null){
                    evento.run();
                }
            }
        });
    }

    public static void dispose() {
        for (Texture t : texturaCache.values()) {
            t.dispose();
        }
        texturaCache.clear();
    }

    public static InputListener agregarHover() {
        return new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (event.getListenerActor() instanceof ImageButton boton) {
                    boton.getImage().setColor(0.7f, 0.7f, 0.7f, 1f);
                }
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (event.getListenerActor() instanceof ImageButton boton) {
                    boton.getImage().setColor(1f, 1f, 1f, 1f);
                }
            }
        };
    }

    public static ClickListener agregarSonido() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sonidoClick.play();
            }
        };
    }
}
