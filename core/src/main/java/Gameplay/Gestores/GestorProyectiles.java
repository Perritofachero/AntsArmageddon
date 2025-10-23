package Gameplay.Gestores;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entidades.proyectiles.Proyectil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestorProyectiles {

    private final List<Proyectil> proyectiles = new ArrayList<>();
    private final GestorColisiones gestorColisiones;
    private final GestorFisica gestorFisica;

    public GestorProyectiles(GestorColisiones gestorColisiones, GestorFisica gestorFisica) {
        this.gestorColisiones = gestorColisiones;
        this.gestorFisica = gestorFisica;
    }

    public void agregar(Proyectil proyectil) {
        proyectiles.add(proyectil);
        gestorColisiones.agregarObjeto(proyectil);
    }

    private void removerProyectil(Proyectil proyectil) {
        gestorColisiones.removerObjeto(proyectil);
        proyectil.dispose();
    }

    public void actualizar(float delta) {
        Iterator<Proyectil> it = proyectiles.iterator();
        while (it.hasNext()) {
            Proyectil proyectil = it.next();
            if (!proyectil.getActivo()) {
                removerProyectil(proyectil);
                it.remove();
                continue;
            }
            proyectil.aplicarFisica(delta, gestorFisica.getFisica());
            proyectil.mover(delta, gestorFisica);
        }
    }

    public void render(SpriteBatch batch) {
        for (Proyectil proyectil : proyectiles) {
            if (proyectil.getActivo()) proyectil.render(batch);
        }
    }

    public void dispose() {
        for (Proyectil proyectil : proyectiles) {
            removerProyectil(proyectil);
        }
        proyectiles.clear();
    }

    public Proyectil getUltimoProyectilActivo() {
        for (int i = proyectiles.size() - 1; i >= 0; i--) {
            if (proyectiles.get(i).getActivo())
                return proyectiles.get(i);
        }
        return null;
    }

    public GestorColisiones getGestorColisiones() { return this.gestorColisiones; }
}
