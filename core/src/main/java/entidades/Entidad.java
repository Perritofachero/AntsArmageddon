package entidades;

import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import Fisicas.Colisionable;
import com.badlogic.gdx.math.Vector2;

public abstract class Entidad implements Colisionable {

    protected float x, y;
    protected Rectangle hitbox;
    protected Sprite sprite;
    protected Texture textura;
    protected boolean sobreAlgo;
    protected boolean activo;
    protected Vector2 velocidad;
    protected GestorColisiones gestorColisiones;

    public Entidad(float x, float y, Texture textura, GestorColisiones gestorColisiones) {
        this.x = x;
        this.y = y;
        this.textura = textura;
        this.gestorColisiones = gestorColisiones;

        if (textura != null) {
            this.sprite = new Sprite(textura);
            this.sprite.setPosition(x, y);
        }

        float ancho = textura != null ? textura.getWidth() : 0;
        float alto = textura != null ? textura.getHeight() : 0;
        this.hitbox = new Rectangle(x, y, ancho, alto);

        this.activo = true;
        this.sobreAlgo = false;
        this.velocidad = new Vector2(0, 0);
    }

    public void updateHitbox() { hitbox.setPosition(x, y); }

    public void setPosicion(float x, float y) {
        this.x = x;
        this.y = y;
        updateHitbox();
        if (sprite != null) sprite.setPosition(x, y);
    }

    public abstract void actualizar(float delta);
    public abstract void render(SpriteBatch batch);

    public void dispose() {}

    @Override public void desactivar() { activo = false; }
    @Override public boolean getActivo() { return activo; }
    @Override public Rectangle getHitbox() { return hitbox; }

    @Override public Rectangle getHitboxPosicion(float x, float y) {
        return new Rectangle(x, y, hitbox.getWidth(), hitbox.getHeight());
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public Vector2 getVelocidad() { return velocidad; }
    public void setVelocidad(Vector2 velocidad) { this.velocidad.set(velocidad); }
    public float getWidth() { return hitbox.getWidth(); }
    public float getHeight() { return hitbox.getHeight(); }
    public boolean getSobreAlgo() { return sobreAlgo; }
    public void setSobreAlgo(boolean sobreAlgo) { this.sobreAlgo = sobreAlgo; }
}
