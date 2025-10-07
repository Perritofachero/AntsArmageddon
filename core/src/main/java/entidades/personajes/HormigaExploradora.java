package entidades.personajes;

import Gameplay.Gestores.GestorColisiones;
import Gameplay.Gestores.GestorProyectiles;
import Gameplay.Movimientos.Rango.LanzaGranada;
import Gameplay.Movimientos.Rango.LanzaNectar;
import Gameplay.Movimientos.Rango.LanzaRoca;
import com.badlogic.gdx.graphics.Texture;
import managers.GestorAssets;
import utils.Constantes;

public class HormigaExploradora extends Personaje {

    public HormigaExploradora(GestorColisiones gestorColisiones, GestorProyectiles gestorProyectiles, float x, float y) {
        super(GestorAssets.get(Constantes.HORMIGA_EXPLORADORA, Texture.class), gestorColisiones, gestorProyectiles, x, y, 80, 80, 150f);
    }

    @Override
    protected void inicializarMovimientos() {
        Texture texturaMovimiento = GestorAssets.get(Constantes.MAPA_1, Texture.class);
        movimientos.add(new LanzaRoca("Lanzar Roca", texturaMovimiento, 1, 300f, gestorProyectiles));
        movimientos.add(new LanzaNectar("Lanza Nectar", texturaMovimiento, 1, 300f, gestorProyectiles));
        movimientos.add(new LanzaGranada("Lanza Granada", texturaMovimiento, 1, 300f, gestorProyectiles));

    }

}
