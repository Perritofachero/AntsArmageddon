package Fisicas;

import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import utils.Constantes;

public class Fisica {

    public Vector2 aplicarGravedad(Vector2 velocidad, float delta, boolean enSuelo) {
        if (!enSuelo) {
            velocidad.y += Constantes.GRAVEDAD * delta;
        }
        return velocidad;
    }

    public Rectangle moverEntidad(Rectangle hitbox, Vector2 velocidad, Mapa mapa, GestorColisiones gestorColisiones, float delta) {
       Rectangle nuevaHitbox = new Rectangle(
            hitbox.x + velocidad.x * delta,
            hitbox.y + velocidad.y * delta,
            hitbox.width,
            hitbox.height
        );

        if (mapa.colisiona(nuevaHitbox)) {
            if (velocidad.y < 0) {
                nuevaHitbox.y = (float)Math.floor(hitbox.y);
                velocidad.y = 0;
            }
        }

        for (Colisionable otro : gestorColisiones.getColisionables()) {
            if (otro.getHitbox() == hitbox) continue;
            if (nuevaHitbox.overlaps(otro.getHitbox())) {
                if (velocidad.y < 0) {
                    nuevaHitbox.y = otro.getHitbox().y + otro.getHitbox().height;
                    velocidad.y = 0;
                }
            }
        }

        return nuevaHitbox;
    }
}
