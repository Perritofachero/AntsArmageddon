package managers;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import utils.Constantes;

public class GestorAssets {

    private static final com.badlogic.gdx.assets.AssetManager assetManager = new com.badlogic.gdx.assets.AssetManager();

    public static void load() {

        // Botones
        assetManager.load(Constantes.ATLAS_BOTONES, TextureAtlas.class);
        assetManager.load("Botones/botones.png", Texture.class);
        assetManager.load("Sonidos/sonido_click.mp3", Sound.class);

        // Fondos
        assetManager.load("fondoPantalla.png", Texture.class);
        assetManager.load("pruebaFondoJuego.jpg", Texture.class);
        assetManager.load("gameOver.png", Texture.class);

        // Mapas
        assetManager.load("Mapas/pruebaMapa1.png", Texture.class);
        assetManager.load("Mapas/pruebaMapa2.png", Texture.class);
        assetManager.load("Mapas/pruebaMapa3.png", Texture.class);
        assetManager.load("Mapas/pruebaMapa4.png", Texture.class);

        // Personajes
        assetManager.load("Personajes/HormigaObrera.png", Texture.class);
        assetManager.load("Personajes/HormigaGuerrera.png", Texture.class);
        assetManager.load("Personajes/HormigaExploradora.png", Texture.class);
        assetManager.load("Personajes/mira.png", Texture.class);

        // Proyectiles
        assetManager.load("Proyectiles/roca.png", Texture.class);
        assetManager.load("Proyectiles/nectar.png", Texture.class);
        assetManager.load("Proyectiles/granada.png", Texture.class);

        // Otros assets
        assetManager.load("1.png", Texture.class);
        assetManager.load("2.png", Texture.class);
        assetManager.load("3.png", Texture.class);
        assetManager.load("4.png", Texture.class);

        // Fuentes
        assetManager.load("Fonts/font_Contador.fnt", BitmapFont.class);
        assetManager.load("Fonts/font_Vida.fnt", BitmapFont.class);

        assetManager.finishLoading();
    }

    public static boolean update() { return assetManager.update(); }
    public static float getProgress() { return assetManager.getProgress(); }
    public static <T> T get(String path, Class<T> type) { return assetManager.get(path, type); }
    public static void dispose() { assetManager.dispose(); }
}
