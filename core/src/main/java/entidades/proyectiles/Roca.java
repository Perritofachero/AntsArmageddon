package entidades.proyectiles;

import Fisicas.Colisionable;
import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import entidades.personajes.Personaje;
import managers.GestorAssets;

public class Roca extends Proyectil {

    private Texture textura;
    private Sprite sprite;
    private int direccion;

    private float velocidadY;

    public Roca(float x, float y, float angulo, float velocidad, int dano, GestorColisiones gestorColisiones, Personaje ejecutor) {
        super(x, y, angulo, velocidad, dano, gestorColisiones, ejecutor);

        this.textura = GestorAssets.get("roca.png", Texture.class);
        this.sprite = new Sprite(textura);
        this.sprite.setPosition(x, y);

        this.velocidadY = MathUtils.sin(angulo) * velocidad;

        this.hitbox.setWidth(sprite.getWidth());
        this.hitbox.setHeight(sprite.getHeight());

        this.ejecutor = ejecutor;
        this.direccion = ejecutor.getDireccionMultiplicador();

        this.activo = true;
    }

    @Override
    protected Vector2 calcularMovimiento(float delta) {
        float dx = MathUtils.cos(angulo) * velocidad * delta * direccion;
        float dy = MathUtils.sin(angulo) * velocidad * delta;

        return new Vector2(dx, dy);
    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.setPosition(this.x, this.y);
        sprite.draw(batch);
    }

    @Override
    protected void impactoProyectil(Colisionable impactado) {
        if (impactado instanceof Personaje) {
            Personaje p = (Personaje) impactado;
            p.recibirDanio(this.danio);
            this.activo = false;
        }
    }

    @Override public void dispose() {}
}
