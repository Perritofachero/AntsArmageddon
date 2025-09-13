package Gameplay.Movimientos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import entidades.Personaje;

public abstract class Movimiento implements IMovimiento {

    protected String nombre;
    protected Sprite sprite;
    protected float cooldown;

    public Movimiento(String nombre, Sprite sprite, float cooldown) {
        this.nombre = nombre;
        this.sprite = sprite;
        this.cooldown = cooldown;
    }

    @Override
    public abstract void ejecutar(Personaje personaje);

    public String getNombre() { return nombre; }
    public Sprite getSprite() { return sprite; }
    public float getCooldown() { return cooldown; }

    public void setCooldown(float cooldown) { this.cooldown = cooldown; }
}
