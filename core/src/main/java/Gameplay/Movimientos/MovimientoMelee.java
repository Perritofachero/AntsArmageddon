package Gameplay.Movimientos;

import Fisicas.Colisionable;
import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import entidades.personajes.Personaje;
import utils.RecursosGlobales;
import java.util.List;

public abstract class MovimientoMelee extends Movimiento {

    protected final float anchoGolpe;
    protected final float altoGolpe;
    protected final int danio;
    protected final float distanciaGolpe;
    protected final GestorColisiones gestorColisiones;

    protected Rectangle areaGolpe;
    protected float tiempoVisible = 0f;
    protected boolean golpeAplicado = false;

    public MovimientoMelee(String nombre, Texture textura, float ancho, float alto, int danio, float distanciaGolpe, GestorColisiones gestorColisiones) {
        super(nombre, textura);

        this.anchoGolpe = ancho;
        this.altoGolpe = alto;
        this.danio = danio;
        this.distanciaGolpe = distanciaGolpe;
        this.gestorColisiones = gestorColisiones;
    }

    public void ejecutar(Personaje atacante) {
        if (golpeAplicado || atacante == null || !atacante.getActivo()) return;
        golpeAplicado = true;

        float angulo = atacante.getMirilla().getAnguloRad();

        float origenX = atacante.getX() + atacante.getSprite().getWidth() / 2f;
        float origenY = atacante.getY() + atacante.getSprite().getHeight() / 2f;

        float x = origenX + MathUtils.cos(angulo) * distanciaGolpe * atacante.getDireccionMultiplicador() - anchoGolpe / 2f;
        float y = origenY + MathUtils.sin(angulo) * distanciaGolpe - altoGolpe / 2f;

        areaGolpe = new Rectangle(x, y, anchoGolpe, altoGolpe);
        tiempoVisible = 0.15f;

        aplicarGolpe(atacante, areaGolpe);
    }

    protected void aplicarGolpe(Personaje atacante, Rectangle area) {
        List<Colisionable> colisionados = gestorColisiones.getColisionablesEnRect(area, atacante);
        if (colisionados == null || colisionados.isEmpty()) return;

        for (Colisionable c : colisionados) {
            if (c instanceof Personaje enemigo && enemigo != atacante && enemigo.getActivo()) {
                enemigo.recibirDanio(danio, 1f, 1f);
            }
        }
    }

    public void renderDebug(ShapeRenderer sr, float delta) {
        if (tiempoVisible <= 0 || areaGolpe == null) return;
        tiempoVisible -= delta;

        sr.setProjectionMatrix(RecursosGlobales.camaraPersonaje.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.RED);
        sr.rect(areaGolpe.x, areaGolpe.y, areaGolpe.width, areaGolpe.height);
        sr.end();

        if (tiempoVisible <= 0) golpeAplicado = false;
    }

    protected float getDistanciaGolpe() { return 50f; }

}
