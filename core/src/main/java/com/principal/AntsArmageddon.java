package com.principal;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import jdk.jshell.execution.Util;
import managers.GestorAssets;
import screens.MenuScreen;
import utils.Constantes;
import utils.Utiles;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class AntsArmageddon extends Game {

    private AssetManager assetManager;

    @Override
    public void create() {
        GestorAssets.load();

        setScreen(new MenuScreen(this));
    }

    @Override
    public void render() { super.render(); }
    @Override
    public void dispose() { super.dispose(); GestorAssets.dispose(); }
}
