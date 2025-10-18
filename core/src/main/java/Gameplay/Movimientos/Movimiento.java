package Gameplay.Movimientos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import entidades.personajes.Personaje;

public abstract class Movimiento {

    //Agregar usos, asi se pueden acabar y los power ups pueden dar usos de habilidades

    protected String nombre;
    protected Texture textura;
    protected Sprite sprite;

    public Movimiento(String nombre, Texture textura) {
        this.nombre = nombre;
        this.textura = textura;
        this.sprite = new Sprite(textura);
    }

    public String getNombre() { return nombre; }
    public Sprite getSprite() { return sprite; }
    public void dispose() {}

}
