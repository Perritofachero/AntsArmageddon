package entidades;

import Fisicas.Colisionable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Limite implements Colisionable {
    private Rectangle hitbox;

    public Limite(float x, float y, float ancho, float alto) {
        this.hitbox = new Rectangle(x, y, ancho, alto);
    }

    public void draw(ShapeRenderer renderer) {
        renderer.setColor(Color.YELLOW);
        renderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    @Override
    public Rectangle getHitbox() {
        return hitbox;
    }

    @Override
    public Rectangle getHitboxPosicion(float x, float y) {
        return hitbox;
    }

}
