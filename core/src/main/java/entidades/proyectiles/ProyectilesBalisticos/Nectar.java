package entidades.proyectiles.ProyectilesBalisticos;

import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.graphics.Texture;
import entidades.personajes.Personaje;
import entidades.proyectiles.ProyectilBalistico;
import managers.GestorAssets;
import utils.Constantes;

public class Nectar extends ProyectilBalistico {

    public Nectar(float x, float y, float angulo, float velocidadBase, GestorColisiones gestorColisiones, Personaje ejecutor) {
        super(x, y, angulo, velocidadBase, 10, gestorColisiones, ejecutor,
            GestorAssets.get(Constantes.PNG_1, Texture.class));

    }

}
