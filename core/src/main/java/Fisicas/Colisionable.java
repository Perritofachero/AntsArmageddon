package Fisicas;

import com.badlogic.gdx.math.Rectangle;

public interface Colisionable {

    Rectangle getHitbox();
    Rectangle getHitboxPosicion(float x, float y);

}
