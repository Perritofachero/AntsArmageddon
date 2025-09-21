package entidades.proyectiles;

import Fisicas.Colisionable;
import Fisicas.Mapa;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import entidades.personajes.Personaje;
import utils.Constantes;

public abstract class Proyectil implements Colisionable {

    protected float x, y;
    protected boolean activo = true;
    protected int danio;
    protected GestorColisiones gestorColisiones;
    protected Personaje ejecutor;
    protected float tiempoGracia = 2f;
    protected float tiempoTranscurrido = 0f;
    protected Vector2 posAnterior = new Vector2();
    protected Vector2 velocidadVector = new Vector2();
    protected Rectangle hitbox;
    protected Sprite sprite;
    protected Texture textura;

    public Proyectil(float x, float y, float angulo, float velocidad, int danio, GestorColisiones gestorColisiones, Personaje ejecutor) {
        this.x = x;
        this.y = y;
        this.danio = danio;
        this.gestorColisiones = gestorColisiones;
        this.ejecutor = ejecutor;
        this.hitbox = new Rectangle(x, y, 0, 0);
        this.velocidadVector.x = (float) Math.cos(angulo) * velocidad * (ejecutor != null ? ejecutor.getDireccionMultiplicador() : 1);
        this.velocidadVector.y = (float) Math.sin(angulo) * velocidad;
    }

    public void mover(float delta) {
        tiempoTranscurrido += delta;
        posAnterior.set(x, y);

        velocidadVector.y += Constantes.GRAVEDAD * delta;

        x += velocidadVector.x * delta;
        y += velocidadVector.y * delta;
        updateHitbox();

        Personaje ignorar = (ejecutor != null && tiempoTranscurrido < tiempoGracia) ? ejecutor : null;
        Colisionable impactado = gestorColisiones.verificarTrayectoria(
            new Vector2(posAnterior.x + hitbox.width/2f, posAnterior.y + hitbox.height/2f),
            new Vector2(x + hitbox.width/2f, y + hitbox.height/2f),
            ignorar, this
        );

        if (impactado != null) {
            x = posAnterior.x;
            y = posAnterior.y;
            impactoProyectil(impactado);
            desactivar();
        }
    }

    public void impactoMapa(Mapa mapa, int radio) {
        int centroX = (int) (x + hitbox.width / 2f);
        int centroY = (int) (y + hitbox.height / 2f);
        mapa.destruir(centroX, centroY, radio);
    }

    public void desactivar() {
        activo = false;
        gestorColisiones.removerObjeto(this);
    }

    protected void updateHitbox() {
        if (hitbox != null) hitbox.setPosition(x, y);
    }

    protected abstract void impactoProyectil(Colisionable impactado);
    public abstract void impactoMapa(Mapa mapa);

    public void render(SpriteBatch batch) {
        if (sprite != null) {
            sprite.setPosition(x, y);
            sprite.draw(batch);
        }
    }

    public void dispose() {
        //if (textura != null) textura.dispose();
    }

    public boolean isActivo() { return activo; }
    public Rectangle getHitbox() { return hitbox; }
    public Rectangle getHitboxPosicion(float x, float y) { return new Rectangle(x, y, hitbox.getWidth(), hitbox.getHeight()); }
}
