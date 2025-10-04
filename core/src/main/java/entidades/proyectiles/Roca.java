package entidades.proyectiles;

import Fisicas.Colisionable;
import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import entidades.personajes.Personaje;
import managers.GestorAssets;

public class Roca extends Proyectil {

    public Roca(float x, float y, float angulo, float velocidadBase, GestorColisiones gestorColisiones, Personaje ejecutor) {
        super(x, y, angulo, velocidadBase, 10, gestorColisiones, ejecutor, 80, 140);

        this.textura = GestorAssets.get("roca.png", Texture.class);
        this.sprite = new Sprite(textura);
        this.sprite.setPosition(x, y);
        this.hitbox.setWidth(sprite.getWidth());
        this.hitbox.setHeight(sprite.getHeight());
    }

    @Override
    public void detonar(float centroX, float centroY) {
        gestorColisiones.getMapa().destruir(centroX, centroY, this.radioDestruccion);

        for (Colisionable colisionable : gestorColisiones.getColisionablesRadio(centroX, centroY, this.radioExpansion)) {
            if (colisionable instanceof Personaje personaje && personaje.getActivo()) {
                float distancia = personaje.distanciaAlCentro(centroX, centroY);

                if (distancia <= radioDestruccion) {
                    personaje.recibirDanio(danio);

                } else {
                    float factor = Math.max(0, 1 - (distancia - radioDestruccion) / (radioExpansion - radioDestruccion));
                    int danioFinal = (int)(danio * factor);
                    if (danioFinal > 0) {
                        personaje.recibirDanio(danioFinal);
                    }
                }
            } else if (colisionable instanceof Proyectil p) {
                p.desactivar();
            }
        }

        this.activo = false;
    }

}
