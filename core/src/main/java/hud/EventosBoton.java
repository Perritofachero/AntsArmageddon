package hud;

import com.badlogic.gdx.Gdx;
import com.principal.AntsArmageddon;
import managers.ScreenManager;
import screens.GameScreen;
import screens.MenuScreen;
import screens.OpcionesScreen;

public class EventosBoton {

    public static Runnable irMenuOpciones(AntsArmageddon juego) {
        return () -> ScreenManager.setScreen(new OpcionesScreen(ScreenManager.returnJuego()));
    }

    public static Runnable salirMenuOpciones(AntsArmageddon juego) {
        return () -> ScreenManager.setScreen(new MenuScreen(ScreenManager.returnJuego()));
    }

    public static Runnable irJuego(AntsArmageddon juego) {
        return () -> ScreenManager.setScreen(new GameScreen(ScreenManager.returnJuego()));
    }

    public static Runnable salirJuego() {
        return Gdx.app::exit;
    }

    public static Runnable descomponerAtlas() {
        return () -> utils.Utiles.descomponerAtlas("botones/pack.atlas", "atlasDescompuestos/");
    }
}
