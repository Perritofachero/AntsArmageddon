package entidades.proyectiles;

import Fisicas.Colisionable;
import Gameplay.Gestores.GestorFisica;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import entidades.personajes.Personaje;
import utils.Constantes;

public abstract class Proyectil implements Colisionable {

    protected float x, y;
    protected Rectangle hitbox;
    protected Sprite sprite;
    protected Texture textura;

    protected GestorColisiones gestorColisiones;
    protected Personaje ejecutor;

    protected boolean activo = true;
    protected int danio;
    protected Vector2 posAnterior = new Vector2();
    protected Vector2 velocidadVector = new Vector2();

    protected float tiempoTranscurrido = 0f;

    protected boolean impacto = false;

    public Proyectil(float x, float y, float angulo, float velocidad, int danio,
                     GestorColisiones gestorColisiones, Personaje ejecutor, Texture textura) {
        this.x = x;
        this.y = y;
        this.danio = danio;
        this.gestorColisiones = gestorColisiones;
        this.ejecutor = ejecutor;

        this.sprite = new Sprite(textura);
        this.sprite.setPosition(x, y);
        this.hitbox = new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());

        this.velocidadVector.x = (float) Math.cos(angulo) * velocidad *
            (ejecutor != null ? ejecutor.getDireccionMultiplicador() : 1);
        this.velocidadVector.y = (float) Math.sin(angulo) * velocidad;

    }

    public void mover(float delta, GestorFisica gestorFisica) {
        if (!activo) return;

        tiempoTranscurrido += delta;
        posAnterior.set(x, y);

        if (tiempoTranscurrido > Constantes.TIEMPO_VIDA_MAX) {
            desactivar();
            return;
        }

        aplicarFisica(delta);

        Personaje ignorar = (ejecutor != null && tiempoTranscurrido < Constantes.TIEMPO_GRACIA) ? ejecutor : null;

        Vector2 nuevaPos = gestorFisica.moverProyectilConRaycast(this, delta, ignorar);

        x = nuevaPos.x;
        y = nuevaPos.y;
        updateHitbox();
    }


    public void setPosicion(float x, float y) {
        this.x = x;
        this.y = y;
        if (hitbox != null) hitbox.setPosition(x, y);
        if (sprite != null) sprite.setPosition(x, y);
    }

    public void aplicarFisica(float delta) {
        velocidadVector.y += Constantes.GRAVEDAD * delta;
    }

    protected Vector2 centroHitbox() {
        return new Vector2(x + hitbox.getWidth() / 2f, y + hitbox.getHeight() / 2f);
    }

    public void desactivar() {
        activo = false;
        gestorColisiones.removerObjeto(this);
    }

    public abstract void impactar(float centroX, float centroY);
    protected void updateHitbox() { if (hitbox != null) hitbox.setPosition(x, y); }
    public float getX() { return x; }
    public float getY() { return y; }
    public Vector2 getVelocidadVector() { return velocidadVector; }
    public Personaje getEjecutor() { return ejecutor; }
    public float getTiempoTranscurrido() { return tiempoTranscurrido; }
    public boolean getActivo() { return activo; }
    @Override public Rectangle getHitbox() { return hitbox; }
    @Override public Rectangle getHitboxPosicion(float x, float y) { return new Rectangle(x, y, hitbox.getWidth(), hitbox.getHeight()); }

    public void setImpacto(boolean valor) { this.impacto = valor; }
    public boolean getImpacto() { return this.impacto; }

    @Override public void setX(float x) { }

    @Override public void setY(float y) { }

    public void render(SpriteBatch batch) {
        if (sprite != null && activo) {
            sprite.setPosition(x, y);
            sprite.draw(batch);
        }
    }

    public void dispose() {}
}
