package Gameplay.Gestores;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entidades.proyectiles.Proyectil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestorProyectiles {

    private final List<Proyectil> proyectiles = new ArrayList<>();
    private final GestorColisiones gestorColisiones;
    private final GestorFisica gestorFisicas;

    public GestorProyectiles(GestorColisiones gestorColisiones, GestorFisica gestorFisicas) {
        this.gestorColisiones = gestorColisiones;
        this.gestorFisicas = gestorFisicas;
    }

    public void agregar(Proyectil proyectil) {
        proyectiles.add(proyectil);
        gestorColisiones.agregarObjeto(proyectil);
    }

    public void actualizar(float delta) {
        Iterator<Proyectil> it = proyectiles.iterator();
        while (it.hasNext()) {
            Proyectil proyectil = it.next();
            proyectil.mover(delta, gestorFisicas);

            if (!proyectil.getActivo()) {
                proyectil.dispose();
                it.remove();
            }
        }
    }

    public void render(SpriteBatch batch) {
        batch.enableBlending();
        for (Proyectil proyectil : proyectiles) {
            if (proyectil.getActivo()) proyectil.render(batch);
        }
    }

    public void dispose() {
        for (Proyectil proyectil : proyectiles) proyectil.dispose();
        proyectiles.clear();
    }

    public GestorColisiones getGestorColisiones() { return this.gestorColisiones; }
}
