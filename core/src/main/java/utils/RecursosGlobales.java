package utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class RecursosGlobales {
    public static SpriteBatch batch;
    public static ShapeRenderer shapeRenderer;

    public static void inicializar() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
    }

    public static void dispose() {
        if (batch != null) batch.dispose();
        if (shapeRenderer != null) shapeRenderer.dispose();
    }
}
