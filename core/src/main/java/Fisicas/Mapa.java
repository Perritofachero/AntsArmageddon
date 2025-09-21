package Fisicas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

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

    public Pixmap getPixmap() {
        return pixmap;
    }

    public boolean esSolido(int x, int y) {
        if (x < 0 || y < 0 || x >= pixmap.getWidth() || y >= pixmap.getHeight()) return false;
        int yPixmap = mundoAY(y);
        Color.rgba8888ToColor(colorTemp, pixmap.getPixel(x, yPixmap));
        return colorTemp.a > 0.05f;
    }

    public boolean colisiona(Rectangle hitbox) {
        int inicioX = Math.max(0, (int) hitbox.x);
        int finX = Math.min(pixmap.getWidth(), (int) (hitbox.x + hitbox.width));
        int inicioY = Math.max(0, (int) hitbox.y);
        int finY = Math.min(pixmap.getHeight(), (int) (hitbox.y + hitbox.height));

        for (int x = inicioX; x < finX; x++) {
            for (int y = inicioY; y < finY; y++) {
                if (esSolido(x, y)) return true;
            }
        }
        return false;
    }

    public void destruir(float xMundo, float yMundo, int radio) {
        int centroX = mundoAX((int)xMundo);
        int centroY = mundoAY((int)yMundo);

        boolean cambiado = false;

        for (int x = -radio; x <= radio; x++) {
            for (int y = -radio; y <= radio; y++) {
                if (x*x + y*y <= radio*radio) {
                    int posX = centroX + x;
                    int posY = centroY + y;
                    if (posX >= 0 && posY >= 0 && posX < pixmap.getWidth() && posY < pixmap.getHeight()) {
                        pixmap.drawPixel(posX, posY, 0x00000000);
                        cambiado = true;
                    }
                }
            }
        }

        if (cambiado) {
            if (textura != null) textura.dispose();
            textura = new Texture(pixmap);
        }
    }

    public void render(SpriteBatch batch) {
        if (textura != null) {
            batch.draw(textura, 0, 0);
        }
    }

    public void renderDebugMapaHitbox(ShapeRenderer sr, Camara camara) {
        sr.setProjectionMatrix(camara.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(new Color(1,0,0,0.3f));

        for (int x = 0; x < pixmap.getWidth(); x += 4) {
            for (int y = 0; y < pixmap.getHeight(); y += 4) {
                if (esSolido(x, y)) {
                    sr.rect(x, y, 4, 4);
                }
            }
        }
        sr.end();
    }

    public void dispose() {
        if (pixmap != null) pixmap.dispose();
        if (textura != null) textura.dispose();
    }

    private int mundoAX(int xMundo) { return MathUtils.clamp(xMundo, 0, pixmap.getWidth() - 1); }
    private int mundoAY(int yMundo) { return MathUtils.clamp(pixmap.getHeight() - 1 - yMundo, 0, pixmap.getHeight() - 1); }

    public int getWidth() { return pixmap.getWidth(); }
    public int getHeight() { return pixmap.getHeight(); }
}
