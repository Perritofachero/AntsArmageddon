package managers;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import utils.Constantes;

public class GestorAssets {

    private static final com.badlogic.gdx.assets.AssetManager assetManager = new com.badlogic.gdx.assets.AssetManager();

    public static void load() {

        assetManager.load(Constantes.ATLAS_BOTONES, TextureAtlas.class);
        assetManager.load("sonido_click.mp3", Sound.class);
        assetManager.load("fondoPantalla.png", Texture.class);
        assetManager.load("Mapa.png", Texture.class);
        assetManager.load("hormiga.png", Texture.class);
        assetManager.load("mira.png", Texture.class);
        assetManager.load("roca.png", Texture.class);
        assetManager.load("prueba.png", Texture.class);
        assetManager.load("pruebaFondoJuego.jpg", Texture.class);
        assetManager.load("1.png", Texture.class);
        assetManager.load("gameOver.png", Texture.class);
        assetManager.load("HormigaObrera.png", Texture.class);
        assetManager.load("Rectangulo.png", Texture.class);
        assetManager.load("RectanguloVida.png", Texture.class);
        assetManager.load("HormigaGuerrera.png", Texture.class);
        assetManager.load("fonts/font_Contador.fnt", BitmapFont.class);
        assetManager.load("fonts/font_Vida.fnt", BitmapFont.class);

        assetManager.load(Constantes.FONDO_JUEGO_PRUEBA, Texture.class);

        assetManager.finishLoading();
    }

    public static boolean update() { return assetManager.update(); }
    public static float getProgress() { return assetManager.getProgress(); }
    public static <T> T get(String path, Class<T> type) { return assetManager.get(path, type); }
    public static void dispose() { assetManager.dispose(); }
}
