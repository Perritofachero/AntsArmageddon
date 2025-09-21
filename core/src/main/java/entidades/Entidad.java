package entidades;

import Fisicas.Fisica;
import Fisicas.Mapa;
import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import Fisicas.Colisionable;

public abstract class Entidad implements Colisionable {

    protected float x, y;
    protected Rectangle hitbox;
    protected boolean sobreAlgo;

    public Entidad(float x, float y, GestorColisiones gestorColisiones){
        this.x = x;
        this.y = y;
        this.hitbox = new Rectangle(x, y, 0, 0);
        this.sobreAlgo = verificarSobreAlgo(gestorColisiones);
    }

    public boolean verificarSobreAlgo(GestorColisiones gestorColisiones) {
        Rectangle rect = new Rectangle(hitbox.x, hitbox.y - 1, hitbox.width, 1);
        return gestorColisiones.colisionaConAlgo(this, rect);
    }

    public abstract void aplicarFisica(Fisica fisica, Mapa mapa, float delta);
    protected void updateHitbox() { hitbox.setPosition(x, y); }
    public abstract void actualizar(float delta);
    public abstract void render(SpriteBatch batch);

    @Override public Rectangle getHitboxPosicion(float x, float y) { return new Rectangle(x, y, hitbox.getWidth(), hitbox.getHeight()); }
    public Rectangle getHitbox() { return hitbox; }
    public void dispose() { }
}
