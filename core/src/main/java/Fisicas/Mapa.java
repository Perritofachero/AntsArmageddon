package Fisicas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import utils.RecursosGlobales;

public class Mapa {

    private Pixmap pixmap;
    private Texture textura;
    private final String ruta;
    private final Color colorTemp = new Color();

    public Mapa(String ruta) {
        this.ruta = ruta;
        cargarMapa(ruta);
    }

    private void cargarMapa(String ruta) {
        pixmap = new Pixmap(Gdx.files.internal(ruta));
        textura = new Texture(pixmap);
    }

    public boolean esSolido(int x, int y) {
        if (x < 0 || y < 0 || x >= pixmap.getWidth() || y >= pixmap.getHeight()) return false;
        int xPixmap = mundoAX(x);
        int yPixmap = mundoAY(y);
        Color.rgba8888ToColor(colorTemp, pixmap.getPixel(xPixmap, yPixmap));
        return colorTemp.a > 0.05f;
    }

    public boolean colisiona(Rectangle hitboxMundo) {
        int inicioX = Math.max(0, mundoAX((int) hitboxMundo.x));
        int finX = Math.min(pixmap.getWidth(), mundoAX((int) (hitboxMundo.x + hitboxMundo.width)));
        int inicioY = Math.max(0, mundoAY((int) (hitboxMundo.y + hitboxMundo.height)));
        int finY = Math.min(pixmap.getHeight(), mundoAY((int) hitboxMundo.y));

        for (int x = inicioX; x < finX; x++) {
            for (int y = inicioY; y < finY; y++) {
                Color.rgba8888ToColor(colorTemp, pixmap.getPixel(x, y));
                if (colorTemp.a > 0.05f) return true;
            }
        }
        return false;
    }

    public void destruir(float xMundo, float yMundo, int radio) {
        int centroX = mundoAX((int) xMundo);
        int centroY = mundoAY((int) yMundo);

        if (!estaDentro(centroX, centroY)) {
            Gdx.app.log("Mapa", "Destrucción fuera de rango: (" + centroX + "," + centroY + ")");
            return;
        }

        pixmap.setBlending(Pixmap.Blending.None);
        pixmap.setColor(0, 0, 0, 0);
        pixmap.fillCircle(centroX, centroY, radio);

        recargarTextura();
        Gdx.app.log("Mapa", "Destrucción en: (" + centroX + "," + centroY + "), radio=" + radio);
    }

    private boolean estaDentro(int x, int y) {
        return x >= 0 && y >= 0 && x < pixmap.getWidth() && y < pixmap.getHeight();
    }

    private void recargarTextura() {
        if (textura != null) textura.dispose();
        textura = new Texture(pixmap);
    }

    public void render() {
        SpriteBatch batch = RecursosGlobales.batch;
        if (textura != null) {
            batch.draw(textura, 0, 0);
        }
    }

    public void dispose() {
        if (pixmap != null) pixmap.dispose();
        if (textura != null) textura.dispose();
    }

    private int mundoAX(int xMundoPixels) {
        return MathUtils.clamp(xMundoPixels, 0, pixmap.getWidth() - 1);
    }

    private int mundoAY(int yMundoPixels) {
        return MathUtils.clamp(pixmap.getHeight() - 1 - yMundoPixels, 0, pixmap.getHeight() - 1);
    }

    public int getWidth() { return pixmap.getWidth(); }
    public int getHeight() { return pixmap.getHeight(); }
    public Pixmap getPixmap() {
        return pixmap;
    }

    public void renderDebugMapaHitbox() {
        ShapeRenderer sr = RecursosGlobales.shapeRenderer;
        Camara camara = RecursosGlobales.camaraPersonaje;

        sr.setProjectionMatrix(camara.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(new Color(1, 0, 0, 0.3f));

        for (int x = 0; x < pixmap.getWidth(); x += 4) {
            for (int y = 0; y < pixmap.getHeight(); y += 4) {
                if (esSolido(x, y)) {
                    sr.rect(x, y, 4, 4);
                }
            }
        }
        sr.end();
    }
}

