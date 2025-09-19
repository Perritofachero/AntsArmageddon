package managers;

import com.badlogic.gdx.Screen;
import com.principal.AntsArmageddon;

public class ScreenManager {

    private static AntsArmageddon juego;

    public static void setJuego(AntsArmageddon applicationListener) {
        juego = applicationListener;

    }

    public static void setScreen(Screen screen) {
        if(juego == null){
            System.out.println("Error, juego null");
        }else {
            juego.setScreen(screen);
        }
    }

    public static AntsArmageddon returnJuego() { return juego; }
}
