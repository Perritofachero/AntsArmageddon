package entidades;

import Fisicas.Fisica;
import Fisicas.Mapa;
import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import Fisicas.Colisionable;

public abstract class Entidad implements Colisionable {

    protected float x, y;
    protected Rectangle hitbox;
    protected Sprite sprite;
    protected Texture textura;
    protected boolean sobreAlgo;
    protected GestorColisiones gestorColisiones;

    public Entidad(float x, float y, GestorColisiones gestorColisiones) {
        this.x = x;
        this.y = y;
        this.gestorColisiones = gestorColisiones;
        this.hitbox = new Rectangle(x, y, 0, 0);
        this.sobreAlgo = verificarSobreAlgo(gestorColisiones);
    }

    public boolean verificarSobreAlgo(GestorColisiones gestorColisiones) {
        Rectangle rect = new Rectangle(hitbox.x, hitbox.y - 1, hitbox.width, 1);
        return gestorColisiones.colisionaConAlgo(this, rect);
    }

    protected void updateHitbox() { hitbox.setPosition(x, y); }
    public abstract void aplicarFisica(Fisica fisica, Mapa mapa, float delta);
    public abstract void actualizar(float delta);
    public abstract void render(SpriteBatch batch);
    @Override public Rectangle getHitboxPosicion(float x, float y) { return new Rectangle(x, y, hitbox.getWidth(), hitbox.getHeight()); }
    public Rectangle getHitbox() { return hitbox; }
    public void dispose() {}
}
