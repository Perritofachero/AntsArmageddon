package screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.principal.AntsArmageddon;
import managers.GestorAssets;

public class GameOverScreen implements Screen {
//Hacer que extienda de ScreensMenus
    private AntsArmageddon juego;
    private SpriteBatch batch;
    private Sprite spriteFondo;


    public GameOverScreen(AntsArmageddon juego) {
        this.juego = juego;
        batch = new SpriteBatch();

        spriteFondo = new Sprite(GestorAssets.get("gameOver.png", com.badlogic.gdx.graphics.Texture.class));
        spriteFondo.setPosition(
            Gdx.graphics.getWidth() / 2f - spriteFondo.getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f - spriteFondo.getHeight() / 2f
        );
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        spriteFondo.draw(batch);
        batch.end();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { batch.dispose(); }
}
