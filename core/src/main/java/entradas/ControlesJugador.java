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
    private boolean proyectilDisparado = false;

    public ControlesJugador(Personaje personaje){
        this.personaje = personaje;
    }

    public void procesarEntrada() {
        this.x = 0;
        this.y = 0;

        if (keysPresionadas.contains(Input.Keys.UP)){
            personaje.getMirilla().mostrarMirilla();
            personaje.getMirilla().cambiarAngulo(-1);
        }
        if (keysPresionadas.contains(Input.Keys.DOWN)){
            personaje.getMirilla().mostrarMirilla();
            personaje.getMirilla().cambiarAngulo(1);
        }
        if (keysPresionadas.contains(Input.Keys.LEFT)) {
            this.x -= 1;
            personaje.getMirilla().ocultarMirilla();
        }
        if (keysPresionadas.contains(Input.Keys.RIGHT)){
            personaje.getMirilla().ocultarMirilla();
            this.x += 1;
        }
        if (keysPresionadas.contains(Input.Keys.SPACE)){
            proyectilDisparado = true;
        }

    }

    @Override
    public boolean keyDown(int keycode) {
        keysPresionadas.add(keycode);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        keysPresionadas.remove(keycode);
        return true;
    }

    @Override
    public boolean keyTyped(char character) { return false; }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) { return false; }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }

    @Override
    public boolean mouseMoved(int screenX, int screenY) { return false; }

    @Override
    public boolean scrolled(float amountX, float amountY) { return false; }



    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public boolean getProyectilDisparado(){
        return proyectilDisparado;
    }

    public void setProyectilDisparado(boolean nuevoEstado){
        proyectilDisparado = nuevoEstado;
    }
}
