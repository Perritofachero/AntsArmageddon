package Gameplay.Movimientos.Rango;

import Gameplay.Gestores.GestorProyectiles;
import Gameplay.Movimientos.MovimientoRango;
import com.badlogic.gdx.graphics.Texture;
import entidades.personajes.Personaje;
import entidades.proyectiles.Proyectil;
import entidades.proyectiles.ProyectilesBalisticos.Roca;
import managers.GestorAssets;
import utils.Constantes;

public class LanzaRoca extends MovimientoRango {

    public LanzaRoca(GestorProyectiles gestorProyectiles) {
        super("Lanza Roca", GestorAssets.get(Constantes.PNG_2, Texture.class), 300f, gestorProyectiles);
    }
//Ajustar las variables y el texture para los movimientos mas adelante

    @Override
    protected Proyectil crearProyectil(float x, float y, float angulo, float velocidad, Personaje ejecutor) {
        return new Roca(x, y, angulo, velocidad, gestorProyectiles.getGestorColisiones(), ejecutor);
    }
}
