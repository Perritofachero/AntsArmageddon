package entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import logica.Colisionable;
import logica.GestorDeColisiones;

public abstract class Entidad implements Colisionable {

    protected float x, y;
    protected Texture textura;
    protected Sprite sprite;
    protected Rectangle hitbox;
    protected GestorDeColisiones gestor;

    public Entidad(String rutaTextura, GestorDeColisiones gestor, float x, float y){
        this.x = x;
        this.y = y;
        this.textura = new Texture(Gdx.files.internal(rutaTextura));
        this.sprite = new Sprite(textura);
        this.sprite.setPosition(x, y);
        this.hitbox = new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
        this.gestor = gestor;

    }

    @Override
    public Rectangle getHitboxPosicion(float x, float y) {
        return new Rectangle(x, y, hitbox.getWidth(), hitbox.getHeight());
    }

    protected void update(){
        sprite.setPosition(x, y);
        hitbox.setPosition(x, y);

    }



    public Rectangle getHitbox(){ return hitbox; }

    public void render(SpriteBatch batch){ sprite.draw(batch); }

    public void dispose(){ sprite.getTexture().dispose(); }
}
