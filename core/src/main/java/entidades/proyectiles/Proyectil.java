package entidades.proyectiles;

import Fisicas.Colisionable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import entidades.personajes.Personaje;
import utils.Constantes;
import utils.RecursosGlobales;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

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

    protected int radioDestruccion;
    protected int radioExpansion;

    protected float tiempoTranscurrido = 0f;
    protected final float TIEMPO_GRACIA = 0.3f;
    protected final float TIEMPO_VIDA_MAX = 7f;


    public Proyectil(float x, float y, float angulo, float velocidad, int danio, GestorColisiones gestorColisiones, Personaje ejecutor, int radioDestruccion, int radioExpansion) {
        this.x = x;
        this.y = y;
        this.danio = danio;
        this.gestorColisiones = gestorColisiones;
        this.ejecutor = ejecutor;
        this.hitbox = new Rectangle(x, y, 0, 0);
        this.velocidadVector.x = (float) Math.cos(angulo) * velocidad * (ejecutor != null ? ejecutor.getDireccionMultiplicador() : 1);
        this.velocidadVector.y = (float) Math.sin(angulo) * velocidad;

        this.radioDestruccion = radioDestruccion;
        this.radioExpansion = radioExpansion;

    }

    public void mover(float delta) {
        tiempoTranscurrido += delta;
        posAnterior.set(x, y);

        if(tiempoTranscurrido > TIEMPO_VIDA_MAX){
            desactivar();
            return;
        }

        velocidadVector.y += Constantes.GRAVEDAD * delta;

        x += velocidadVector.x * delta;
        y += velocidadVector.y * delta;
        updateHitbox();

        Personaje ignorar = (ejecutor != null && tiempoTranscurrido < TIEMPO_GRACIA) ? ejecutor : null;
        Colisionable impactado = gestorColisiones.verificarTrayectoria(
            new Vector2(posAnterior.x + hitbox.width/2f, posAnterior.y + hitbox.height/2f),
            new Vector2(x + hitbox.width/2f, y + hitbox.height/2f),
            ignorar, this
        );

        if (impactado != null) {
            x = posAnterior.x;
            y = posAnterior.y;
            float centroX = x + hitbox.getWidth() / 2f;
            float centroY = y + hitbox.getHeight() / 2f;
            detonar(centroX, centroY);
            desactivar();
        }
    }

    public void desactivar() {
        activo = false;
        gestorColisiones.removerObjeto(this);
    }

    public void render(SpriteBatch batch) {
        if (sprite != null) {
            sprite.setPosition(x, y);
            sprite.draw(batch);
        }
        ShapeRenderer sr = RecursosGlobales.shapeRenderer;

        sr.begin(ShapeRenderer.ShapeType.Line);

        sr.setColor(Color.RED);
        float centroX = x + hitbox.getWidth() / 2f;
        float centroY = y + hitbox.getHeight() / 2f;
        sr.circle(centroX, centroY, radioDestruccion);

        sr.setColor(Color.YELLOW);
        sr.circle(centroX, centroY, radioExpansion);

        sr.end();
    }

    protected void updateHitbox() { if (hitbox != null) hitbox.setPosition(x, y); }
    public abstract void detonar(float centroX, float centroY);

    public boolean isActivo() { return activo; }
    public Rectangle getHitbox() { return hitbox; }
    public Rectangle getHitboxPosicion(float x, float y) { return new Rectangle(x, y, hitbox.getWidth(), hitbox.getHeight()); }
    public int getRadioDestruccion() { return this.radioDestruccion; }

    public void dispose() { }

}
