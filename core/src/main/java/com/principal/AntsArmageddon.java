package com.principal;

import com.badlogic.gdx.Game;
import managers.GestorAssets;
import managers.ScreenManager;
import screens.MenuScreen;
import utils.RecursosGlobales;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class AntsArmageddon extends Game {

    @Override
    public void create() {
        RecursosGlobales.inicializar();
        GestorAssets.load();

        ScreenManager.setJuego(this);
        setScreen(new MenuScreen(this));
    }

    @Override
    public void render() { super.render(); }
    @Override
    public void dispose() {
        super.dispose();
        GestorAssets.dispose();
        RecursosGlobales.dispose();
    }
}
