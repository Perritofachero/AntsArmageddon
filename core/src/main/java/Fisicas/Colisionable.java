package Fisicas;

import com.badlogic.gdx.math.Rectangle;

public interface Colisionable {

    Rectangle getHitbox();
    Rectangle getHitboxPosicion(float x, float y);
    void desactivar();
    float getX();
    float getY();
    void setX(float x);
    void setY(float y);
    boolean getActivo();

}
