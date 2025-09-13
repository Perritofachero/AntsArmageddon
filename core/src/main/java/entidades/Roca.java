package entidades;

import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Roca extends Proyectil {

    private Texture textura;
    private Sprite sprite;
    private float velocidadY;
    private float direccion;

    public Roca(float x, float y, float angulo, float velocidad, int dano, GestorColisiones gestorColisiones, Personaje ejecutor) {
        super(x, y, angulo, velocidad, dano, gestorColisiones, ejecutor);

        this.textura = new Texture("proyectil.png");
        this.sprite = new Sprite(textura);
        this.sprite.setPosition(x, y);

        this.velocidadY = MathUtils.sin(angulo) * velocidad;

        this.hitbox.setWidth(sprite.getWidth());
        this.hitbox.setHeight(sprite.getHeight());

        this.ejecutor = ejecutor;

        this.direccion = ejecutor.getDireccionMultiplicador();

        this.activo = true;
    }

    private boolean primerFrame = true;

    @Override
    public void mover(float delta) {
        tiempoTranscurrido += delta;

        float nuevaX = this.x + MathUtils.cos(angulo) * velocidad * delta * direccion;
        float nuevaY = this.y + MathUtils.sin(angulo) * velocidad * delta;

        Personaje ignorar = null;
        if (tiempoTranscurrido < tiempoGracia) {
            ignorar = ejecutor;
        }

        if (gestor.verificarHitbox(this, nuevaX, nuevaY, ignorar)) {
            x = nuevaX;
            y = nuevaY;
            update();
        } else {
            desactivar();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.setPosition(this.x, this.y);
        sprite.draw(batch);
    }

    @Override
    public void dispose() {
        textura.dispose();
    }
}
