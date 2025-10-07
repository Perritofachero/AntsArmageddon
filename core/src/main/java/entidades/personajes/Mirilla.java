package entidades.personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import managers.GestorAssets;
import utils.Constantes;

public class Mirilla {

    private float x, y, angulo, radio = 50, anguloRad;
    private Texture textura;
    private Sprite sprite;
    private Personaje personaje;
    private boolean visible;
    private float velocidadMirilla = 2f;

    public Mirilla(Personaje personaje) {
        this.personaje = personaje;
        this.textura = GestorAssets.get(Constantes.MIRA, Texture.class);
        this.sprite = new Sprite(textura);
        this.angulo = personaje.getDireccion() ? 0 : 180;
        actualizarPosicion();
    }

    public void actualizarPosicion() {
        float poscX = personaje.getX() + personaje.getSprite().getWidth() / 2;
        float poscY = personaje.getY() + personaje.getSprite().getHeight() / 2;

        anguloRad = (float) Math.toRadians(angulo);

        float distanciaX = (float) Math.cos(anguloRad) * radio * personaje.getDireccionMultiplicador();
        float distanciaY = (float) Math.sin(anguloRad) * radio;

        this.x = poscX + distanciaX;
        this.y = poscY + distanciaY;

        this.sprite.setPosition(this.x - sprite.getWidth() / 2, this.y - sprite.getHeight() / 2);
    }

    public void cambiarAngulo(int direccion) {
        angulo += direccion * velocidadMirilla;

        if (angulo > 270) {
            angulo = 270;
        } else if (angulo < 90) {
            angulo = 90;
        }
        actualizarPosicion();
    }

    public void render(SpriteBatch batch) {
        if (visible) {
            sprite.draw(batch);
        }
    }

    public void mostrarMirilla() { this.visible = true; }
    public void ocultarMirilla() { this.visible = false; }
    public float getAnguloRad() { return this.anguloRad; }
}

