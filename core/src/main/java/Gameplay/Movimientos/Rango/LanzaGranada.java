package Gameplay.Movimientos.Rango;

import Gameplay.Gestores.GestorProyectiles;
import Gameplay.Movimientos.MovimientoRango;
import com.badlogic.gdx.graphics.Texture;
import entidades.personajes.Personaje;
import entidades.proyectiles.Proyectil;
import entidades.proyectiles.ProyectilesBalisticos.GranadaMano;

public class LanzaGranada extends MovimientoRango {

    public LanzaGranada(String nombre, Texture textura, int cooldown, float velocidad, GestorProyectiles gestorProyectiles) {
        super(nombre, textura, cooldown, velocidad, gestorProyectiles);
    }

    @Override
    protected Proyectil crearProyectil(float x, float y, float angulo, float velocidad, Personaje ejecutor) {
        return new GranadaMano(x, y, angulo, velocidad, gestorProyectiles.getGestorColisiones(), ejecutor);
    }

}
