package Gameplay.Movimientos;

import Gameplay.Gestores.GestorProyectiles;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import entidades.personajes.Personaje;
import entidades.proyectiles.Roca;

public class LanzaRoca extends MovimientoRango {

    public LanzaRoca(String nombre, Texture textura, int cooldown, float velocidad, int danio, GestorProyectiles gestorProyectiles) {
        super(nombre, textura, cooldown, 1000, danio, gestorProyectiles);
    }

    @Override
    public void ejecutar(Personaje personaje) {
        float radio = 10f;

        float angulo = personaje.getMirilla().getAnguloRad();

        float poscX = personaje.getX() + personaje.getSprite().getWidth() / 2f;
        float poscY = personaje.getY() + personaje.getSprite().getHeight() / 2f;

        float x = poscX + MathUtils.cos(angulo) * radio * personaje.getDireccionMultiplicador();
        float y = poscY + MathUtils.sin(angulo) * radio;

        Roca roca = new Roca(x, y, angulo, velocidad, danio, gestorProyectiles.getGestorColisiones(), personaje);

        gestorProyectiles.agregar(roca);
        System.out.println("Creando roca en x=" + x + ", y=" + y + " con angulo=" + angulo);
    }
}
