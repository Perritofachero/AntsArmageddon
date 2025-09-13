package Gameplay.Gestores;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entidades.Proyectil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestorProyectiles {

    private final List<Proyectil> proyectiles = new ArrayList<>();
    private final GestorColisiones gestorColisiones;

    public GestorProyectiles(GestorColisiones gestorColisiones) {
        this.gestorColisiones = gestorColisiones;
    }

    public void agregar(Proyectil proyectil) {
        proyectiles.add(proyectil);
        gestorColisiones.agregarObjeto(proyectil);
    }

    public void actualizar(float delta) {
        Iterator<Proyectil> it = proyectiles.iterator();
        while (it.hasNext()) {
            Proyectil proyectil = it.next();
            proyectil.mover(delta);

            if (!proyectil.isActivo()) {
                gestorColisiones.removerObjeto(proyectil);
                it.remove();
                proyectil.dispose();
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (Proyectil proyectil : proyectiles) {
            if (proyectil.isActivo()) {
                proyectil.render(batch);
            }
        }
    }

    public void dispose() {
        for (Proyectil proyectil : proyectiles) {
            proyectil.dispose();
        }
        proyectiles.clear();
    }

    public List<Proyectil> getProyectiles() {
        return proyectiles;
    }

    public GestorColisiones getGestorColisiones() {
        return gestorColisiones;
    }
}
