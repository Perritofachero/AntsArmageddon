package utils;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entidades.Personaje;

public class Hud {
    private BitmapFont fuente;

    public Hud() {
        fuente = new BitmapFont();
    }

    public void mostrarVida(SpriteBatch batch, Personaje personaje) {
        String texto = personaje.getVida() + " / " + personaje.getVidaMaxima();
        fuente.draw(batch, texto, personaje.getX(), personaje.getY() + 60);
    }

    public void mostrarContador(SpriteBatch batch, int contador) {
        fuente.draw(batch, "Tiempo restante: " + contador, 10, 400);
    }

    public void dispose() {
        fuente.dispose();
    }
}
