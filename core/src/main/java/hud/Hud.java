package hud;

import Fisicas.Camara;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.viewport.Viewport;
import entidades.personajes.Personaje;
import managers.GestorAssets;
import utils.RecursosGlobales;

public class Hud {
    private final BitmapFont fuenteContador, fuenteVida;
    private final GlyphLayout layout;

    public Hud() {
        this.fuenteContador = GestorAssets.get("fonts/font_Contador.fnt", BitmapFont.class);
        this.fuenteVida = GestorAssets.get("fonts/font_Vida.fnt", BitmapFont.class);
        this.layout = new GlyphLayout();
    }

    public void mostrarVida(Personaje personaje) {
        if (!personaje.getActivo()) return;

        String texto = String.valueOf(personaje.getVida());
        layout.setText(fuenteVida, texto);

        float posX = personaje.getX() + personaje.getSprite().getWidth() / 2f - layout.width / 2f;
        float posY = personaje.getY() + personaje.getSprite().getHeight() + 20;

        float offset = 1f;

        fuenteVida.setColor(0, 0, 0, 1f);
        fuenteVida.draw(RecursosGlobales.batch, texto, posX - offset, posY - offset);
        fuenteVida.draw(RecursosGlobales.batch, texto, posX + offset, posY - offset);
        fuenteVida.draw(RecursosGlobales.batch, texto, posX - offset, posY + offset);
        fuenteVida.draw(RecursosGlobales.batch, texto, posX + offset, posY + offset);

        fuenteVida.setColor(1, 1, 1, 1f);
        fuenteVida.draw(RecursosGlobales.batch, texto, posX, posY);
    }


    public void mostrarContador(float contador, Camara camara) {
        String texto = String.format("%.2f", contador);
        layout.setText(fuenteContador, texto);

        OrthographicCamera camera = camara.getCamera();
        Viewport viewport = camara.getViewport();

        float camX = camera.position.x;
        float camY = camera.position.y;

        float offsetX = viewport.getWorldWidth() / 2f - 200;
        float offsetY = viewport.getWorldHeight() / 2f - 50;

        float posX = camX + offsetX;
        float posY = camY + offsetY;

        fuenteContador.draw(RecursosGlobales.batch, texto, posX, posY);

    }

    public void dispose() {  }
}
