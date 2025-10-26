package hud;

import com.badlogic.gdx.Gdx;
import com.principal.AntsArmageddon;
import Gameplay.Gestores.Logicos.GestorScreen;
import screens.GameScreen;
import screens.MenuScreen;
import screens.OpcionesScreen;
import screens.PreGameScreen;
import partida.ConfiguracionPartida;

public class EventosBoton {

    public static Runnable irMenuOpciones(AntsArmageddon juego) {
        return () -> GestorScreen.setScreen(new OpcionesScreen(juego));
    }

    public static Runnable salirMenuOpciones(AntsArmageddon juego) {
        return () -> GestorScreen.setScreen(new MenuScreen(juego));
    }

    public static Runnable irJuego(AntsArmageddon juego, ConfiguracionPartida configuracion) {
        return () -> GestorScreen.setScreen(new GameScreen(juego, configuracion));
    }

    public static Runnable irPreGameScreen(AntsArmageddon juego) {
        return () -> GestorScreen.setScreen(new PreGameScreen(juego));
    }

    public static Runnable salirJuego() {
        return Gdx.app::exit;
    }

    public static Runnable descomponerAtlas() {
        return () -> utils.Utiles.descomponerAtlas("botones/botones.atlas", "atlasDescompuestos/");
    }

    public static Runnable descomponerSheet(String sheetPath, int cols, int rows, String outputFolder) {
        return () -> utils.Utiles.descomponerSheet(sheetPath, cols, rows, outputFolder);
    }

}
