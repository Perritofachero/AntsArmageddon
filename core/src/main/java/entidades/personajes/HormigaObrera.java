package entidades.personajes;

import Gameplay.Gestores.GestorColisiones;
import Gameplay.Gestores.GestorProyectiles;
import Gameplay.Movimientos.Melee.Aranazo;
import Gameplay.Movimientos.Rango.LanzaGranada;
import Gameplay.Movimientos.Rango.LanzaNectar;
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
        movimientos.add(new Aranazo(gestorColisiones));
        movimientos.add(new LanzaNectar(gestorProyectiles));
        movimientos.add(new LanzaGranada(gestorProyectiles));
        movimientos.add(new LanzaRoca(gestorProyectiles));

    }

}
