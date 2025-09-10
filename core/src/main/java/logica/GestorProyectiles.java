package logica;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entidades.Proyectil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestorProyectiles {

    private final List<Proyectil> proyectiles = new ArrayList<>();
    private final GestorDeColisiones gestorColisiones;

    public GestorProyectiles(GestorDeColisiones gestorColisiones) {
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
            proyectil.moverProyectil(delta);
            if (!proyectil.getActivo()) {
                it.remove();
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (Proyectil proyectil : proyectiles) {
            if (proyectil.getActivo()) {
                proyectil.render(batch);
            }
        }
    }

    public List<Proyectil> getProyectiles() {
        return proyectiles;
    }
}
