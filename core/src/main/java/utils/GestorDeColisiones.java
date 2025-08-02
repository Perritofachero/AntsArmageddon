package utils;

import com.badlogic.gdx.math.Rectangle;
import java.util.List;
import java.util.ArrayList;

public class GestorDeColisiones {

    private List<Colisionable> objetos;

    public GestorDeColisiones() {
        objetos = new ArrayList<>();
    }

    public boolean verificarHitbox(Colisionable objeto, float x, float y){
        Rectangle hitboxAux = objeto.getHitboxPosicion(x, y);
        int i = 0;
        boolean puedeMoverse = true;

        while(i < objetos.size() && puedeMoverse){
            Colisionable objetoColisionable = objetos.get(i);
            if(objetoColisionable != objeto){
                if(hitboxAux != null && objeto != null && hitboxAux.overlaps(objetoColisionable.getHitbox())){
                    puedeMoverse = false;
                }
            }

            i++;
        }

        return puedeMoverse;
    }

    public void agregarObjeto(Colisionable objeto) {
        if (!objetos.contains(objeto)) {
            objetos.add(objeto);
        }
    }

    public void removerObjeto(Colisionable objeto) {
        objetos.remove(objeto);
    }

}
