package entidades.personajes;

import Gameplay.Gestores.GestorProyectiles;
import Gameplay.Movimientos.Melee.Aranazo;
import Gameplay.Movimientos.Movimiento;
import Gameplay.Movimientos.MovimientoMelee;
import Gameplay.Movimientos.MovimientoRango;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    protected float velocidadX;
    protected BarraCarga barraCarga;
    private int movimientoSeleccionado = 0;

    //Agregar fisicas de knockback

    public Personaje(Texture textura, GestorColisiones gestorColisiones, GestorProyectiles gestorProyectiles,
                     float x, float y, int vida, int vidaMaxima, float velocidadX) {
        super(x, y, textura, gestorColisiones);

        this.gestorProyectiles = gestorProyectiles;
        this.textura = textura;
        this.sprite = new Sprite(textura);
        this.sprite.setPosition(x, y);

        float margenX = 2f;
        float margenY = 3f;
        float nuevaAncho = sprite.getWidth() - 4 * margenX;
        float nuevaAlto = sprite.getHeight() - 4 * margenY;
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

    public void mover(float deltaX, float deltaY, float deltaTiempo) {
        moverX(deltaX, deltaTiempo);
        moverY(deltaY, deltaTiempo);
        updateHitbox();
    }

    private void moverX(float deltaX, float deltaTiempo) {
        if (deltaX == 0) return;

        if (deltaX < 0) {
            direccion = false;
            if (!sprite.isFlipX()) sprite.flip(true, false);
        } else {
            direccion = true;
            if (sprite.isFlipX()) sprite.flip(true, false);
        }

        float nuevaX = x + deltaX * velocidadX * deltaTiempo;
        boolean puedeMoverX = gestorColisiones.verificarMovimiento(this, nuevaX, y);

        if (puedeMoverX) {
            x = nuevaX;
        } else {
            int maxAscenso = 4;
            for (int i = 1; i <= maxAscenso; i++) {
                if (gestorColisiones.verificarMovimiento(this, nuevaX, y + i)) {
                    x = nuevaX;
                    y += i;
                    break;
                }
            }
        }
    }

    private void moverY(float deltaY, float deltaTiempo) {
        if (deltaY == 0) return;

        float destinoY = y + deltaY * velocidad.y * deltaTiempo;
        float pasoY = 1f;

        if (deltaY > 0) {
            while (y < destinoY) {
                if (gestorColisiones.verificarMovimiento(this, x, y + pasoY)) {
                    y += pasoY;
                } else {
                    y = (float)Math.floor(y);
                    velocidad.y = 0;
                    break;
                }
            }
            sobreAlgo = false;
        } else {
            while (y > destinoY) {
                if (gestorColisiones.verificarMovimiento(this, x, y - pasoY)) {
                    y -= pasoY;
                } else {
                    y = (float)Math.ceil(y);
                    velocidad.y = 0;
                    sobreAlgo = true;
                    break;
                }
            }
            if (velocidad.y != 0) sobreAlgo = false;
        }
    }

    public void saltar() {
        if (sobreAlgo) {
            velocidad.y = Constantes.SALTO;
            sobreAlgo = false;
        }
    }

    public void usarMovimiento() {
        if (movimientoSeleccionado < 0 || movimientoSeleccionado >= movimientos.size()) return;

        Movimiento movimientoUsado = movimientos.get(movimientoSeleccionado);

        if (movimientoUsado instanceof MovimientoRango movimientoRango) {
            float potencia = barraCarga.getCargaNormalizada();
            if (potencia <= 0f) return;
            movimientoRango.ejecutar(this, potencia);
            barraCarga.reset();
        } else if (movimientoUsado instanceof MovimientoMelee movimientoMelee) {
            movimientoMelee.ejecutar(this);
        }
    }

    @Override
    public void actualizar(float delta) {
        if (!activo) return;

        barraCarga.update(delta);
        mirilla.actualizarPosicion();
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!activo) return;

        float offsetVisual = -5f;
        sprite.setPosition(x, y + offsetVisual);
        sprite.draw(batch);

        mirilla.render(batch);

        if (barraCarga.getCargaActual() > 0f) {
            batch.end();
            barraCarga.render(x, y - 7f, sprite.getWidth(), 5f);
            batch.begin();
        }

        Movimiento movimientoActual = getMovimientoSeleccionado();
        if (movimientoActual instanceof Aranazo ara) {
            batch.end();
            ara.renderDebug(RecursosGlobales.shapeRenderer, Gdx.graphics.getDeltaTime());
            batch.begin();
        }
    }

    public void recibirDanio(int danio) {
        vida -= danio;
        if (vida <= 0) {
            vida = 0;
            desactivar();
        }
    }

    @Override
    public void desactivar() {
        activo = false;
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

    public void setMovimientoSeleccionado(int indice) {
        if (indice >= 0 && indice < movimientos.size()) movimientoSeleccionado = indice;
    }

    public void apuntar(int direccion) {
        mirilla.mostrarMirilla();
        mirilla.cambiarAngulo(direccion);
    }

    public float distanciaAlCentro(float x, float y) {
        float centroX = this.getX() + this.getSprite().getWidth() / 2f;
        float centroY = this.getY() + this.getSprite().getHeight() / 2f;
        float dx = centroX - x;
        float dy = centroY - y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public void mostrarMirilla() { mirilla.mostrarMirilla(); }
    public void ocultarMirilla() { mirilla.ocultarMirilla(); }

    public BarraCarga getBarraCarga() { return barraCarga; }
    public int getDireccionMultiplicador() { return direccion ? -1 : 1; }
    public int getVida() { return vida; }
    public boolean getDireccion() { return direccion; }
    public Sprite getSprite() { return sprite; }
    public Mirilla getMirilla() { return mirilla; }
    public List<Movimiento> getMovimientos() { return movimientos; }
    public Movimiento getMovimientoSeleccionado() {
        if (movimientoSeleccionado < 0 || movimientoSeleccionado >= movimientos.size()) return null;
        return movimientos.get(movimientoSeleccionado);
    }

    protected abstract void inicializarMovimientos();

    @Override public void dispose() {}
}
