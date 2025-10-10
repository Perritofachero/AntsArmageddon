package Gameplay.Movimientos.Melee;

import Gameplay.Gestores.GestorColisiones;
import Gameplay.Movimientos.MovimientoMelee;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import entidades.personajes.Personaje;
import managers.GestorAssets;
import utils.Constantes;
import utils.RecursosGlobales;

public class Aranazo extends MovimientoMelee {

    private static final float TIEMPO_DEBUG = 0.15f;

    private Rectangle areaGolpe;
    private float tiempoVisible = 0f;
    private boolean golpeAplicado = false;

    public Aranazo(GestorColisiones gestorColisiones) {
        super("Arañazo", GestorAssets.get(Constantes.PNG_2, Texture.class),
            10f, 40f, 10, gestorColisiones);
    }

    @Override
    public void ejecutar(Personaje atacante) {
        if (golpeAplicado) return;
        golpeAplicado = true;

        System.out.println("Ejecutando arañazo");

        float angulo = atacante.getMirilla().getAnguloRad();
        float distancia = 50f;

        float origenX = atacante.getX() + atacante.getSprite().getWidth() / 2f;
        float origenY = atacante.getY() + atacante.getSprite().getHeight() / 2f;

        float x = origenX + MathUtils.cos(angulo) * distancia * atacante.getDireccionMultiplicador() - anchoGolpe / 2f;
        float y = origenY + MathUtils.sin(angulo) * distancia - altoGolpe / 2f;

        areaGolpe = new Rectangle(x, y, anchoGolpe, altoGolpe);
        tiempoVisible = TIEMPO_DEBUG;

        aplicarGolpe(atacante, areaGolpe);
    }

    public void renderDebug(ShapeRenderer sr, float delta) {
        if (tiempoVisible <= 0 || areaGolpe == null) return;

        tiempoVisible -= delta;

        sr.setProjectionMatrix(RecursosGlobales.camaraPersonaje.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.RED);
        sr.rect(areaGolpe.x, areaGolpe.y, areaGolpe.width, areaGolpe.height);
        sr.end();

        if (tiempoVisible <= 0) {
            golpeAplicado = false;
        }
    }
}

