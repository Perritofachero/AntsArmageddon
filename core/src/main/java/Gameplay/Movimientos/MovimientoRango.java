package Gameplay.Movimientos;

import Gameplay.Gestores.GestorProyectiles;
import com.badlogic.gdx.graphics.Texture;
import entidades.personajes.Personaje;

public abstract class MovimientoRango extends Movimiento {
    protected float velocidad;
    protected GestorProyectiles gestorProyectiles;

    public MovimientoRango(String nombre, Texture textura, int cooldown, float velocidad, GestorProyectiles gestorProyectiles) {
        super(nombre, textura, cooldown);
        this.velocidad = velocidad;
        this.gestorProyectiles = gestorProyectiles;
    }

    public abstract void ejecutar(Personaje personaje, float potencia);

    public float getVelocidad() { return velocidad; }
}
