package Gameplay.Gestores;

import Fisicas.Camara;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import entidades.Entidad;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestorEntidades {

    private final List<Entidad> entidades = new ArrayList<>();
    private final GestorFisica gestorFisica;
    private final GestorColisiones gestorColisiones;

    public GestorEntidades(GestorFisica gestorFisica, GestorColisiones gestorColisiones) {
        this.gestorFisica = gestorFisica;
        this.gestorColisiones = gestorColisiones;
    }

    public void actualizar(float delta) {
        Iterator<Entidad> it = entidades.iterator();
        while (it.hasNext()) {
            Entidad entidad = it.next();

            if (!entidad.getActivo()) {
                removerEntidad(entidad);
                it.remove();
                continue;
            }

            gestorFisica.aplicarFisica(entidad, delta);
            entidad.actualizar(delta);
        }
    }

    public void render(SpriteBatch batch) {
        for (Entidad entidad : entidades) {
            if (entidad.getActivo()) entidad.render(batch);
        }
    }

    public void renderDebug(ShapeRenderer shapeRenderer, Camara camara) {
        shapeRenderer.setProjectionMatrix(camara.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        for (Entidad entidad : entidades) {
            if (!entidad.getActivo()) continue;
            Rectangle hitbox = entidad.getHitbox();
            shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        }

        shapeRenderer.end();
    }

    public void agregarEntidad(Entidad entidad) {
        if (!entidades.contains(entidad)) {
            entidades.add(entidad);
            gestorColisiones.agregarObjeto(entidad);
        }
    }

    public void removerEntidad(Entidad entidad) {
        gestorColisiones.removerObjeto(entidad);
        entidad.dispose();
    }

    public List<Entidad> getEntidades() {
        return this.entidades;
    }

    public void dispose() {
        for (Entidad entidad : entidades) {
            entidad.desactivar();
            entidad.dispose();
            gestorColisiones.removerObjeto(entidad);
        }
        entidades.clear();
    }
}
