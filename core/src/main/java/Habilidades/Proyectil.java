package Habilidades;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import entidades.Entidad;
import utils.GestorDeColisiones;

public class Proyectil extends Entidad {

    private float velocidad = 200;
    private float angulo;
    private boolean activo;

    public Proyectil(float x, float y, float angulo, GestorDeColisiones gestor){
        super("proyectil.png", gestor, x, y);
        this.velocidad = velocidad;
        this.angulo = angulo;
        this.activo = true;
    }

    public void moverProyectil(float delta){
        float nuevaX = this.x + MathUtils.cos(angulo) * velocidad * delta;
        float nuevaY = this.y + MathUtils.sin(angulo) * velocidad * delta;

        if(gestor.verificarHitbox(this, nuevaX, nuevaY)){
            this.x = nuevaX;
            this.y = nuevaY;
            update();
        }else{
            gestor.removerObjeto(this);
            activo = false;
        }
    }

    public void render(SpriteBatch batch){
        batch.draw(sprite, this.x, this.y);
    }

    public boolean getActivo(){
        return this.activo;
    }
}
