package Gameplay.Movimientos.Melee;

import Gameplay.Gestores.GestorColisiones;
import Gameplay.Movimientos.MovimientoMelee;
import com.badlogic.gdx.graphics.Texture;
import managers.GestorAssets;
import utils.Constantes;

public class Aranazo extends MovimientoMelee {

    public Aranazo(GestorColisiones gestorColisiones) {
        super("Arañazo", GestorAssets.get(Constantes.PNG_2, Texture.class), 10f, 40f,
            10, 550f, gestorColisiones);
    }

}

