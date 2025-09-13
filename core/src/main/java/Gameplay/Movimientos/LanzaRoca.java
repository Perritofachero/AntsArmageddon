package Gameplay.Movimientos;


import Gameplay.Gestores.GestorProyectiles;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import entidades.Personaje;
import entidades.Roca;

public class LanzaRoca extends MovimientoRango {

    public LanzaRoca(String nombre, Sprite sprite, float cooldown, float velocidad, int danio, GestorProyectiles gestorProyectiles) {
        super(nombre, sprite, cooldown, velocidad, danio, gestorProyectiles);
    }

    @Override
    public void ejecutar(Personaje personaje) {
        float angulo = personaje.getMirilla().getAnguloRad();

        float startX = personaje.getX() + personaje.getSprite().getWidth() / 2f + MathUtils.cos(angulo) * 10;
        float startY = personaje.getY() + personaje.getSprite().getHeight() / 2f + MathUtils.sin(angulo) * 10;

        Roca roca = new Roca(startX, startY, angulo, velocidad, danio, gestorProyectiles.getGestorColisiones(), personaje);

        gestorProyectiles.agregar(roca);
        System.out.println("LanzaRoca ejecutado con gestorProyectiles=" + gestorProyectiles);
    }
}
