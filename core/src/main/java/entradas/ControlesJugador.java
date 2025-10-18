package entradas;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import java.util.HashSet;
import java.util.Set;

public class ControlesJugador implements InputProcessor {

    private final Set<Integer> keysPresionadas = new HashSet<>();
    private int movimientoSeleccionado = 0;

    private float x, y;
    private boolean saltar;
    private int apuntarDir;

    private boolean disparoPresionado = false;
    private boolean disparoLiberado = false;

    public void procesarEntrada() {
        this.x = 0;
        this.y = 0;
        this.saltar = false;
        this.apuntarDir = 0;

        if (keysPresionadas.contains(Input.Keys.LEFT))  { x -= 1; }
        if (keysPresionadas.contains(Input.Keys.RIGHT)) { x += 1; }
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

    public boolean getDisparoPresionado() { return disparoPresionado; }
    public boolean getDisparoLiberado() { return disparoLiberado; }

    @Override
    public boolean keyDown(int keycode) {
        keysPresionadas.add(keycode);

        switch (keycode) {
            case Input.Keys.NUM_1: movimientoSeleccionado = 0; System.out.println("Seleccionado 1"); break;
            case Input.Keys.NUM_2: movimientoSeleccionado = 1; System.out.println("Seleccionado 2"); break;
            case Input.Keys.NUM_3: movimientoSeleccionado = 2; System.out.println("Seleccionado 3"); break;
            case Input.Keys.NUM_4: movimientoSeleccionado = 3; System.out.println("Seleccionado 4"); break;
            case Input.Keys.NUM_5: movimientoSeleccionado = 4; System.out.println("Seleccionado 5"); break;
            case Input.Keys.NUM_6: movimientoSeleccionado = 5; System.out.println("Seleccionado 6"); break;
            case Input.Keys.NUM_7: movimientoSeleccionado = 6; System.out.println("Seleccionado 7"); break;
        }

        if (keycode == Input.Keys.Z) {
            disparoPresionado = true;
            disparoLiberado = false;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        keysPresionadas.remove(keycode);

        if (keycode == Input.Keys.Z) {
            disparoPresionado = false;
            disparoLiberado = true;
        }

        return true;
    }

    public void reset() {
        keysPresionadas.clear();
        disparoPresionado = false;
        disparoLiberado = false;
        x = 0;
        y = 0;
        saltar = false;
        apuntarDir = 0;
    }


    public void resetDisparoLiberado() { disparoLiberado = false; }

    @Override public boolean keyTyped(char character) { return false; }
    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchCancelled(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
    @Override public boolean scrolled(float amountX, float amountY) { return false; }
}
