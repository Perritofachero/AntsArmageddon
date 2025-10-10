package Gameplay.Movimientos;

import Fisicas.Colisionable;
import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import entidades.personajes.Personaje;

import java.util.List;

public abstract class MovimientoMelee extends Movimiento {
    protected float anchoGolpe, altoGolpe;
    protected int danio;
    protected GestorColisiones gestorColisiones;

    public MovimientoMelee(String nombre, Texture textura, float ancho, float alto, int danio, GestorColisiones gestorColisiones) {
        super(nombre, textura);
        this.anchoGolpe = ancho;
        this.altoGolpe = alto;
        this.danio = danio;
        this.gestorColisiones = gestorColisiones;
    }

    public abstract void ejecutar(Personaje personaje);

    protected void aplicarGolpe(Personaje atacante, Rectangle area) {
        List<Colisionable> colisionados = gestorColisiones.getColisionablesEnRect(area, atacante);

        for (Colisionable c : colisionados) {
            if (c instanceof Personaje enemigo) {
                enemigo.recibirDanio(danio);
            }
        }
    }
}
