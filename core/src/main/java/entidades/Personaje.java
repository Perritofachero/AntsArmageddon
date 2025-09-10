package entidades;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import logica.GestorDeColisiones;

public class Personaje extends Entidad {

    private int vida;
    private float velocidad;
    private Mirilla mirilla;
    private boolean direccion;

    public Personaje(String rutaTextura, GestorDeColisiones gestor, float x, float y) {
        super(rutaTextura, gestor, x, y);
        this.vida = 50;
        this.velocidad = 200;
        this.mirilla = new Mirilla(this);
        this.direccion = false;
    }

    public void mover(float deltaX, float deltaY, float deltaTiempo){
        float nuevaX = this.x + deltaX * velocidad * deltaTiempo;
        float nuevaY = this.y + deltaY * velocidad * deltaTiempo;

        if (deltaX < 0) {
            direccion = false;
            if (!sprite.isFlipX()) {
                sprite.flip(true, false);
            }
        } else if (deltaX > 0) {
            direccion = true;
            if (sprite.isFlipX()) {
                sprite.flip(true, false);
            }
        }

        if(gestor.verificarHitbox(this, nuevaX, nuevaY)){
            this.x = nuevaX;
            this.y = nuevaY;
            update();
        }
    }

    public Proyectil atacar(){
        float poscX = this.x + (sprite.getWidth()/2);
        float poscY = this.y + (sprite.getHeight()/2);

        float angulo = mirilla.getAnguloRad();

        if (direccion) {
            angulo = (float) Math.PI - angulo;
        }

        float desplazamientoCentro = Math.max(sprite.getWidth(), sprite.getHeight()) / 2f + 70f;

        float balaX = poscX + MathUtils.cos(angulo) * desplazamientoCentro;
        float balaY = poscY + MathUtils.sin(angulo) * desplazamientoCentro;

        return new Proyectil(balaX, balaY, angulo, gestor);
    }

    public void restarVida(int danioCausado){
        this.vida -= danioCausado;

        if(this.vida < 0){
            this.vida = 0;
        }
    }

    public void render(SpriteBatch batch){
        sprite.setPosition(this.x, this.y);
        sprite.draw(batch);

        mirilla.actualizarPosicion();
        mirilla.render(batch);
    }




    public float getX() { return this.x; }

    public float getY() { return this.y; }

    public int getVida() { return this.vida; }

    public int getVidaMaxima() { return 50; } //lo ponemos manualmente por ahora

    public boolean getDireccion() { return direccion; }

    public Sprite getSprite() { return this.sprite; }

    public Mirilla getMirilla() { return this.mirilla; }
}
