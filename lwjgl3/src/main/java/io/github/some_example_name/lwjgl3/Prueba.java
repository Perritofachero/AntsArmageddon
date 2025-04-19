package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Prueba extends ApplicationAdapter {
    SpriteBatch batch;
    Pixmap pixmap;
    Texture texture;
    int width, height;

    // Coordenadas y tamaño del cuadrado
    int cuadradoX = 100;
    int cuadradoY = 100;
    int cuadradoAncho = 100;
    int cuadradoAlto = 100;

    boolean cuadradoRoto = false;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // Cargar el mapa como Pixmap
        pixmap = new Pixmap(Gdx.files.internal("images.png"));
        width = pixmap.getWidth();
        height = pixmap.getHeight();

        // Convertir a textura
        texture = new Texture(pixmap);
    }

    @Override
    public void render() {
        // Detección de clic solo si el cuadrado no está roto
        if (Gdx.input.isTouched() && !cuadradoRoto) {
            int x = Gdx.input.getX();
            int y = height - Gdx.input.getY(); // invertir Y

            if (x >= cuadradoX && x <= cuadradoX + cuadradoAncho &&
                y >= cuadradoY && y <= cuadradoY + cuadradoAlto) {

                destruirTerreno(x, y, 40); // más grande para romper bien el cuadrado
                cuadradoRoto = true;
            }
        }

        // Dibujar el mapa
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(texture, 0, 0);
        batch.end();
    }

    private void destruirTerreno(int x, int y, int radio) {
        // Dibujar un círculo transparente (agujero)
        pixmap.setColor(0, 0, 0, 0); // color transparente
        pixmap.fillCircle(x, y, radio);

        // Liberar textura anterior y cargar nueva
        texture.dispose();
        texture = new Texture(pixmap);
    }

    @Override
    public void dispose() {
        batch.dispose();
        pixmap.dispose();
        texture.dispose();
    }
}
