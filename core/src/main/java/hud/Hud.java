package hud;

import Fisicas.Camara;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import entidades.personajes.Personaje;

public class Hud {
    private final BitmapFont fuente; //cambiar la fuente con un .fnt
    private final GlyphLayout layout;

    public Hud() {
        this.fuente = new BitmapFont();
        this.layout = new GlyphLayout();
    }

    public void mostrarVida(SpriteBatch batch, Personaje personaje) {
        if (!personaje.getActivo()) return;
        String texto = personaje.getVida() + " / " + personaje.getVidaMaxima();
        layout.setText(fuente, texto);
        float posX = personaje.getX();
        float posY = personaje.getY() + personaje.getSprite().getHeight() + 20;
        fuente.draw(batch, texto, posX, posY);
    }

    public void mostrarContador(SpriteBatch batch, float contador, Camara camara) {
        String texto = String.format("Tiempo restante: %.2f s", contador);
        layout.setText(fuente, texto);

        OrthographicCamera camera = camara.getCamera();
        Viewport viewport = camara.getViewport();

        float camX = camera.position.x;
        float camY = camera.position.y;

        float offsetX = viewport.getWorldWidth() / 2f - 200;
        float offsetY = viewport.getWorldHeight() / 2f - 50;

        float posX = camX + offsetX;
        float posY = camY + offsetY;

        fuente.draw(batch, texto, posX, posY);

    }

    public void dispose() { fuente.dispose(); }
}
