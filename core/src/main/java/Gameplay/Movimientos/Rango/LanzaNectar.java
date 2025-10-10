package Gameplay.Movimientos.Rango;

import Gameplay.Gestores.GestorProyectiles;
import Gameplay.Movimientos.MovimientoRango;
import com.badlogic.gdx.graphics.Texture;
import entidades.personajes.Personaje;
import entidades.proyectiles.Proyectil;
import entidades.proyectiles.ProyectilesBalisticos.Nectar;
import managers.GestorAssets;
import utils.Constantes;

public class LanzaNectar extends MovimientoRango {

    public LanzaNectar(GestorProyectiles gestorProyectiles) {
        super("Lanza Nectar", GestorAssets.get(Constantes.PNG_2, Texture.class), 300f, gestorProyectiles);
    }

    @Override
    protected Proyectil crearProyectil(float x, float y, float angulo, float velocidad, Personaje ejecutor) {
        return new Nectar(x, y, angulo, velocidad, gestorProyectiles.getGestorColisiones(), ejecutor);
    }


}
