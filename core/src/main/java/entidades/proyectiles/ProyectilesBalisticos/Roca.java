package entidades.proyectiles.ProyectilesBalisticos;

import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.graphics.Texture;
import entidades.personajes.Personaje;
import entidades.proyectiles.ProyectilExplosivo;
import managers.GestorAssets;
import utils.Constantes;

public class Roca extends ProyectilExplosivo {

    public Roca(float x, float y, float angulo, float velocidadBase, GestorColisiones gestorColisiones, Personaje ejecutor) {
        super(x, y, angulo, velocidadBase, 10, gestorColisiones, ejecutor, 80, 140,
            GestorAssets.get(Constantes.ROCA, Texture.class));
    }
}
