package Fisicas;

import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import entidades.Limite;
import utils.Constantes;

public class Borde {

    private Limite limiteSuperior, limiteInferior, limiteIzquierdo, limiteDerecho;

    public Borde(GestorColisiones gestor) {
        final int ancho = Constantes.RESOLUCION_ANCHO_MAPA;
        final int alto  = Constantes.RESOLUCION_ALTO_MAPA;
        final int grosor = 10;

        limiteSuperior  = new Limite(0, alto, ancho, grosor);
        limiteInferior  = new Limite(0, -grosor, ancho, grosor);
        limiteIzquierdo = new Limite(-grosor, 0, grosor, alto);
        limiteDerecho   = new Limite(ancho, 0, grosor, alto);

        gestor.agregarObjeto(limiteSuperior);
        gestor.agregarObjeto(limiteInferior);
        gestor.agregarObjeto(limiteIzquierdo);
        gestor.agregarObjeto(limiteDerecho);
    }

    public void draw(ShapeRenderer sr, Camara camara) {
        sr.setProjectionMatrix(camara.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);

        limiteSuperior.draw(sr);
        limiteInferior.draw(sr);
        limiteIzquierdo.draw(sr);
        limiteDerecho.draw(sr);

        sr.end();
        sr.setColor(Color.WHITE);
    }
}
