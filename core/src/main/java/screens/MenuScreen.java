package screens;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.principal.AntsArmageddon;
import hud.EventosBoton;
import hud.FabricaBotones;
import utils.Constantes;

public class MenuScreen extends ScreenMenus {

    public MenuScreen(AntsArmageddon juego) {
        super(juego);
    }

    @Override
    protected void construirUI() {

        ImageButton jugar = FabricaBotones.JUGAR.crearBoton(Constantes.ATLAS_BOTONES, Constantes.SONIDO_CLICK, EventosBoton.irPreGameScreen(juego));
        ImageButton opciones = FabricaBotones.OPCIONES.crearBoton(Constantes.ATLAS_BOTONES, Constantes.SONIDO_CLICK, EventosBoton.irMenuOpciones(juego));
        ImageButton salir = FabricaBotones.SALIR.crearBoton(Constantes.ATLAS_BOTONES, Constantes.SONIDO_CLICK, EventosBoton.salirJuego());

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(jugar).pad(10).row();
        table.add(opciones).pad(10).row();
        table.add(salir).pad(10).row();

        escenario.addActor(table);
    }
}
