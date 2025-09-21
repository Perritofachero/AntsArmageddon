package entidades.proyectiles;

import Fisicas.Colisionable;
import Fisicas.Mapa;
import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import entidades.personajes.Personaje;
import managers.GestorAssets;

public class Roca extends Proyectil {

    public Roca(float x, float y, float angulo, float velocidad, int dano, GestorColisiones gestorColisiones, Personaje ejecutor) {
        super(x, y, angulo, velocidad, dano, gestorColisiones, ejecutor);
        this.textura = GestorAssets.get("roca.png", Texture.class);
        this.sprite = new Sprite(textura);
        this.sprite.setPosition(x, y);
        this.hitbox.setWidth(sprite.getWidth());
        this.hitbox.setHeight(sprite.getHeight());
    }

    @Override
    protected void impactoProyectil(Colisionable impactado) {
        if (impactado instanceof Personaje) {
            ((Personaje) impactado).recibirDanio(danio);
        }
        this.activo = false;
    }

    @Override
    public void impactoMapa(Mapa mapa) {
        int centroX = (int) (x + hitbox.width / 2f);
        int centroY = mapa.getHeight() - 1 - (int) (y + hitbox.height / 2f);
        mapa.destruir(x + hitbox.width/2f, y + hitbox.height/2f, 10);
        System.out.println("Impacto con el mapa en (" + centroX + ", " + centroY + ")");
    }
}
