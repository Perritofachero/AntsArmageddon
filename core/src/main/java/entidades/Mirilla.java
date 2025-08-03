package entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Mirilla {

    private float x, y, angulo, radio, anguloRad;
    private Texture textura;
    private Sprite sprite;
    private Personaje personaje;
    private boolean visible;
    private float velocidadMirilla = 30f;

    public Mirilla(Personaje personaje){
        this.personaje = personaje;
        this.radio = 50;
        this.textura = new Texture("mira.png");
        this.sprite = new Sprite(textura);
        this.angulo = 180;
    }

    public void actualizarPosicion() {
        float poscX = personaje.getX() + personaje.sprite.getWidth() / 2;
        float poscY = personaje.getY() + personaje.sprite.getHeight() / 2;

        boolean direccion = personaje.getDireccion();

        anguloRad = (float) Math.toRadians(angulo);

        float distanciaX = (float) Math.cos(anguloRad) * radio;
        float distanciaY = (float) Math.sin(anguloRad) * radio;

        if (direccion) {
            distanciaX = -distanciaX;
        } else {
            //la mirilla esta mas lejos dependiendo si mira a la derecha o a la izquierda
            //por el momento lo ajustamos asi manualmente hasta encontrar solucion.
            distanciaX -= 13;
        }

        this.x = poscX + distanciaX;
        this.y = poscY + distanciaY;

        this.sprite.setPosition(this.x, this.y);
    }

    public void cambiarAngulo(float delta){
        angulo += delta;

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
