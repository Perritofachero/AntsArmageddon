package com.principal;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import jdk.jshell.execution.Util;
import screens.MenuScreen;
import utils.Constantes;
import utils.Utiles;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class AntsArmageddon extends Game {

    private AssetManager assetManager;

    @Override
    public void create() {
        cargarAssets();

        setScreen(new MenuScreen(this, assetManager));
    }

    @Override
    public void render() { super.render(); }

    @Override
    public void dispose() { super.dispose(); assetManager.dispose(); }

    public void cargarAssets() {
        assetManager = new AssetManager();

        assetManager.load(Constantes.ATLAS_BOTONES, TextureAtlas.class);
        assetManager.load("sonido_click.mp3", Sound.class);
        assetManager.load("fondoPantalla.png", Texture.class);
        assetManager.load("Mapa.png", Texture.class);

        assetManager.finishLoading();

    }

    public AssetManager getAssetManager(){
        return this.assetManager;
    }

}
