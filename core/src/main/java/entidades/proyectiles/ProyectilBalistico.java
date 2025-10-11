package entidades.proyectiles;

import Fisicas.Colisionable;
import Gameplay.Gestores.GestorColisiones;
import Gameplay.Gestores.GestorFisica;
import com.badlogic.gdx.graphics.Texture;
import entidades.personajes.Personaje;

public class ProyectilBalistico extends Proyectil {

    private boolean clavado = false;

    public ProyectilBalistico(float x, float y, float angulo, float velocidad, int danio,
                              GestorColisiones gestorColisiones, Personaje ejecutor, Texture textura) {
        super(x, y, angulo, velocidad, danio, gestorColisiones, ejecutor, textura);
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
                personaje.recibirDanio(danio);
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
