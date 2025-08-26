package entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Mirilla {

    private float x, y, angulo = 180, radio = 50, anguloRad;
    private Texture textura;
    private Sprite sprite;
    private Personaje personaje;
    private boolean visible;
    private float velocidadMirilla = 4f;

    public Mirilla(Personaje personaje){
        this.personaje = personaje;
        this.textura = new Texture("mira.png");
        this.sprite = new Sprite(textura);
    }

    public void actualizarPosicion() {
        float poscX = personaje.getX() + personaje.sprite.getWidth() / 2;
        float poscY = personaje.getY() + personaje.sprite.getHeight() / 2;

        anguloRad = (float) Math.toRadians(angulo);

        float direccionMultiplier = personaje.getDireccion() ? -1 : 1;

        float distanciaX = (float) Math.cos(anguloRad) * radio * direccionMultiplier;
        float distanciaY = (float) Math.sin(anguloRad) * radio;

        this.x = poscX + distanciaX;
        this.y = poscY + distanciaY;

        this.sprite.setPosition(this.x - sprite.getWidth() / 2, this.y - sprite.getHeight() / 2);
    }

    public void cambiarAngulo(int direccion){
        angulo += direccion * velocidadMirilla;

        if(angulo > 270){
            angulo = 270;
        }else if(angulo < 90){
            angulo = 90;
        }
        actualizarPosicion();
    }

    public void render(SpriteBatch batch) {
        if(visible){
            sprite.draw(batch);

        }
    }


    public void mostrarMirilla(){ this.visible = true; }

    public void ocultarMirilla(){ this.visible = false; }

    public float getAnguloRad(){ return this.anguloRad; }
}
