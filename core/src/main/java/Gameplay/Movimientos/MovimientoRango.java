package Gameplay.Movimientos;

import Gameplay.Gestores.GestorProyectiles;
import com.badlogic.gdx.graphics.Texture;
import entidades.personajes.Personaje;

public abstract class MovimientoRango extends Movimiento {

    protected float velocidad;
    protected int danio;
    protected GestorProyectiles gestorProyectiles;

    public MovimientoRango(String nombre, Texture textura, int cooldown, float velocidad, int danio, GestorProyectiles gestorProyectiles) {
        super(nombre, textura, cooldown);
        this.velocidad = velocidad;
        this.danio = danio;
        this.gestorProyectiles = gestorProyectiles;
    }

    @Override
    public abstract void ejecutar(Personaje personaje);

    public float getVelocidad() { return velocidad; }
    public int getDano() { return danio; }
}
