package entidades.personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import managers.GestorAssets;
import utils.Constantes;

public class Mirilla {

    //Que la mirilla se muestre siempre que el personaje no se este moviendo
    //Que su angulo se guarde, asi cuando se mueve, se oculta, y cuando se queda quieto vuelve aparecer
    //En el ultimo angulo que estuvo

    private float x, y;
    private float angulo;
    private float anguloRad;
    private float radio = 50f;

    private final Texture textura;
    private final Sprite sprite;
    private final Personaje personaje;

    private boolean visible = false;
    private final float velocidadMirilla = 2f;

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

