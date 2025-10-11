package Fisicas;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import entidades.personajes.Personaje;

public class Camara {
    private OrthographicCamera camera;
    private Viewport viewport;

    private float mapWidth;
    private float mapHeight;

    private static final float LERP_FACTOR = 0.1f;


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

        float targetX = MathUtils.clamp(targetPosition.x, halfWidth, mapWidth - halfWidth);
        float targetY = MathUtils.clamp(targetPosition.y, halfHeight, mapHeight - halfHeight);

        Vector3 pos = camera.position;
        pos.x += (targetX - pos.x) * LERP_FACTOR;
        pos.y += (targetY - pos.y) * LERP_FACTOR;

        camera.position.set(pos.x, pos.y, 0);
        camera.update();
    }

    public void seguirPersonaje(Personaje personaje) {
        update(new Vector2(personaje.getX(), personaje.getY()));
    }

    public OrthographicCamera getCamera() { return camera; }
    public Viewport getViewport() { return viewport; }
}
