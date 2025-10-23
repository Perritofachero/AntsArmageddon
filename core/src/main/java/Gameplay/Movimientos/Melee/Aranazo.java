package Gameplay.Movimientos.Melee;

import Gameplay.Gestores.GestorColisiones;
import Gameplay.Gestores.GestorRutas;
import Gameplay.Movimientos.MovimientoMelee;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import managers.GestorAssets;

public class Aranazo extends MovimientoMelee {

    public Aranazo(GestorColisiones gestorColisiones) {
        super("Arañazo", GestorAssets.get(GestorRutas.ATLAS_PRUEBA, TextureAtlas.class),
            "Prueba", 10f, 40f, 10, 50f, gestorColisiones);
    }
}
