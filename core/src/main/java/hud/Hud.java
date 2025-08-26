package hud;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entidades.Personaje;
import utils.Constantes;

public class Hud {
    private final BitmapFont fuente; //cambiar la fuente con un .fnt
    private final GlyphLayout layout;

    public Hud() {
        this.fuente = new BitmapFont();
        this.layout = new GlyphLayout();
    }

    public void mostrarVida(SpriteBatch batch, Personaje personaje) {
        String texto = personaje.getVida() + " / " + personaje.getVidaMaxima();
        layout.setText(fuente, texto);
        float posX = personaje.getX();
        float posY = personaje.getY() + personaje.getSprite().getHeight() + 20;
        fuente.draw(batch, texto, posX, posY);
    }

    public void mostrarContador(SpriteBatch batch, float contador) {
        String texto = String.format("Tiempo restante: %.2f s", contador);
        layout.setText(fuente, texto);
        float posX = Constantes.RESOLUCION_ANCHO - 200;
        float posY = Constantes.RESOLUCION_ALTO - 50;
        fuente.draw(batch, texto, posX, posY);
    }

    public void dispose() { fuente.dispose(); }
}
