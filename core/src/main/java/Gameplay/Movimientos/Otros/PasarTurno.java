package Gameplay.Movimientos.Otros;

import Gameplay.Gestores.GestorRutas;
import Gameplay.Movimientos.Movimiento;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import entidades.personajes.Personaje;
import managers.GestorAssets;

public class PasarTurno extends Movimiento {

    public PasarTurno() {
        super("Pasar turno", GestorAssets.get(GestorRutas.ATLAS_PRUEBA, TextureAtlas.class),
            "Prueba");
    }

    @Override
    public void ejecutar(Personaje p) {
        p.terminarTurno();
    }
}

