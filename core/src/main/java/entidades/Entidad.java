package entidades;

import com.badlogic.gdx.math.Rectangle;
import Fisicas.Colisionable;

public abstract class Entidad implements Colisionable {

    protected float x, y;
    protected Rectangle hitbox;

    public Entidad(float x, float y){
        this.x = x;
        this.y = y;
        this.hitbox = new Rectangle(x, y, 0, 0);
    }

    protected void update() {
        hitbox.setPosition(x, y);
    }

    public void mover(float dx, float dy) {
        this.x += dx;
        this.y += dy;
        update();
    }

    @Override
    public Rectangle getHitboxPosicion(float x, float y) { return new Rectangle(x, y, hitbox.getWidth(), hitbox.getHeight()); }
    public Rectangle getHitbox() { return hitbox; }
    public void dispose() { }

}
