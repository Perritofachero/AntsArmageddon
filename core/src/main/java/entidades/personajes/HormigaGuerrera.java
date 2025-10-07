package entidades.personajes;

import Gameplay.Gestores.GestorColisiones;
import Gameplay.Gestores.GestorProyectiles;
import Gameplay.Movimientos.Rango.LanzaRoca;
import com.badlogic.gdx.graphics.Texture;
import managers.GestorAssets;
import utils.Constantes;

public class HormigaGuerrera extends Personaje{

    public HormigaGuerrera(GestorColisiones gestorColisiones, GestorProyectiles gestorProyectiles, float x, float y) {
        super(GestorAssets.get(Constantes.HORMIGA_GUERRERA, Texture.class), gestorColisiones, gestorProyectiles, x, y, 80, 80, 150f);
    }

    @Override
    protected void inicializarMovimientos() {
        Texture texturaMovimiento = GestorAssets.get(Constantes.MAPA_2, Texture.class);
        movimientos.add(new LanzaRoca("Lanzar Roca", texturaMovimiento, 1, 300f, gestorProyectiles));

    }

}
