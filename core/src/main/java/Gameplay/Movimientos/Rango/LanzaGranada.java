package Gameplay.Movimientos.Rango;

import Gameplay.Gestores.GestorProyectiles;
import Gameplay.Movimientos.MovimientoRango;
import com.badlogic.gdx.graphics.Texture;
import entidades.personajes.Personaje;
import entidades.proyectiles.Proyectil;
import entidades.proyectiles.ProyectilesBalisticos.GranadaMano;
import managers.GestorAssets;
import utils.Constantes;

public class LanzaGranada extends MovimientoRango {

    public LanzaGranada(GestorProyectiles gestorProyectiles) {
        super("Lanza Roca", GestorAssets.get(Constantes.PNG_2, Texture.class), 300f, gestorProyectiles);
    }

    @Override
    protected Proyectil crearProyectil(float x, float y, float angulo, float velocidad, Personaje ejecutor) {
        return new GranadaMano(x, y, angulo, velocidad, gestorProyectiles.getGestorColisiones(), ejecutor);
    }

}
