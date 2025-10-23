package entidades.personajes;

import Fisicas.Camara;
import Gameplay.Gestores.GestorProyectiles;
import Gameplay.Movimientos.Movimiento;
import Gameplay.Movimientos.MovimientoRango;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import entidades.Entidad;
import entidades.personajes.AtributosPersonaje.BarraCarga;
import entidades.personajes.AtributosPersonaje.FisicaPersonaje;
import entidades.personajes.AtributosPersonaje.Mirilla;
import utils.Constantes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Personaje extends Entidad {

    public enum Estado {
        IDLE, WALK, JUMP, HIT, MUERTE;
    }

    private int idJugador;
    private Color colorJugador;

    protected Map<Estado, Animation<TextureRegion>> animaciones = new HashMap<>();
    protected Animation<TextureRegion> animActual;
    protected float stateTime = 0f;
    protected Estado estadoActual = Estado.IDLE;

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

    private float lastX = 0f;

    public Personaje(Texture textura, GestorColisiones gestorColisiones, GestorProyectiles gestorProyectiles,
                     float x, float y, int vida, float velocidadX, int idJugador) {

        super(x, y, textura, gestorColisiones);

        this.gestorProyectiles = gestorProyectiles;
        this.textura = textura;
        this.sprite = new Sprite(textura);
        this.sprite.setPosition(x, y);

        this.lastX = x;

        float margenX = 2f;
        float margenY = 3f;
        float nuevaAncho = sprite.getWidth() - 4 * margenX;
        float nuevaAlto = sprite.getHeight() - 4 * margenY;
        this.hitbox.set(x + margenX, y + margenY, nuevaAncho, nuevaAlto);

        this.vida = vida;
        this.activo = true;
        this.velocidadX = velocidadX;
        this.barraCarga = new BarraCarga();

        this.mirilla = new Mirilla(this);
        this.movimientos = new ArrayList<>();
        this.fisicas = new FisicaPersonaje(this, gestorColisiones);

        this.direccion = MathUtils.randomBoolean();

        if (!direccion && !sprite.isFlipX()) sprite.flip(true, false);
        else if (direccion && sprite.isFlipX()) sprite.flip(true, false);

        this.idJugador = idJugador;
        this.colorJugador = (idJugador == 0) ? Color.BLUE : Color.RED;

        inicializarMovimientos();
        inicializarAnimaciones();

        this.stateTime = MathUtils.random(0f, animActual.getAnimationDuration());
    }

    protected abstract void inicializarAnimaciones();

    @Override
    public void actualizar(float delta) {
        if (!activo) return;

        stateTime += delta;
        fisicas.actualizar(delta);
        mirilla.update(delta);
        mirilla.actualizarPosicion();

        if (estadoActual == Estado.MUERTE) {
            if (animActual.isAnimationFinished(stateTime)) desactivar();
            return;
        }

        if (estadoActual == Estado.HIT && fisicas.estaEnKnockback()) {
            return;
        }

        float dx = Math.abs(getX() - lastX);
        boolean caminando = dx > 0.1f && getSobreAlgo();

        if (vida <= 0) {
            cambiarEstado(Estado.MUERTE);
        }
        else if (!getSobreAlgo()) {
            cambiarEstado(Estado.JUMP);
        }
        else if (caminando) {
            cambiarEstado(Estado.WALK);
        }
        else {
            cambiarEstado(Estado.IDLE);
        }

        if (!enTurno || estadoActual == Estado.MUERTE || estadoActual == Estado.HIT) {
            ocultarMirilla();
        }
        else if (estaDisparando) {
            mostrarMirilla();
        }
        else if (!caminando && getSobreAlgo()) {
            mostrarMirilla();
        } else {
            ocultarMirilla();
        }

        lastX = getX();
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!activo) return;

        TextureRegion frame = animActual.getKeyFrame(stateTime, true);

        if (direccion && frame.isFlipX()) frame.flip(true, false);
        else if (!direccion && !frame.isFlipX()) frame.flip(true, false);

        batch.draw(frame, sprite.getX(), sprite.getY());

        if (enTurno) mirilla.render(batch);
    }

    protected void cambiarEstado(Estado nuevoEstado) {
        if (estadoActual != nuevoEstado) {
            estadoActual = nuevoEstado;
            animActual = animaciones.get(estadoActual);
            stateTime = 0f;
        }
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

    public void usarMovimiento() {

        if (!getSobreAlgo() || !activo) return;

        Movimiento movimiento = getMovimientoSeleccionado();
        if (movimiento == null) return;

        if (movimiento instanceof MovimientoRango movimientoRango) {

            if (estaDisparando) {
                float potencia = barraCarga.getCargaNormalizada();
                if (potencia > 0f) movimientoRango.ejecutar(this, potencia);

                barraCarga.reset();
                estaDisparando = false;
                terminarTurno();
                return;
            }

            barraCarga.start();
            estaDisparando = true;
            return;
        }

        movimiento.ejecutar(this);
        terminarTurno();
    }

    public void actualizarDisparo(float delta) {
        if (estaDisparando) barraCarga.update(delta);
    }

    public void recibirDanio(int danio, float fuerzaX, float fuerzaY) {
        vida -= danio;

        if (vida <= 0) {
            vida = 0;
            cambiarEstado(Estado.MUERTE);
            terminarTurno();
            return;
        }

        fisicas.aplicarKnockback(fuerzaX, fuerzaY);
        cambiarEstado(Estado.HIT);
    }

    public void aumentarVida(int vidaRecogida) { this.vida += vidaRecogida; }

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

    public void setEnTurno(boolean enTurno) {
        this.enTurno = enTurno;
        if (!enTurno) ocultarMirilla();
    }

    public boolean isEnTurno() { return enTurno; }
    public void terminarTurno() {
        turnoTerminado = true;
        ocultarMirilla();
    }
    public boolean isTurnoTerminado() { return turnoTerminado; }
    public void reiniciarTurno() { turnoTerminado = false; }

    public void mostrarMirilla() { mirilla.mostrarMirilla(); }
    public void ocultarMirilla() { mirilla.ocultarMirilla(); }
    public BarraCarga getBarraCarga() { return barraCarga; }
    public int getDireccionMultiplicador() { return direccion ? -1 : 1; }
    public int getVida() { return vida; }
    public boolean getDireccion() { return direccion; }
    public Mirilla getMirilla() { return mirilla; }
    public List<Movimiento> getMovimientos() { return movimientos; }
    public float getVelocidadX() { return this.velocidadX; }
    public void setDireccion(boolean direccion) { this.direccion = direccion; }
    public FisicaPersonaje getFisicas() { return fisicas;}
    public boolean isDisparando() { return estaDisparando; }
    public void setDisparando(boolean disparando) { this.estaDisparando = disparando; }
    public int getIdJugador() { return this.idJugador; }
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
