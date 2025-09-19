package entidades.personajes;

import Gameplay.Gestores.GestorProyectiles;
import Gameplay.Movimientos.Movimiento;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.graphics.Texture;
import entidades.Entidad;
import java.util.ArrayList;
import java.util.List;

public abstract class Personaje extends Entidad {

    protected GestorColisiones gestorColisiones;
    protected GestorProyectiles gestorProyectiles;
    protected Sprite sprite;
    protected Texture textura;
    protected Mirilla mirilla;
    protected boolean direccion;
    protected int vida;
    protected int vidaMaxima;
    protected int velocidad;
    protected List<Movimiento> movimientos;
    protected boolean activo;

    public Personaje(Texture textura, GestorColisiones gestorColisiones, GestorProyectiles gestorProyectiles, float x, float y, int vidaMaxima, int velocidad) {
        super(x, y);
        this.gestorColisiones = gestorColisiones;
        this.gestorProyectiles = gestorProyectiles;
        this.textura = textura;
        this.sprite = new Sprite(textura);
        this.sprite.setPosition(x, y);
        this.hitbox.setWidth(sprite.getWidth());
        this.hitbox.setHeight(sprite.getHeight());

        this.vidaMaxima = vidaMaxima;
        this.vida = vidaMaxima;
        this.velocidad = velocidad;
        this.direccion = false;
        this.activo = true;

        if (!direccion) sprite.flip(true, false);

        this.mirilla = new Mirilla(this);
        this.movimientos = new ArrayList<>();

        inicializarMovimientos();
    }

    public void mover(float deltaX, float deltaY, float deltaTiempo) {
        float nuevaX = this.x + deltaX * velocidad * deltaTiempo;
        float nuevaY = this.y + deltaY * velocidad * deltaTiempo;

        if (deltaX < 0) {
            direccion = false;
            if (!sprite.isFlipX()) sprite.flip(true, false);
        } else if (deltaX > 0) {
            direccion = true;
            if (sprite.isFlipX()) sprite.flip(true, false);
        }

        if (gestorColisiones.verificarHitbox(this, nuevaX, nuevaY, this)) {
            this.x = nuevaX;
            this.y = nuevaY;
            update();
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

