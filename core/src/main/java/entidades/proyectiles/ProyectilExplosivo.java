package entidades.proyectiles;

import Fisicas.Colisionable;
import Gameplay.Gestores.GestorColisiones;
import Gameplay.Gestores.GestorFisica;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import entidades.personajes.Personaje;

public abstract class ProyectilExplosivo extends Proyectil {

    protected int radioDestruccion;
    protected int radioExpansion;

    public ProyectilExplosivo(float x, float y, float angulo, float velocidad, int danio,
                              GestorColisiones gestorColisiones, Personaje ejecutor,
                              int radioDestruccion, int radioExpansion, Texture textura) {
        super(x, y, angulo, velocidad, danio, gestorColisiones, ejecutor, textura);
        this.radioDestruccion = radioDestruccion;
        this.radioExpansion = radioExpansion;
    }

    @Override
    public void mover(float delta, GestorFisica gestorFisica) {
        if (!activo) return;

        super.mover(delta, gestorFisica);

        if (getImpacto() && activo) {
            impactar(centroHitbox().x, centroHitbox().y);
            setImpacto(false);
        }
    }

    @Override
    public void impactar(float centroX, float centroY) {
        gestorColisiones.getMapa().destruir(centroX, centroY, radioDestruccion);
        for (Colisionable c : gestorColisiones.getColisionablesRadio(centroX, centroY, radioExpansion)) {
            if (c instanceof Personaje personaje && personaje.getActivo()) {

                float distancia = personaje.distanciaAlCentro(centroX, centroY);
                float factor = (distancia <= radioDestruccion) ? 1f : factorDeDanio(distancia);
                int danioFinal = (int) (danio * factor);

                if (danioFinal > 0) {
                    // Calcular dirección de fuerza (radial desde el centro de explosión)
                    Vector2 dir = new Vector2(
                        personaje.getX() + personaje.getWidth() / 2f - centroX,
                        personaje.getY() + personaje.getHeight() / 2f - centroY
                    ).nor();

                    float fuerzaBase = 450f;
                    float fuerzaX = dir.x * fuerzaBase * factor;
                    float fuerzaY = dir.y * fuerzaBase * factor * 0.8f;

                    personaje.recibirDanio(danioFinal, fuerzaX, fuerzaY);
                }
            } else if (!(c instanceof Personaje)) {
                c.desactivar();
            }
        }


        desactivar();
    }

    protected float factorDeDanio(float distancia) {
        if (distancia <= radioDestruccion) return 1f;
        if (distancia >= radioExpansion) return 0f;
        return 1f - (distancia - radioDestruccion) / (radioExpansion - radioDestruccion);
    }
}
