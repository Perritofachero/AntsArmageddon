package Gameplay.Movimientos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import entidades.personajes.Personaje;

public abstract class Movimiento implements IMovimiento {

    protected String nombre;
    protected Texture textura;
    protected Sprite sprite;
    protected int cooldown;

    public Movimiento(String nombre, Texture textura, int cooldown) {
        this.nombre = nombre;
        this.textura = textura;
        this.sprite = new Sprite(textura);
        this.cooldown = cooldown;
    }

    public abstract void ejecutar(Personaje personaje, float potencia);

    public String getNombre() { return nombre; }
    public Sprite getSprite() { return sprite; }
    public int getCooldown() { return cooldown; }
    public void setCooldown(int cooldown) { this.cooldown = cooldown; }

}
