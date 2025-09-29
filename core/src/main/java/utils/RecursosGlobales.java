package utils;

import Fisicas.Camara;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class RecursosGlobales {
    public static SpriteBatch batch;
    public static ShapeRenderer shapeRenderer;
    public static Camara camaraPersonaje;

    public static void inicializar() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        camaraPersonaje = new Camara(
            Constantes.RESOLUCION_ANCHO,
            Constantes.RESOLUCION_ALTO,
            Constantes.RESOLUCION_ANCHO_MAPA,
            Constantes.RESOLUCION_ALTO_MAPA
        );
    }

    public static void dispose() {
        if (batch != null) batch.dispose();
        if (shapeRenderer != null) shapeRenderer.dispose();
    }
}
