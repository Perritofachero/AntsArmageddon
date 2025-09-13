package entidades;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import Gameplay.Gestores.GestorColisiones;

public abstract class Proyectil extends Entidad {

    protected float velocidad;
    protected float angulo;
    protected boolean activo;
    protected int danio;
    protected float peso;//despues lo usaremos
    protected GestorColisiones gestor;
    protected Personaje ejecutor;
    protected float tiempoGracia = 2f;
    protected float tiempoTranscurrido = 0f;

    public Proyectil(float x, float y, float angulo, float velocidad, int danio, GestorColisiones gestor, Personaje ejecutor) {
        super(x, y);
        this.velocidad = velocidad;
        this.angulo = angulo;
        this.danio = danio;
        this.activo = true;
        this.gestor = gestor;
        this.ejecutor = ejecutor;
    }

    public abstract void mover(float delta);

    public abstract void render(SpriteBatch batch);

    public boolean isActivo() {
        return activo;
    }

    public int getDanio() {
        return danio;
    }

    public void desactivar() {
        activo = false;
        gestor.removerObjeto(this);
    }
}

