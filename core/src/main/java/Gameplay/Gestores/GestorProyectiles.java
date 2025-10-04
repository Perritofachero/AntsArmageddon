package Gameplay.Gestores;

import Fisicas.Mapa;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entidades.proyectiles.Proyectil;
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

    public void actualizar(float delta, Mapa mapa) {
        Iterator<Proyectil> it = proyectiles.iterator();
        while (it.hasNext()) {
            Proyectil proyectil = it.next();
            proyectil.mover(delta);

            if (mapa.colisiona(proyectil.getHitbox())) {
                float centroX = proyectil.getHitbox().x + proyectil.getHitbox().width / 2f;
                float centroY = proyectil.getHitbox().y + proyectil.getHitbox().height / 2f;
                proyectil.detonar(centroX, centroY);
            }

            if (!proyectil.isActivo()) {
                proyectil.dispose();
                it.remove();
            }
        }
    }

    public void render(SpriteBatch batch) {
        batch.enableBlending();
        for (Proyectil proyectil : proyectiles) {
            if (proyectil.isActivo()) proyectil.render(batch);
        }
    }

    public void dispose() {
        for (Proyectil proyectil : proyectiles) proyectil.dispose();
        proyectiles.clear();
    }

    public GestorColisiones getGestorColisiones() { return this.gestorColisiones; }
}
