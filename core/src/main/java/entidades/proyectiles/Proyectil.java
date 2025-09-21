package entidades.proyectiles;

import Fisicas.Colisionable;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.math.Vector2;
import entidades.Entidad;
import entidades.personajes.Personaje;

public abstract class Proyectil extends Entidad {

    protected float velocidad;
    protected float angulo;
    protected boolean activo;
    protected int danio;
    protected GestorColisiones gestorColisiones;
    protected Personaje ejecutor;
    protected float tiempoGracia = 2f;
    protected float tiempoTranscurrido = 0f;
    protected Vector2 posAnterior = new Vector2();
    //protected float peso; despues lo usaremos

    public Proyectil(float x, float y, float angulo, float velocidad, int danio, GestorColisiones gestorColisiones, Personaje ejecutor) {
        super(x, y, gestorColisiones);
        this.velocidad = velocidad;
        this.angulo = angulo;
        this.danio = danio;
        this.activo = true;
        this.gestorColisiones = gestorColisiones;
        this.ejecutor = ejecutor;
    }

    public Vector2 debugInicio = new Vector2();
    public Vector2 debugFin = new Vector2();

    public void mover(float delta) {
        tiempoTranscurrido += delta;
        posAnterior.set(x, y);
        Vector2 movimiento = calcularMovimiento(delta);

        Personaje ignorar = null;
        if (ejecutor != null && tiempoTranscurrido < tiempoGracia) {
            ignorar = ejecutor;
        }

        Colisionable impactado = gestorColisiones.verificarTrayectoria(
            new Vector2(x + hitbox.width / 2f, y + hitbox.height / 2f),
            new Vector2(x + hitbox.width / 2f + movimiento.x, y + hitbox.height / 2f + movimiento.y),
            ignorar, this );

        if (impactado != null) {
            x = posAnterior.x;
            y = posAnterior.y;

            impactoProyectil(impactado);
            desactivar();

        } else {
            x += movimiento.x;
            y += movimiento.y;
            updateHitbox();
        }

        System.out.println("x:" + this.x + " y:" + this.y);
    }

    public void desactivar() {
        activo = false;
        gestorColisiones.removerObjeto(this);
    }

    protected abstract Vector2 calcularMovimiento(float delta);
    protected abstract void impactoProyectil(Colisionable impactado);

    public abstract void render(SpriteBatch batch);
    public boolean isActivo() {
        return activo;
    }
    public int getDanio() {
        return danio;
    }
    public Vector2 getPosAnterior() { return posAnterior; }
    public Vector2 getPosActual() { return new Vector2(x, y); }

}

