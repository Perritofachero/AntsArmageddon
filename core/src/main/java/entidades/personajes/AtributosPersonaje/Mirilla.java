package entidades.personajes.AtributosPersonaje;

import Gameplay.Gestores.Visuales.GestorAnimaciones;
import Gameplay.Gestores.GestorRutas;
import com.badlogic.gdx.graphics.g2d.*;
import entidades.personajes.Personaje;
import Gameplay.Gestores.Visuales.GestorAssets;

public class Mirilla {

    private float x, y;
    private float angulo;
    private float anguloRad;
    private float radio = 50f;

    private final Personaje personaje;

    private final Animation<TextureRegion> animMirilla;
    private boolean animTerminada = false;
    private float stateTime = 0f;

    private boolean visible = false;
    private final float velocidadMirilla = 2f;

    public Mirilla(Personaje personaje) {
        this.personaje = personaje;

        TextureAtlas atlas = GestorAssets.get(GestorRutas.ATLAS_MIRA, TextureAtlas.class);
        animMirilla = GestorAnimaciones.obtener(atlas, "mira", 0.1f, false);

        this.angulo = personaje.getDireccion() ? 0 : 180;
        actualizarPosicion();
    }

    public void actualizarPosicion() {
        float poscX = personaje.getX() + personaje.getSprite().getWidth() / 2f;
        float poscY = personaje.getY() + personaje.getSprite().getHeight() / 2f;

        anguloRad = (float) Math.toRadians(angulo);

        float distanciaX = (float) Math.cos(anguloRad) * radio * personaje.getDireccionMultiplicador();
        float distanciaY = (float) Math.sin(anguloRad) * radio;

        this.x = poscX + distanciaX;
        this.y = poscY + distanciaY;
    }

    public void cambiarAngulo(int direccion) {
        angulo += direccion * velocidadMirilla;
        if (angulo > 270) angulo = 270;
        else if (angulo < 90) angulo = 90;
        actualizarPosicion();
    }

    public void update(float delta) {
        if (!visible) return;

        if (!animTerminada) {
            stateTime += delta;
            if (animMirilla.isAnimationFinished(stateTime)) {
                animTerminada = true;
            }
        }
    }

    public void render(SpriteBatch batch) {
        if (!visible) return;

        TextureRegion frame = animMirilla.getKeyFrame(stateTime, false);
        float ancho = frame.getRegionWidth();
        float alto = frame.getRegionHeight();

        batch.draw(frame, x - ancho / 2f, y - alto / 2f);
    }

    public void mostrarMirilla() {
        if (!visible) {
            visible = true;
            animTerminada = false;
            stateTime = 0f;
        }
    }

    public void ocultarMirilla() {
        visible = false;
        stateTime = 0f;
        animTerminada = false;
    }

    public float getAnguloRad() { return this.anguloRad; }
}
