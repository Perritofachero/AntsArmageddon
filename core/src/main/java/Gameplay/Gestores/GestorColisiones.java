package Gameplay.Gestores;

import com.badlogic.gdx.math.Rectangle;
import Fisicas.Colisionable;
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
            if (otro == ignorar) continue; // <- ignoramos temporalmente al disparador

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

    public void removerObjeto(Colisionable objeto) { objetos.remove(objeto); }

}
