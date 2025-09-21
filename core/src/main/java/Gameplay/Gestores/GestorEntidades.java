package Gameplay.Gestores;

import Fisicas.Camara;
import Fisicas.Fisica;
import Fisicas.Mapa;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import entidades.Entidad;
import java.util.ArrayList;
import java.util.List;

public class GestorEntidades {

    private List<Entidad> entidades = new ArrayList<>();

    public void actualizar(float delta, Fisica fisica, Mapa mapa) {
        for (Entidad entidadAux : entidades) {
            entidadAux.aplicarFisica(fisica, mapa, delta);

            entidadAux.actualizar(delta);
        }
    }

    public void render(SpriteBatch batch) {
        for (Entidad entidadAux : entidades) {
            entidadAux.render(batch);
        }
    }

    public void renderDebug(ShapeRenderer shapeRenderer, Camara camara) {
        shapeRenderer.setProjectionMatrix(camara.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        for (Entidad entidadAux : entidades) {
            Rectangle hitbox = entidadAux.getHitbox();
            shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        }

        shapeRenderer.end();
    }

    public void agregarEntidad(Entidad entidad) { entidades.add(entidad); }
    public void removerEntidad(Entidad entidad) { entidades.remove(entidad); }

    public List<Entidad> getEntidades() {
        return entidades;
    }
}
