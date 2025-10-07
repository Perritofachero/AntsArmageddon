package entidades.personajes;

import Gameplay.Gestores.GestorColisiones;
import Gameplay.Gestores.GestorProyectiles;
import Gameplay.Movimientos.Rango.LanzaRoca;
import com.badlogic.gdx.graphics.Texture;
import managers.GestorAssets;
import utils.Constantes;

public class HormigaObrera extends Personaje {

    public HormigaObrera(GestorColisiones gestorColisiones, GestorProyectiles gestorProyectiles, float x, float y) {
        super(GestorAssets.get(Constantes.HORMIGA_OBRERA, Texture.class), gestorColisiones, gestorProyectiles, x, y, 80, 80, 150f);
    }

    @Override
    protected void inicializarMovimientos() {
        Texture texturaMovimiento = GestorAssets.get(Constantes.PNG_1, Texture.class);
        movimientos.add(new LanzaRoca("Lanzar Roca", texturaMovimiento, 1, 300f, gestorProyectiles));

    }

}
