package entidades.personajes.PowerUps;

import Fisicas.Camara;
import Fisicas.Colisionable;
import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import entidades.Entidad;
import entidades.personajes.Personaje;

public abstract class PowerUp extends Entidad {

    protected Rectangle areaRecoleccion;
    protected float extraArea = 3f;

    public PowerUp(float x, float y, Texture textura, GestorColisiones gestorColisiones) {
        super(x, y, textura, gestorColisiones);
        this.velocidad.set(0, 0);
        this.areaRecoleccion = new Rectangle();
        actualizarAreaRecoleccion();

    }

    @Override public void render(SpriteBatch batch) {
        if (!activo) return;
        sprite.draw(batch);
    }

    @Override public void actualizar(float delta) {
        if (!activo) return;

        if (sprite != null) sprite.setPosition(x, y);
        updateHitbox();
        actualizarAreaRecoleccion();

        Personaje personaje = detectarPersonajeColicion();
        if (personaje != null) {
            aplicarEfecto(personaje);
            desactivar();
        }
    }

    protected void actualizarAreaRecoleccion() {
        areaRecoleccion.set(
            sprite.getX() - extraArea,
            sprite.getY() - extraArea,
            sprite.getWidth() + 2 * extraArea,
            sprite.getHeight() + 2 * extraArea
        );
    }

    protected Personaje detectarPersonajeColicion() {
        for (Colisionable c : gestorColisiones.getColisionables()) {
            if (!(c instanceof Personaje personaje)) continue;
            if (!personaje.getActivo()) continue;

            if (areaRecoleccion.overlaps(personaje.getHitbox())) return personaje;
        }
        return null;
    }

    public void setExtraRecogida(float extra) {
        this.extraArea = extra;
        actualizarAreaRecoleccion();
    }

    public abstract void aplicarEfecto(Personaje personaje);

    public void renderHitbox(ShapeRenderer shapeRenderer, Camara camara) {
        if (!activo) return;

        shapeRenderer.setProjectionMatrix(camara.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(areaRecoleccion.x, areaRecoleccion.y, areaRecoleccion.width, areaRecoleccion.height);

        shapeRenderer.end();
    }
}
