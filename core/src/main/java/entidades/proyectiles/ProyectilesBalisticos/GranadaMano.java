package entidades.proyectiles.ProyectilesBalisticos;

import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.graphics.Texture;
import entidades.personajes.Personaje;
import entidades.proyectiles.Granada;
import managers.GestorAssets;
import utils.Constantes;

public class GranadaMano extends Granada {

    private static final int RADIO_DESTRUCCION = 50;
    private static final int RADIO_EXPANSION = 100;
    private static final float TIEMPO_EXPLOSION = 3f;

    public GranadaMano(float x, float y, float angulo, float velocidadBase, GestorColisiones gestorColisiones, Personaje ejecutor) {
        super(x, y, angulo, velocidadBase, 15, gestorColisiones, ejecutor, RADIO_DESTRUCCION, RADIO_EXPANSION,
            GestorAssets.get(Constantes.GRANADA, Texture.class), TIEMPO_EXPLOSION);
    }
}
