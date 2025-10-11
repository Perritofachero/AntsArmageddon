package entidades.proyectiles.ProyectilesBalisticos;

import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.graphics.Texture;
import entidades.personajes.Personaje;
import entidades.proyectiles.Granada;
import managers.GestorAssets;
import utils.Constantes;

public class GranadaMano extends Granada {

    public GranadaMano(float x, float y, float angulo, float velocidadBase, GestorColisiones gestorColisiones, Personaje ejecutor) {
        super(x, y, angulo, velocidadBase, 15, gestorColisiones, ejecutor, 50,
            100, GestorAssets.get(Constantes.GRANADA, Texture.class), 3f);
    }
}
