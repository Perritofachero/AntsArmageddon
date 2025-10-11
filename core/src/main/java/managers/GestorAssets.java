package managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import utils.Constantes;

public class GestorAssets {

    private static final AssetManager assetManager = new AssetManager();

    public static void load() {

        // --- Botones ---
        assetManager.load(Constantes.ATLAS_BOTONES, TextureAtlas.class);
        assetManager.load(Constantes.BOTONES_PNG, Texture.class);
        assetManager.load(Constantes.SONIDO_CLICK, Sound.class);

        // --- Fondos ---
        assetManager.load(Constantes.FONDO_PANTALLA, Texture.class);
        assetManager.load(Constantes.FONDO_JUEGO, Texture.class);
        assetManager.load(Constantes.GAME_OVER, Texture.class);

        // --- Mapas ---
        assetManager.load(Constantes.MAPA_1, Texture.class);
        assetManager.load(Constantes.MAPA_2, Texture.class);
        assetManager.load(Constantes.MAPA_3, Texture.class);
        assetManager.load(Constantes.MAPA_4, Texture.class);

        // --- Personajes ---
        assetManager.load(Constantes.HORMIGA_OBRERA, Texture.class);
        assetManager.load(Constantes.HORMIGA_GUERRERA, Texture.class);
        assetManager.load(Constantes.HORMIGA_EXPLORADORA, Texture.class);
        assetManager.load(Constantes.MIRA, Texture.class);

        // --- Proyectiles ---
        assetManager.load(Constantes.ROCA, Texture.class);
        assetManager.load(Constantes.NECTAR, Texture.class);
        assetManager.load(Constantes.GRANADA, Texture.class);

        // --- Otros assets ---
        assetManager.load(Constantes.PNG_1, Texture.class);
        assetManager.load(Constantes.PNG_2, Texture.class);
        assetManager.load(Constantes.PNG_3, Texture.class);
        assetManager.load(Constantes.PNG_4, Texture.class);

        // --- Fuentes ---
        assetManager.load(Constantes.FONT_CONTADOR, BitmapFont.class);
        assetManager.load(Constantes.FONT_VIDA, BitmapFont.class);

        assetManager.finishLoading();
    }

    public static boolean update() { return assetManager.update(); }
    public static float getProgress() { return assetManager.getProgress(); }
    public static <T> T get(String path, Class<T> type) { return assetManager.get(path, type); }
    public static void dispose() { assetManager.dispose(); }
}
