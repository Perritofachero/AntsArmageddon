package entidades.proyectiles;

import Fisicas.Colisionable;
import Gameplay.Gestores.GestorColisiones;
import Gameplay.Gestores.GestorFisica;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import entidades.personajes.Personaje;

public class ProyectilBalistico extends Proyectil {

    private boolean clavado = false;

    public ProyectilBalistico(float x, float y, float angulo, float velocidad, int danio,
                              float fuerzaKnockback,
                              GestorColisiones gestorColisiones, Personaje ejecutor, Texture textura) {
        super(x, y, angulo, velocidad, danio, fuerzaKnockback, gestorColisiones, ejecutor, textura);
    }

    @Override
    public void mover(float delta, GestorFisica gestorFisica) {
        if (!activo) return;

        if (clavado) {
            updateHitbox();
            if (sprite != null) sprite.setPosition(x, y);
            return;
        }

        super.mover(delta, gestorFisica);

        if (getImpacto() && activo) {
            impactar(centroHitbox().x, centroHitbox().y);
            setImpacto(false);
        }
    }

    @Override
    public void impactar(float centroX, float centroY) {
        if (!activo || clavado) return;

        for (Colisionable c : gestorColisiones.getColisionables()) {
            if (c == this) continue;

            if (c instanceof Personaje personaje && c.getHitbox().overlaps(hitbox)) {
                float centroProyectilX = centroHitbox().x;
                float centroProyectilY = centroHitbox().y;
                float dx = personaje.getX() + personaje.getWidth() / 2f - centroProyectilX;
                float dy = personaje.getY() + personaje.getHeight() / 2f - centroProyectilY;
                Vector2 dir = new Vector2(dx, dy).nor();

                float fuerzaX = dir.x * fuerzaKnockback;
                float fuerzaY = dir.y * fuerzaKnockback * 0.6f;

                personaje.recibirDanio(danio, fuerzaX, fuerzaY);

                desactivar();
                return;
            }
        }

        clavarse(centroX, centroY);
    }

    private void clavarse(float centroX, float centroY) {
        updateHitbox();
        velocidadVector.setZero();
        clavado = true;
    }
}
