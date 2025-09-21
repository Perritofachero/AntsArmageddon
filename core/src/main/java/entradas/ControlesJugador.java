package entradas;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import java.util.HashSet;
import java.util.Set;

public class ControlesJugador implements InputProcessor {

    private final Set<Integer> keysPresionadas = new HashSet<>();
    private int movimientoSeleccionado = -1;

    private float x, y;
    private boolean saltar;
    private int apuntarDir;

    public void procesarEntrada() {
        this.x = 0;
        this.y = 0;
        this.saltar = false;
        this.apuntarDir = 0;

        if (keysPresionadas.contains(Input.Keys.LEFT))  { x -= 1; }
        if (keysPresionadas.contains(Input.Keys.RIGHT)) { x += 1; }
        if (keysPresionadas.contains(Input.Keys.UP))    { y += 4; }
        if (keysPresionadas.contains(Input.Keys.DOWN))  { y -= 1; }
        if (keysPresionadas.contains(Input.Keys.SPACE)) { saltar = true; }

        if (keysPresionadas.contains(Input.Keys.W)) apuntarDir = -1;
        if (keysPresionadas.contains(Input.Keys.S)) apuntarDir = 1;
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public boolean getSaltar() { return saltar; }
    public int getApuntarDir() { return apuntarDir; }

    public int getMovimientoSeleccionado() { return movimientoSeleccionado; }
    public void resetMovimientoSeleccionado() { movimientoSeleccionado = -1; }

    @Override
    public boolean keyDown(int keycode) {
        keysPresionadas.add(keycode);

        if (keycode == Input.Keys.Z) movimientoSeleccionado = 0;
        if (keycode == Input.Keys.X) movimientoSeleccionado = 1;

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
}
