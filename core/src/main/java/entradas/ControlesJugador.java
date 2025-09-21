package entradas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import entidades.personajes.Personaje;
import java.util.HashSet;
import java.util.Set;

public class ControlesJugador implements InputProcessor {

    private Personaje personaje;
    private final Set<Integer> keysPresionadas = new HashSet<>();
    private int movimientoSeleccionado = -1;
    private float x, y;

    public void procesarEntrada() {
        if (personaje == null) return;

        this.x = 0;
        this.y = 0;

        if (keysPresionadas.contains(Input.Keys.LEFT))  { x -= 1; personaje.ocultarMirilla(); }
        if (keysPresionadas.contains(Input.Keys.RIGHT)) { x += 1; personaje.ocultarMirilla(); }
        if (keysPresionadas.contains(Input.Keys.UP))    { y += 4; personaje.ocultarMirilla(); }
        if (keysPresionadas.contains(Input.Keys.DOWN))  { y -= 1; personaje.ocultarMirilla(); }
        if (keysPresionadas.contains(Input.Keys.SPACE))  { personaje.saltar(); }

        personaje.mover(x, y, Gdx.graphics.getDeltaTime());

        if (keysPresionadas.contains(Input.Keys.W)) personaje.apuntar(-1);
        if (keysPresionadas.contains(Input.Keys.S)) personaje.apuntar(1);
    }

    public int getMovimientoSeleccionado() { return movimientoSeleccionado; }
    public void resetMovimientoSeleccionado() { movimientoSeleccionado = -1; }

    public void setPersonaje(Personaje personaje) {
        this.personaje = personaje;
        clearKeys();
        resetMovimientoSeleccionado();
    }

    private void clearKeys() {
        keysPresionadas.clear();
    }
    @Override
    public boolean keyDown(int keycode) {
        keysPresionadas.add(keycode);

        if (personaje != null) {
            if (keycode == Input.Keys.Z) movimientoSeleccionado = 0;
            if (keycode == Input.Keys.X) movimientoSeleccionado = 1;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        keysPresionadas.remove(keycode);
        return true;
    }

    @Override public boolean keyTyped(char character) { return false; }
    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchCancelled(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
    @Override public boolean scrolled(float amountX, float amountY) { return false; }

    public float getX() { return this.x; }
    public float getY() { return this.y; }
}
