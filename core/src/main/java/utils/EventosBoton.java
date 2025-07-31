package utils;

import com.badlogic.gdx.Gdx;
import com.principal.AntsArmageddon;
import screens.GameScreen;
import screens.MenuScreen;
import screens.OpcionesScreen;

public class EventosBoton {

    public static Runnable salirDelJuego(){
        return() -> Gdx.app.exit();
    }

    public static Runnable irMenuOpciones(AntsArmageddon juego){
        return() -> juego.setScreen(new OpcionesScreen(juego));
    }

    public static Runnable salirMenuOpciones(AntsArmageddon juego){
        return() -> juego.setScreen(new MenuScreen(juego));
    }

    public static Runnable irJuego(AntsArmageddon juego){
        return() -> juego.setScreen(new GameScreen(juego));
    }

    public static Runnable salirJuego(AntsArmageddon juego){
        return() -> juego.setScreen(new MenuScreen(juego));
    }

}
