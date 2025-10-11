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
        ImageButton jugar = FabricaBotones.JUGAR.crearBoton(EventosBoton.irJuego(juego));
        ImageButton opciones = FabricaBotones.OPCIONES.crearBoton(EventosBoton.irMenuOpciones(juego));
        ImageButton salir = FabricaBotones.SALIR.crearBoton(EventosBoton.salirJuego());

        Sound sonidoClick = GestorAssets.get(Constantes.SONIDO_CLICK, Sound.class);
        FabricaBotones.agregarSonido(jugar, sonidoClick);
        FabricaBotones.agregarSonido(opciones, sonidoClick);
        FabricaBotones.agregarSonido(salir, sonidoClick);

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(jugar).pad(10).row();
        table.add(opciones).pad(10).row();
        table.add(salir).pad(10).row();

        escenario.addActor(table);
    }
}
