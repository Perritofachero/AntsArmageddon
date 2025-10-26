package entidades.proyectiles;

import Fisicas.Colisionable;
import Fisicas.Fisica;
import Gameplay.Gestores.Logicos.GestorFisica;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import Gameplay.Gestores.Logicos.GestorColisiones;
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

    protected boolean activo;
    protected int danio;
    protected Vector2 posAnterior = new Vector2();
    protected Vector2 velocidadVector = new Vector2();
    protected float fuerzaKnockback;

    protected float tiempoTranscurrido = 0f;
    protected boolean impacto = false;

    public Proyectil(float x, float y, float angulo, float velocidad, int danio, float fuerzaKnockback,
                     GestorColisiones gestorColisiones, Personaje ejecutor, Texture textura) {

        this.x = x;
        this.y = y;
        this.danio = danio;
        this.gestorColisiones = gestorColisiones;
        this.ejecutor = ejecutor;

        this.textura = textura;
        this.sprite = new Sprite(textura);
        this.sprite.setPosition(x, y);
        this.hitbox = new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
        this.activo = true;
        this.fuerzaKnockback = fuerzaKnockback;

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

        Personaje ignorar = (ejecutor != null && tiempoTranscurrido < Constantes.TIEMPO_GRACIA)
            ? ejecutor : null;

        Vector2 nuevaPos = gestorFisica.moverProyectilConRaycast(this, delta, ignorar);

        setPosicion(nuevaPos.x, nuevaPos.y);
    }

    public void setPosicion(float x, float y) {
        this.x = x;
        this.y = y;
        updateHitbox();
        if (sprite != null) sprite.setPosition(x, y);
    }

    public void aplicarFisica(float delta, Fisica fisica) {
        fisica.aplicarGravedad(velocidadVector, delta);
    }

    public void desactivar() { activo = false; }

    public void render(SpriteBatch batch) {
        if (sprite != null && activo) {
            sprite.draw(batch);
        }
    }


    @Override public void updateHitbox() {
        if (hitbox != null) hitbox.setPosition(x, y); }

    public float getX() { return x; }
    public float getY() { return y; }
    public void setX(float nuevaX) { this.x = nuevaX; }
    public void setY(float nuevaY) { this.y = nuevaY; }

    public boolean getActivo() { return activo; }
    public Vector2 getVelocidadVector() { return velocidadVector; }
    public Personaje getEjecutor() { return ejecutor; }
    public float getTiempoTranscurrido() { return tiempoTranscurrido; }
    public boolean getImpacto() { return impacto; }
    public void setImpacto(boolean valor) { this.impacto = valor; }
    public abstract void impactar(float centroX, float centroY);

    @Override public Rectangle getHitbox() { return hitbox; }

    @Override public Rectangle getHitboxPosicion(float x, float y) {
        return new Rectangle(x, y, hitbox.getWidth(), hitbox.getHeight());
    }

    protected Vector2 centroHitbox() {
        return new Vector2(
            x + hitbox.getWidth() / 2f,
            y + hitbox.getHeight() / 2f
        );
    }

    public float getFuerzaKnockback() { return fuerzaKnockback; }
    public void setFuerzaKnockback(float fuerzaKnockback) { this.fuerzaKnockback = fuerzaKnockback; }


    public void dispose() {}
}
