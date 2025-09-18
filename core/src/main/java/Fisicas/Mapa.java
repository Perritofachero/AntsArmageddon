package Fisicas;

import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import entidades.Limite;
import utils.Constantes;

public class Mapa {

    private Limite limiteSuperior, limiteInferior, limiteIzquierdo, limiteDerecho;

    public Mapa(GestorColisiones gestor) {
        int ancho = Constantes.RESOLUCION_ANCHO_MAPA;
        int alto  = Constantes.RESOLUCION_ALTO_MAPA;
        //eliminar los - y + < a 100, son para ver los limites
        limiteSuperior  = new Limite(0, alto-100, ancho, 10);
        limiteInferior  = new Limite(0, -10+100, ancho, 10);
        limiteIzquierdo = new Limite(-10+100, 0, 10, alto);
        limiteDerecho   = new Limite(ancho-800, 0, 10, alto);

        gestor.agregarObjeto(limiteSuperior);
        gestor.agregarObjeto(limiteInferior);
        gestor.agregarObjeto(limiteIzquierdo);
        gestor.agregarObjeto(limiteDerecho);
    }

    public void draw(ShapeRenderer shapeRenderer, Camara camara) {
        shapeRenderer.setProjectionMatrix(camara.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        limiteSuperior.draw(shapeRenderer);
        limiteInferior.draw(shapeRenderer);
        limiteIzquierdo.draw(shapeRenderer);
        limiteDerecho.draw(shapeRenderer);

        shapeRenderer.end();

        shapeRenderer.setColor(Color.WHITE);

    }
}
