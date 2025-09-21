package entidades.personajes;

import Fisicas.Fisica;
import Fisicas.Mapa;
import Gameplay.Gestores.GestorProyectiles;
import Gameplay.Movimientos.Movimiento;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import entidades.Entidad;
import utils.Constantes;
import java.util.ArrayList;
import java.util.List;

public abstract class Personaje extends Entidad {

    protected GestorColisiones gestorColisiones;
    protected GestorProyectiles gestorProyectiles;
    protected Sprite sprite;
    protected Texture textura;
    protected Mirilla mirilla;
    protected List<Movimiento> movimientos;
    protected Vector2 velocidadVector = new Vector2();
    protected boolean direccion;
    protected int vida, vidaMaxima;
    protected float velocidadX, velocidadY;
    protected boolean sobreSuelo, activo;

    public Personaje(Texture textura, GestorColisiones gestorColisiones, GestorProyectiles gestorProyectiles, float x, float y, int vida, int vidaMaxima, float velocidadX, float velocidadY) {
        super(x, y, gestorColisiones);
        this.gestorColisiones = gestorColisiones;
        this.gestorProyectiles = gestorProyectiles;
        this.textura = textura;
        this.sprite = new Sprite(textura);
        this.sprite.setPosition(x, y);
        this.hitbox.setWidth(sprite.getWidth());
        this.hitbox.setHeight(sprite.getHeight());

        this.vida = vida;
        this.vidaMaxima = vidaMaxima;
        this.velocidadX = velocidadX;
        this.velocidadY = velocidadY;
        this.direccion = false;
        this.activo = true;

        if (!direccion) sprite.flip(true, false);

        this.mirilla = new Mirilla(this);
        this.movimientos = new ArrayList<>();

        inicializarMovimientos();
    }

    public void aplicarFisica(Fisica fisica, Mapa mapa, float delta) {
        sobreAlgo = verificarSobreAlgo(gestorColisiones);
        velocidadVector = fisica.aplicarGravedad(velocidadVector, delta, sobreAlgo);

        Rectangle nuevaHitbox = fisica.moverEntidad(hitbox, velocidadVector, mapa, gestorColisiones, delta);
        this.x = nuevaHitbox.x;
        this.y = nuevaHitbox.y;
        updateHitbox();
    }

    public void mover(float deltaX, float deltaY, float deltaTiempo) {
        float nuevaX = this.x + deltaX * velocidadX * deltaTiempo;
        float nuevaY = this.y + deltaY * velocidadY * deltaTiempo;

        if (deltaX < 0) {
            direccion = false;
            if (!sprite.isFlipX()) sprite.flip(true, false);
        } else if (deltaX > 0) {
            direccion = true;
            if (sprite.isFlipX()) sprite.flip(true, false);
        }

        boolean puedeMover = gestorColisiones.verificarMovimiento(this, nuevaX, nuevaY);

        if (puedeMover) {
            this.x = nuevaX;
            this.y = nuevaY;
            updateHitbox();
        }
    }

    public void saltar() {
        if (sobreSuelo) {
            velocidadY = Constantes.SALTO;
            sobreSuelo = false;
        }
    }

    public void usarMovimiento(int indice) {
        if (indice >= 0 && indice < movimientos.size()) {
            movimientos.get(indice).ejecutar(this);
        }
        System.out.println("Utilizando movimiento");
    }

    public void apuntar(int direccion) {
        mirilla.mostrarMirilla();
        mirilla.cambiarAngulo(direccion);
    }

    public void render(SpriteBatch batch) {
        if (!activo) { return; }
        sprite.setPosition(this.x, this.y);
        sprite.draw(batch);
        mirilla.actualizarPosicion();
        mirilla.render(batch);
    }

    public void recibirDanio(int danio) {
        this.vida -= danio;
        if (this.vida <= 0) {
            this.vida = 0;
            morir();
        }
    }

    private void morir() {
        this.activo = false;
        if (gestorColisiones != null) {
            gestorColisiones.removerObjeto(this);
        }
        dispose();
    }

    public void mostrarMirilla() { mirilla.mostrarMirilla(); }
    public void ocultarMirilla() { mirilla.ocultarMirilla(); }

    public void dispose() {
        for (Movimiento m : movimientos) {
            if (m.getSprite() != null) m.getSprite().getTexture().dispose();
        }
    }

    protected abstract void inicializarMovimientos();

    public boolean getActivo() { return this.activo; }
    public int getDireccionMultiplicador() { return direccion ? -1 : 1; }
    public float getX() { return this.x; }
    public float getY() { return this.y; }
    public int getVida() { return this.vida; }
    public int getVidaMaxima() { return this.vidaMaxima; }
    public boolean getDireccion() { return this.direccion; }
    public Sprite getSprite() { return this.sprite; }
    public Mirilla getMirilla() { return this.mirilla; }
    public List<Movimiento> getMovimientos() { return this.movimientos; }
}

