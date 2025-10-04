package entidades.personajes;

import Fisicas.Fisica;
import Fisicas.Mapa;
import Gameplay.Gestores.GestorProyectiles;
import Gameplay.Movimientos.Movimiento;
import Gameplay.Movimientos.MovimientoRango;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import entidades.Entidad;
import utils.Constantes;
import utils.RecursosGlobales;

import java.util.ArrayList;
import java.util.List;

public abstract class Personaje extends Entidad {

    protected GestorProyectiles gestorProyectiles;
    protected Mirilla mirilla;
    protected List<Movimiento> movimientos;
    protected boolean direccion;
    protected int vida, vidaMaxima;
    protected boolean activo;
    protected float velocidadX;
    protected Vector2 velocidadVector = new Vector2();
    protected BarraCarga barraCarga;

    public Personaje(Texture textura, GestorColisiones gestorColisiones, GestorProyectiles gestorProyectiles, float x, float y, int vida, int vidaMaxima, float velocidadX) {
        super(x, y, gestorColisiones);

        this.gestorProyectiles = gestorProyectiles;
        this.textura = textura;
        this.sprite = new Sprite(textura);
        this.sprite.setPosition(x, y);

        float margenX = 2f;
        float margenY = 3f;
        float nuevaAncho = sprite.getWidth() - 4 * margenX;
        float nuevaAlto = sprite.getHeight() - 4 * margenY;
        //Reduciomos la hitbox manualmente

        this.hitbox.set(x + margenX, y + margenY, nuevaAncho, nuevaAlto);

        this.vida = vida;
        this.vidaMaxima = vidaMaxima;
        this.direccion = false;
        this.activo = true;
        this.velocidadX = velocidadX;
        this.barraCarga = new BarraCarga();

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

    public void mover(float deltaX, float deltaTiempo) {
        if (deltaX == 0) return;

        if (deltaX < 0) {
            direccion = false;
            if (!sprite.isFlipX()) sprite.flip(true, false);
        } else {
            direccion = true;
            if (sprite.isFlipX()) sprite.flip(true, false);
        }

        float nuevaX = this.x + deltaX * velocidadX * deltaTiempo;

        boolean puedeMover = gestorColisiones.verificarMovimiento(this, nuevaX, this.y);

        if (puedeMover) {
            this.x = nuevaX;
            updateHitbox();

            //Ascenso y descenso para ajustar tolerancia de subida y bajada de pendietnes.

            int maxDescenso = 4;

            for (int x = 1; x <= maxDescenso; x++) {
                float nuevaY = this.y - x;
                if (!gestorColisiones.verificarMovimiento(this, this.x, nuevaY)) {
                    this.y = this.y - (x - 1);
                    updateHitbox();
                    break;
                }
            }
        } else {
            boolean pudoSubir = false;
            int maxAscenso = 4;

            for (int x = 1; x <= maxAscenso; x++) {
                float nuevaY = this.y + x;
                if (gestorColisiones.verificarMovimiento(this, nuevaX, nuevaY)) {
                    this.x = nuevaX;
                    this.y = nuevaY;
                    updateHitbox();
                    pudoSubir = true;
                    break;
                }
            }

            if (!pudoSubir) { /*Para poner algo cuando choque*/}
        }
    }

    public void saltar() {
        if (sobreAlgo) {
            velocidadVector.y = Constantes.SALTO;
            sobreAlgo = false;
        }
    }

    public void usarMovimiento(int indice) {
        if (indice < 0 || indice >= movimientos.size()) return;

        Movimiento movimiento = movimientos.get(indice);

        if (movimiento instanceof MovimientoRango rango) {
            float potencia = barraCarga.getCargaNormalizada();

            if (potencia > 0f) {
                rango.ejecutar(this, potencia);
                barraCarga.reset();
                System.out.println("Disparando movimiento de rango con potencia " + potencia);
            }
        } else {
            //ejecutar movimiento de otro tipo
        }
    }

    public void apuntar(int direccion) {
        mirilla.mostrarMirilla();
        mirilla.cambiarAngulo(direccion);
    }

    public void render(SpriteBatch batch) {
        if (!activo) return;

        float offsetVisual = -5f;
        sprite.setPosition(this.x, this.y + offsetVisual);
        sprite.draw(batch);

        mirilla.actualizarPosicion();
        mirilla.render(batch);

        if (barraCarga.getCargaActual() > 0f) {
            batch.end();

            float barraAncho = sprite.getWidth();
            float barraAlto = 5f;
            barraCarga.render(x, y - barraAlto - 2f, barraAncho, barraAlto);

            batch.begin();
        }
    }

    public float distanciaAlCentro(float x, float y) {
        float centroX = this.getX() + this.getSprite().getWidth() / 2f;
        float centroY = this.getY() + this.getSprite().getHeight() / 2f;

        float dx = centroX - x;
        float dy = centroY - y;
        return (float) Math.sqrt(dx * dx + dy * dy);
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

    public void renderHitbox() {
        if (!activo) return;

        ShapeRenderer sr = RecursosGlobales.shapeRenderer;

        sr.setProjectionMatrix(RecursosGlobales.camaraPersonaje.getCamera().combined);

        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.RED);
        sr.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        sr.end();
    }

    protected abstract void inicializarMovimientos();

    public BarraCarga getBarraCarga() { return this.barraCarga; }
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

