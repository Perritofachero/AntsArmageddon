package Gameplay.Movimientos.Rango;

import Gameplay.Gestores.GestorProyectiles;
import Gameplay.Movimientos.MovimientoRango;
import com.badlogic.gdx.graphics.Texture;
import entidades.personajes.Personaje;
import entidades.proyectiles.Proyectil;
import entidades.proyectiles.ProyectilesBalisticos.Roca;

public class LanzaRoca extends MovimientoRango {

    public LanzaRoca(String nombre, Texture textura, int cooldown, float velocidad, GestorProyectiles gestorProyectiles) {
        super(nombre, textura, cooldown, velocidad, gestorProyectiles);
    }

    @Override
    protected Proyectil crearProyectil(float x, float y, float angulo, float velocidad, Personaje ejecutor) {
        return new Roca(x, y, angulo, velocidad, gestorProyectiles.getGestorColisiones(), ejecutor);
    }
}
