package Gameplay.Gestores;

import Fisicas.Camara;
import Fisicas.Mapa;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import Fisicas.Colisionable;
import com.badlogic.gdx.math.Vector2;
import entidades.personajes.Personaje;

import java.util.List;
import java.util.ArrayList;

public class GestorColisiones {

    private final List<Colisionable> colisionables;
    private final Mapa mapa;

    public GestorColisiones(Mapa mapa) {
        this.mapa = mapa;
        this.colisionables = new ArrayList<>();
    }

    public boolean colisionaConAlgo(Colisionable ejecutor, Rectangle area) {
        for (Colisionable otro : colisionables) {
            if (otro == ejecutor) continue;
            if (area.overlaps(otro.getHitbox())) {
                return true;
            }
        }

        if (mapa != null && mapa.colisiona(area)) {
            return true;
        }

        return false;
    }

    public boolean verificarMovimiento(Colisionable objeto, float nuevaX, float nuevaY) {
        Rectangle hitbox = objeto.getHitboxPosicion(nuevaX, nuevaY);
        return !colisionaConAlgo(objeto, hitbox);
    }

    public Colisionable verificarTrayectoria(Vector2 inicio, Vector2 fin, Colisionable ignorar, Colisionable self) {
        for (Colisionable otro : colisionables) {
            if (otro == ignorar || otro == self) continue;
            Rectangle rect = otro.getHitbox();
            if (Intersector.intersectSegmentRectangle(inicio, fin, rect)) {
                return otro;
            }
        }
        return null;
    }

    public List<Colisionable> getColisionablesRadio(float x, float y, float radio) {
        List<Colisionable> dentroDelRadio = new ArrayList<>();
        float radio2 = radio * radio;

        for (Colisionable c : colisionables) {
            Rectangle hitbox = c.getHitbox();
            float centroX = hitbox.x + hitbox.width / 2f;
            float centroY = hitbox.y + hitbox.height / 2f;

            float dx = centroX - x;
            float dy = centroY - y;
            float dist2 = dx * dx + dy * dy;

            if (dist2 <= radio2) {
                dentroDelRadio.add(c);
            }
        }

        return dentroDelRadio;
    }

    public void agregarObjeto(Colisionable objeto) {
        if (!colisionables.contains(objeto)) {
            colisionables.add(objeto);
        }
    }

    public void renderDebugHitboxes(ShapeRenderer sr, Camara camara) {
        sr.setProjectionMatrix(camara.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.RED);

        for (Colisionable c : colisionables) {
            Rectangle r = c.getHitbox();
            sr.rect(r.x, r.y, r.width, r.height);
        }

        sr.end();
    }

    public List<Colisionable> getColisionables() { return colisionables; }
    public void removerObjeto(Colisionable objeto) { colisionables.remove(objeto); }
    public Mapa getMapa() { return this.mapa; }
}
