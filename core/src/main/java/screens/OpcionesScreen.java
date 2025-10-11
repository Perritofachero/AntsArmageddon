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
        ImageButton sonido = FabricaBotones.SONIDO.crearBoton(() -> System.out.println("Sonido presionado"));
        ImageButton graficos = FabricaBotones.OPC1.crearBoton(() -> System.out.println("Gráficos presionado"));
        ImageButton volver = FabricaBotones.VOLVER.crearBoton(EventosBoton.salirMenuOpciones(juego));

        Sound sonidoClick = GestorAssets.get(Constantes.SONIDO_CLICK, Sound.class);
        FabricaBotones.agregarSonido(sonido, sonidoClick);
        FabricaBotones.agregarSonido(graficos, sonidoClick);
        FabricaBotones.agregarSonido(volver, sonidoClick);

        Table table = new Table();
        table.setFillParent(true);
        table.top().right();
        table.add(sonido).pad(10).row();
        table.add(graficos).pad(10).row();
        table.add(volver).pad(10).row();

        escenario.addActor(table);
    }
}
