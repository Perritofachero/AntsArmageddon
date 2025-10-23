package screens;

import Gameplay.Gestores.GestorRutas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.principal.AntsArmageddon;
import hud.EventosBoton;
import hud.FabricaBotones;

public class OpcionesScreen extends ScreenMenus {

    public OpcionesScreen(AntsArmageddon juego) {
        super(juego);
    }

    @Override
    protected void construirUI() {

        ImageButton sonido = FabricaBotones.SONIDO.crearBoton(GestorRutas.ATLAS_BOTONES, GestorRutas.SONIDO_CLICK, EventosBoton.descomponerAtlas());
        ImageButton graficos = FabricaBotones.OPC1.crearBoton(GestorRutas.ATLAS_BOTONES, GestorRutas.SONIDO_CLICK, EventosBoton.irPreGameScreen(juego));
        ImageButton volver = FabricaBotones.VOLVER.crearBoton(GestorRutas.ATLAS_BOTONES, GestorRutas.SONIDO_CLICK, EventosBoton.salirMenuOpciones(juego));

        Table table = new Table();
        table.setFillParent(true);
        table.top().right();
        table.add(sonido).pad(10).row();
        table.add(graficos).pad(10).row();
        table.add(volver).pad(10).row();

        escenario.addActor(table);
    }
}
