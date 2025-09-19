package entidades;

import Gameplay.Gestores.GestorProyectiles;
import Gameplay.Movimientos.LanzaRoca;
import Gameplay.Movimientos.Movimiento;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.graphics.Texture;
import managers.GestorAssets;

import java.util.ArrayList;
import java.util.List;

public class Personaje extends Entidad {

    private GestorColisiones gestorColisiones;
    private GestorProyectiles gestorProyectiles;
    private Sprite sprite;
    private Texture textura;
    private Mirilla mirilla;
    private boolean direccion;
    private int vida;
    private int vidaMaxima = 100;
    private int velocidad;
    private List<Movimiento> movimientos;

    public Personaje(Texture textura, GestorColisiones gestorColisiones, GestorProyectiles gestorProyectiles, float x, float y) {
        super(x, y);
        this.gestorColisiones = gestorColisiones;
        this.gestorProyectiles = gestorProyectiles;
        this.textura = textura;
        this.sprite = new Sprite(textura);
        this.sprite.setPosition(x, y);

        this.hitbox.setWidth(sprite.getWidth());
        this.hitbox.setHeight(sprite.getHeight());

        this.vida = vidaMaxima;
        this.velocidad = 200;
        this.direccion = false;

        if (!direccion) {
            sprite.flip(true, false);
        }

        this.mirilla = new Mirilla(this);

        movimientos = new ArrayList<>();
        Texture texturaMovimiento = GestorAssets.get("1.png", Texture.class);
        movimientos.add(new LanzaRoca("Lanzar Roca", texturaMovimiento, 1f, 300f, 25, gestorProyectiles));
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
            Movimiento movimiento = movimientos.get(indice);
            movimiento.ejecutar(this);
        }
    }

    public void render(SpriteBatch batch) {
        sprite.setPosition(this.x, this.y);
        sprite.draw(batch);

        mirilla.actualizarPosicion();
        mirilla.render(batch);
    }

    public void restarVida(int danio) {
        this.vida -= danio;
        if (this.vida < 0) this.vida = 0;
    }

    public void dispose() {
        for (Movimiento m : movimientos) {
            if (m.getSprite() != null) m.getSprite().getTexture().dispose();
        }
    }

    public int getDireccionMultiplicador() {
        if (direccion) {
            return -1;
        } else {
            return 1;
        }
    }
    public float getX() { return this.x; }
    public float getY() { return this.y; }
    public int getVida() { return this.vida; }
    public int getVidaMaxima() { return vidaMaxima; }
    public boolean getDireccion() { return direccion; }
    public Sprite getSprite() { return this.sprite; }
    public Mirilla getMirilla() { return this.mirilla; }
    public List<Movimiento> getMovimientos() { return movimientos; }
}

