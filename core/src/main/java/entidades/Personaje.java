package entidades;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import logica.GestorDeColisiones;

public class Personaje extends Entidad {

    private int vida;
    private float velocidad;
    private boolean direccion; //izquierda false, derecha true.
    private Mirilla mirilla;

    public Personaje(String rutaTextura, GestorDeColisiones gestor, float x, float y, int vida, boolean direccion) {
        super(rutaTextura, gestor, x, y);
        this.vida = vida;
        this.velocidad = 200;
        this.direccion = direccion;
        this.mirilla = new Mirilla(this);
    }

    public void mover(float deltaX, float deltaY, float deltaTiempo){
        float nuevaX = this.x + deltaX * velocidad * deltaTiempo;
        float nuevaY = this.y + deltaY * velocidad * deltaTiempo;

        if (this.direccion == sprite.isFlipX()) {
            sprite.flip(true, false);
        }

        if(gestor.verificarHitbox(this, nuevaX, nuevaY)){
            this.x = nuevaX;
            this.y = nuevaY;
            update();
        }
    }

    public Proyectil atacar(){
        float poscX = this.x + (sprite.getWidth()/2); //probar esto
        float poscY = this.y + (sprite.getHeight()/2);

        float angulo = mirilla.getAnguloRad();

        if (direccion) {
            angulo = (float) Math.PI - angulo;
        }

        return new Proyectil(poscX, poscY, angulo, gestor);
    }

    public void restarVida(int danioCausado){
        this.vida -= danioCausado;

        if(this.vida < 0){
            this.vida = 0;
        }
    }

    public void render(SpriteBatch batch){
        sprite.draw(batch);
        mirilla.actualizarPosicion();
        mirilla.render(batch);
    }



    public float getX(){ return this.x; }

    public float getY(){ return this.y; }

    public float getVelocidad(){ return this.velocidad; }

    public int getVida(){ return this.vida; }

    public int getVidaMaxima(){ return 50; } //lo ponemos manualmente por ahora

    public boolean getDireccion(){ return direccion; }

    public void setDireccion(boolean nuevaDireccion){ this.direccion = nuevaDireccion; }

    public Mirilla getMirilla(){ return this.mirilla; }
}
