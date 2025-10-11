package entidades.proyectiles;

import Fisicas.Colisionable;
import Gameplay.Gestores.GestorColisiones;
import Gameplay.Gestores.GestorFisica;
import com.badlogic.gdx.graphics.Texture;
import entidades.personajes.Personaje;
import utils.Constantes;

public class Granada extends Proyectil {

    private float tiempoVida;
    private float tiempoTranscurridoExplosion = 0f;
    private float coeficienteRebote = 0.6f;
    private static final float VELOCIDAD_MINIMA = 5f;

    private int radioDestruccion;
    private int radioExpansion;

    //Por el momento mantenemos granada como hijo de proyectil aunque no use alguno de sus metodos
    //sigue funcionando. Tal vez en un futuro la saquemos.

    public Granada(float x, float y, float angulo, float velocidad, int danio,
                   GestorColisiones gestorColisiones, Personaje ejecutor,
                   int radioDestruccion, int radioExpansion, Texture textura,
                   float tiempoVida) {
        super(x, y, angulo, velocidad, danio, gestorColisiones, ejecutor, textura);
        this.radioDestruccion = radioDestruccion;
        this.radioExpansion = radioExpansion;
        this.tiempoVida = tiempoVida;
    }

    @Override
    public void mover(float delta, GestorFisica gestorFisica) {
        if (!activo) return;

        tiempoTranscurrido += delta;
        tiempoTranscurridoExplosion += delta;

        if (tiempoTranscurridoExplosion >= tiempoVida) {
            explotar();
            return;
        }

        Personaje ignorar = (ejecutor != null && tiempoTranscurrido < Constantes.TIEMPO_GRACIA) ? ejecutor : null;

        float nuevaX = x + velocidadVector.x * delta;
        float nuevaY = y + velocidadVector.y * delta;

        boolean colisionX = !gestorColisiones.verificarMovimiento(this, nuevaX, y, ignorar);
        boolean colisionY = !gestorColisiones.verificarMovimiento(this, x, nuevaY, ignorar);

        if (colisionX) velocidadVector.x *= -coeficienteRebote;
        else x = nuevaX;

        if (colisionY) {
            velocidadVector.y *= -coeficienteRebote;
            if (velocidadVector.y > 0) y += 1f;
        } else {
            y = nuevaY;
        }

        if (velocidadVector.len2() < VELOCIDAD_MINIMA) velocidadVector.setZero();

        updateHitbox();
        if (sprite != null) sprite.setPosition(x, y);
    }

    private void explotar() {
        gestorColisiones.getMapa().destruir(centroHitbox().x, centroHitbox().y, radioDestruccion);

        for (Colisionable c : gestorColisiones.getColisionablesRadio(centroHitbox().x, centroHitbox().y, radioExpansion)) {
            if (c instanceof Personaje personaje && personaje.getActivo()) {
                float distancia = personaje.distanciaAlCentro(x, y);
                float factor = (distancia <= radioDestruccion) ? 1f : factorDeDanio(distancia);
                int danioFinal = (int)(danio * factor);
                if (danioFinal > 0) personaje.recibirDanio(danioFinal);
            } else if (!(c instanceof Personaje)) {
                c.desactivar();
            }
        }

        desactivar();
    }

    private float factorDeDanio(float distancia) {
        if (distancia <= radioDestruccion) return 1f;
        if (distancia >= radioExpansion) return 0f;
        return 1f - (distancia - radioDestruccion) / (radioExpansion - radioDestruccion);
    }

    @Override  public void impactar(float centroX, float centroY) { }
}
