package com.principal;

import com.badlogic.gdx.Game;
import screens.MenuScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class AntsArmageddon extends Game {

    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }



    @Override
    public void render() { super.render(); }

    @Override
    public void dispose() { super.dispose(); }
}
