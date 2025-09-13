package entradas;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import entidades.Personaje;
import java.util.HashSet;
import java.util.Set;

public class ControlesJugador implements InputProcessor {

    private Personaje personaje;
    private float x = 0, y = 0;
    private Set<Integer> keysPresionadas = new HashSet<>();

    private int movimientoSeleccionado = -1;

    public ControlesJugador(Personaje personaje){
        this.personaje = personaje;
    }

    public void procesarEntrada() {
        this.x = 0;
        this.y = 0;

        if(keysPresionadas.contains(Input.Keys.LEFT)) {
            x -= 1;
            personaje.getMirilla().ocultarMirilla();
        }
        if(keysPresionadas.contains(Input.Keys.RIGHT)){
            x += 1;
            personaje.getMirilla().ocultarMirilla();
        }
        if(keysPresionadas.contains(Input.Keys.UP)) {
            y += 1;
            personaje.getMirilla().ocultarMirilla();
        }
        if(keysPresionadas.contains(Input.Keys.DOWN)) {
            y -= 1;
            personaje.getMirilla().ocultarMirilla();
        }

        if(keysPresionadas.contains(Input.Keys.W)){
            personaje.getMirilla().mostrarMirilla();
            personaje.getMirilla().cambiarAngulo(-1);
        }
        if(keysPresionadas.contains(Input.Keys.S)){
            personaje.getMirilla().mostrarMirilla();
            personaje.getMirilla().cambiarAngulo(1);
        }
        if(keysPresionadas.contains(Input.Keys.Z)) {
            movimientoSeleccionado = 0;
            //System.out.println("Controles Z");
            //personaje.usarMovimiento(movimientoSeleccionado);
        }
        if(keysPresionadas.contains(Input.Keys.X)) {
            movimientoSeleccionado = 1;
            //System.out.println("Controles X");
            //personaje.usarMovimiento(movimientoSeleccionado);
        }
    }
    public float getX(){ return x; }
    public float getY(){ return y; }

    public int getMovimientoSeleccionado() { return movimientoSeleccionado; }
    public void resetMovimientoSeleccionado() { movimientoSeleccionado = -1; }

    @Override public boolean keyDown(int keycode) { keysPresionadas.add(keycode); return true; }
    @Override public boolean keyUp(int keycode) { keysPresionadas.remove(keycode); return true; }
    @Override public boolean keyTyped(char character) { return false; }
    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchCancelled(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
    @Override public boolean scrolled(float amountX, float amountY) { return false; }
}
