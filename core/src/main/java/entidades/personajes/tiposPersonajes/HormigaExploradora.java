package entidades.personajes.tiposPersonajes;

import Gameplay.Gestores.GestorColisiones;
import Gameplay.Gestores.GestorProyectiles;
import Gameplay.Movimientos.Melee.Aranazo;
import Gameplay.Movimientos.Rango.LanzaGranada;
import Gameplay.Movimientos.Rango.LanzaNectar;
import Gameplay.Movimientos.Rango.LanzaRoca;
import com.badlogic.gdx.graphics.Texture;
import entidades.personajes.Personaje;
import managers.GestorAssets;
import utils.Constantes;

public class HormigaExploradora extends Personaje {

    public HormigaExploradora(GestorColisiones gestorColisiones, GestorProyectiles gestorProyectiles, float x, float y) {
        super(GestorAssets.get(Constantes.HORMIGA_EXPLORADORA, Texture.class), gestorColisiones, gestorProyectiles, x, y, 9999, 150f);
    }

    @Override
    protected void inicializarMovimientos() {
        movimientos.add(new Aranazo(gestorColisiones));
        movimientos.add(new LanzaNectar(gestorProyectiles));
        movimientos.add(new LanzaGranada(gestorProyectiles));
        movimientos.add(new LanzaRoca(gestorProyectiles));

    }

}
