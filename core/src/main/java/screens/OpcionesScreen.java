package screens;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.principal.AntsArmageddon;
import hud.EventosBoton;
import hud.FabricaBotones;
import managers.GestorAssets;
import utils.Constantes;

public class OpcionesScreen extends ScreenMenus {

    public OpcionesScreen(AntsArmageddon juego) {
        super(juego);
    }

    @Override
    protected void construirUI() {

        Sound sonidoClick = GestorAssets.get(Constantes.SONIDO_CLICK, Sound.class);

        ImageButton sonido = FabricaBotones.SONIDO.crearBoton(sonidoClick, EventosBoton.descomponerAtlas());
        ImageButton graficos = FabricaBotones.OPC1.crearBoton(sonidoClick, EventosBoton.irPreGameScreen(juego));
        ImageButton volver = FabricaBotones.VOLVER.crearBoton(sonidoClick, EventosBoton.salirMenuOpciones(juego));

        Table table = new Table();
        table.setFillParent(true);
        table.top().right();
        table.add(sonido).pad(10).row();
        table.add(graficos).pad(10).row();
        table.add(volver).pad(10).row();

        escenario.addActor(table);
    }
}
