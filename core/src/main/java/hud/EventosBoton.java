package hud;

import com.badlogic.gdx.Gdx;
import com.principal.AntsArmageddon;
import screens.GameScreen;
import screens.MenuScreen;
import screens.OpcionesScreen;
import utils.Utiles;

public class EventosBoton {

    public static Runnable irMenuOpciones(AntsArmageddon juego){
        return() -> juego.setScreen(new OpcionesScreen(juego, juego.getAssetManager()));
    }

    public static Runnable salirMenuOpciones(AntsArmageddon juego){
        return() -> juego.setScreen(new MenuScreen(juego, juego.getAssetManager()));
    }

    public static Runnable irJuego(AntsArmageddon juego){
        return() -> juego.setScreen(new GameScreen(juego, juego.getAssetManager()));
    }

    public static Runnable salirJuego(){
        return() -> Gdx.app.exit();
    }

    public static Runnable descomponerAtlas(){
        return() -> Utiles.descomponerAtlas("botones/pack.atlas", "atlasDescompuestos/");
    }
}
