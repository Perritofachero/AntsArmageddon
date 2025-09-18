package Gameplay.Gestores;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import Fisicas.Colisionable;
import com.badlogic.gdx.math.Vector2;

import java.util.List;
import java.util.ArrayList;

public class GestorColisiones {

    private List<Colisionable> objetos;

    public GestorColisiones() {
        objetos = new ArrayList<>();
    }

    public boolean verificarHitbox(Colisionable objeto, float nuevaX, float nuevaY, Colisionable ignorar) {
        Rectangle hitboxPropuesta = objeto.getHitboxPosicion(nuevaX, nuevaY);

        for (Colisionable otro : objetos) {
            if (otro == objeto) continue;
            if (otro == ignorar) continue;

            if (hitboxPropuesta.overlaps(otro.getHitbox())) {
                return false;
            }
        }
        return true;
    }

    public void agregarObjeto(Colisionable objeto) {
        if (!objetos.contains(objeto)) {
            objetos.add(objeto);
        }
    }

    public Colisionable verificarTrayectoria(Vector2 inicio, Vector2 fin, Colisionable ignorar, Colisionable self) {
        for (Colisionable otro : objetos) {
            if (otro == ignorar || otro == self) continue;
            Rectangle rect = otro.getHitbox();
            if (Intersector.intersectSegmentRectangle(inicio, fin, rect)) {
                return otro;
            }
        }
        return null;
    }

    public void removerObjeto(Colisionable objeto) { objetos.remove(objeto); }

}
