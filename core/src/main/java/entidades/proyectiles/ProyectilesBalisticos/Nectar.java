package entidades.proyectiles.ProyectilesBalisticos;

import Gameplay.Gestores.GestorColisiones;
import Gameplay.Gestores.GestorRutas;
import com.badlogic.gdx.graphics.Texture;
import entidades.personajes.Personaje;
import entidades.proyectiles.ProyectilBalistico;
import managers.GestorAssets;

public class Nectar extends ProyectilBalistico {

    public Nectar(float x, float y, float angulo, float velocidadBase,
                  GestorColisiones gestorColisiones, Personaje ejecutor) {
        super(x, y, angulo, velocidadBase, 10, 300f, gestorColisiones, ejecutor,
            GestorAssets.get(GestorRutas.PNG_1, Texture.class));
    }
}

