package hud;

import com.badlogic.gdx.Gdx;
import com.principal.AntsArmageddon;
import managers.ScreenManager;
import screens.GameScreen;
import screens.MenuScreen;
import screens.OpcionesScreen;
import screens.PreGameScreen;
import partida.ConfiguracionPartida;

public class EventosBoton {

    public static Runnable irMenuOpciones(AntsArmageddon juego) {
        return () -> ScreenManager.setScreen(new OpcionesScreen(juego));
    }

    public static Runnable salirMenuOpciones(AntsArmageddon juego) {
        return () -> ScreenManager.setScreen(new MenuScreen(juego));
    }

    public static Runnable irJuego(AntsArmageddon juego, ConfiguracionPartida configuracion) {
        return () -> ScreenManager.setScreen(new GameScreen(juego, configuracion));
    }

    public static Runnable irPreGameScreen(AntsArmageddon juego) {
        return () -> ScreenManager.setScreen(new PreGameScreen(juego));
    }

    public static Runnable salirJuego() {
        return Gdx.app::exit;
    }

    public static Runnable descomponerAtlas() {
        return () -> utils.Utiles.descomponerAtlas("botones/botones.atlas", "atlasDescompuestos/");
    }
}
