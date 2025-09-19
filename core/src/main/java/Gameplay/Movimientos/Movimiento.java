package Gameplay.Movimientos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import entidades.Personaje;

public abstract class Movimiento implements IMovimiento {

    protected String nombre;
    protected Texture textura;
    protected Sprite sprite;
    protected float cooldown;

    public Movimiento(String nombre, Texture textura, float cooldown) {
        this.nombre = nombre;
        this.textura = textura;
        this.sprite = new Sprite(textura);
        this.cooldown = cooldown;
    }

    @Override
    public abstract void ejecutar(Personaje personaje);

    public String getNombre() { return nombre; }
    public Sprite getSprite() { return sprite; }
    public float getCooldown() { return cooldown; }
    public void setCooldown(float cooldown) { this.cooldown = cooldown; }
}
