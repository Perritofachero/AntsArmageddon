package entidades.personajes;

import Fisicas.Camara;
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
    protected FisicaPersonaje fisicas;
    private int movimientoSeleccionado = 0;

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
        this.fisicas = new FisicaPersonaje(this, gestorColisiones);

        inicializarMovimientos();
    }

    public void mover(float deltaX, float deltaY, float deltaTiempo) {
        fisicas.moverHorizontal(deltaX, deltaTiempo);
        fisicas.moverVertical(deltaY, deltaTiempo);
    }

    public void saltar() { fisicas.saltar(Constantes.SALTO); }

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

        fisicas.actualizar(delta);
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

    public void recibirDanio(int danio, float fuerzaX, float fuerzaY) {
        vida -= danio;
        if (vida <= 0) {
            vida = 0;
            desactivar();
        } else {
            fisicas.aplicarKnockback(fuerzaX, fuerzaY);
        }
    }

    @Override public void desactivar() { activo = false; }

    public void setMovimientoSeleccionado(int indice) {
        if (indice >= 0 && indice < movimientos.size()) movimientoSeleccionado = indice;
    }

    public void apuntar(int direccion) {
        mirilla.mostrarMirilla();
        mirilla.cambiarAngulo(direccion);
    }

    public void aumentarVida(int vidaRecogida) { this.vida += vidaRecogida; }

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
    public float getVelocidadX() { return this.velocidadX; }
    public void setDireccion(boolean direccion) { this.direccion = direccion; }
    public FisicaPersonaje getFisicas() { return fisicas;}

    public Movimiento getMovimientoSeleccionado() {
        if (movimientoSeleccionado < 0 || movimientoSeleccionado >= movimientos.size()) return null;
        return movimientos.get(movimientoSeleccionado);
    }

    protected abstract void inicializarMovimientos();

    @Override public void dispose() {}

    public void renderHitbox(ShapeRenderer shapeRenderer, Camara camara) {
        if (!activo) return;
        shapeRenderer.setProjectionMatrix(camara.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        shapeRenderer.end();
    }
}
