package Fisicas;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import entidades.Personaje;

public class Camara {
    private OrthographicCamera camera;
    private Viewport viewport;

    private float mapWidth;
    private float mapHeight;

    public Camara(float worldWidth, float worldHeight, float mapWidth, float mapHeight) {
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(worldWidth, worldHeight, camera);

        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;

        camera.position.set(mapWidth / 2f, mapHeight / 2f, 0);
        camera.update();

    }

    public void update(Vector2 targetPosition) {
        float halfWidth = viewport.getWorldWidth() / 2f;
        float halfHeight = viewport.getWorldHeight() / 2f;

        float x = MathUtils.clamp(targetPosition.x, halfWidth, mapWidth - halfWidth);
        float y = MathUtils.clamp(targetPosition.y, halfHeight, mapHeight - halfHeight);

        camera.position.set(x, y, 0);
        camera.update();
    }

    public void seguirPersonaje(Personaje personaje) {
        update(new Vector2(personaje.getX(), personaje.getY()));
    }

    public OrthographicCamera getCamera() { return camera; }
    public Viewport getViewport() { return viewport; }
}
