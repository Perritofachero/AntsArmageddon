package entidades.personajes;

import Gameplay.Gestores.GestorColisiones;
import Gameplay.Gestores.GestorProyectiles;
import Gameplay.Movimientos.LanzaRoca;
import com.badlogic.gdx.graphics.Texture;
import managers.GestorAssets;

public class HormigaObrera extends Personaje {

    public HormigaObrera(GestorColisiones gestorColisiones, GestorProyectiles gestorProyectiles, float x, float y) {
        super(GestorAssets.get("prueba.png", Texture.class), gestorColisiones, gestorProyectiles, x, y, 80, 80, 150f);
    }

    @Override
    protected void inicializarMovimientos() {
        Texture texturaMovimiento = GestorAssets.get("1.png", Texture.class);
        movimientos.add(new LanzaRoca("Lanzar Roca", texturaMovimiento, 1, 300f, 25, gestorProyectiles));

    }

    @Override
    public void actualizar(float delta) { }
}
