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
import entidades.personajes.AtributosPersonaje.BarraCarga;
import entidades.personajes.AtributosPersonaje.FisicaPersonaje;
import entidades.personajes.AtributosPersonaje.Mirilla;
import utils.Constantes;
import utils.RecursosGlobales;
import java.util.ArrayList;
import java.util.List;

public abstract class Personaje extends Entidad {

    protected GestorProyectiles gestorProyectiles;
    protected Mirilla mirilla;
    protected List<Movimiento> movimientos;
    protected boolean direccion;
    protected int vida;
    protected float velocidadX;
    protected BarraCarga barraCarga;
    protected FisicaPersonaje fisicas;
    private int movimientoSeleccionado = 0;
    private boolean estaDisparando = false;

    private boolean enTurno = false;
    private boolean turnoTerminado = false;

    public Personaje(Texture textura, GestorColisiones gestorColisiones, GestorProyectiles gestorProyectiles,
                     float x, float y, int vida, float velocidadX) {
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
        if (!puedeActuar()) return;
        fisicas.moverHorizontal(deltaX, deltaTiempo);
        fisicas.moverVertical(deltaY, deltaTiempo);
    }

    public void saltar() {
        if (!puedeActuar()) return;
        fisicas.saltar(Constantes.SALTO);
    }

    public void apuntar(int direccion) {
        if (!getSobreAlgo()) return;
        mirilla.mostrarMirilla();
        mirilla.cambiarAngulo(direccion);
    }

    public void iniciarDisparo() {
        if (!puedeDisparar()) return;

        Movimiento movimiento = getMovimientoSeleccionado();
        if (movimiento == null) return;

        if (!estaDisparando) {
            barraCarga.start();
            estaDisparando = true;
        }
    }

    public void liberarDisparo() {
        Movimiento movimiento = getMovimientoSeleccionado();
        if (movimiento == null) return;

        if (movimiento instanceof MovimientoRango movimientoRango && estaDisparando) {
            float potencia = barraCarga.getCargaNormalizada();
            if (potencia > 0f) movimientoRango.ejecutar(this, potencia);
            barraCarga.reset();
            estaDisparando = false;
        }
        else if (movimiento instanceof MovimientoMelee movimientoMelee && puedeAtacarMelee()) {
            movimientoMelee.ejecutar(this);
            estaDisparando = false;
        }
    }

    public void actualizarDisparo(float delta) {
        if (estaDisparando) barraCarga.update(delta);
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

    public void aumentarVida(int vidaRecogida) { this.vida += vidaRecogida; }

    @Override
    public void actualizar(float delta) {
        if (!activo) return;

        fisicas.actualizar(delta);
        mirilla.actualizarPosicion();
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!activo) return;

        sprite.draw(batch);
        if (enTurno) {
            mirilla.render(batch);
        }
    }


    public float distanciaAlCentro(float x, float y) {
        float centroX = this.getX() + this.getSprite().getWidth() / 2f;
        float centroY = this.getY() + this.getSprite().getHeight() / 2f;
        float dx = centroX - x;
        float dy = centroY - y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public void setMovimientoSeleccionado(int indice) {
        if (indice >= 0 && indice < movimientos.size()) movimientoSeleccionado = indice;
    }

    public boolean puedeActuar() {
        return activo && !estaDisparando;
    }

    public boolean puedeDisparar() {
        Movimiento m = getMovimientoSeleccionado();
        return activo && m instanceof MovimientoRango && getSobreAlgo();
    }

    public boolean puedeAtacarMelee() {
        Movimiento m = getMovimientoSeleccionado();
        return activo && m instanceof MovimientoMelee && getSobreAlgo();
    }

    public void setEnTurno(boolean enTurno) {
        this.enTurno = enTurno;
        if(!enTurno) ocultarMirilla();
    }

    //despues para terminar turnos despues de una accion o cuando se lastima el personaje
    public boolean isEnTurno() {
        return enTurno;
    }

    public void terminarTurno() {
        turnoTerminado = true;
        ocultarMirilla();
    }

    public boolean isTurnoTerminado() {
        return turnoTerminado;
    }

    public void reiniciarTurno() {
        turnoTerminado = false;
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
    public boolean isDisparando() { return estaDisparando; }
    public void setDisparando(boolean disparando) { this.estaDisparando = disparando; }
    public Movimiento getMovimientoSeleccionado() {
        if (movimientoSeleccionado < 0 || movimientoSeleccionado >= movimientos.size()) return null;
        return movimientos.get(movimientoSeleccionado);
    }

    protected abstract void inicializarMovimientos();
    @Override public void dispose() {}
    @Override public void desactivar() { activo = false; }

    public void renderHitbox(ShapeRenderer shapeRenderer, Camara camara) {
        if (!activo) return;
        shapeRenderer.setProjectionMatrix(camara.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        shapeRenderer.end();
    }
}
