package screens;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.principal.AntsArmageddon;
import hud.EventosBoton;
import hud.FabricaBotones;
import managers.GestorAssets;
import utils.Constantes;

public class MenuScreen extends ScreenMenus {

    public MenuScreen(AntsArmageddon juego) {
        super(juego);
    }

    @Override
    protected void construirUI() {

        Sound sonidoClick = GestorAssets.get(Constantes.SONIDO_CLICK, Sound.class);

        ImageButton jugar = FabricaBotones.JUGAR.crearBoton(sonidoClick, EventosBoton.irJuego(juego));
        ImageButton opciones = FabricaBotones.OPCIONES.crearBoton(sonidoClick, EventosBoton.irMenuOpciones(juego));
        ImageButton salir = FabricaBotones.SALIR.crearBoton(sonidoClick, EventosBoton.salirJuego());

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(jugar).pad(10).row();
        table.add(opciones).pad(10).row();
        table.add(salir).pad(10).row();

        escenario.addActor(table);
    }
}
