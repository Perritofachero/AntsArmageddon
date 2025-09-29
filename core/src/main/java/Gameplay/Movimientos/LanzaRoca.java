package Gameplay.Movimientos;

import Gameplay.Gestores.GestorProyectiles;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import entidades.personajes.Personaje;
import entidades.proyectiles.Roca;

public class LanzaRoca extends MovimientoRango {

    public LanzaRoca(String nombre, Texture textura, int cooldown, float velocidad, int danio, GestorProyectiles gestorProyectiles) {
        super(nombre, textura, cooldown, 500, danio, gestorProyectiles);
    }

    @Override
    public void ejecutar(Personaje personaje, float potencia) {
        float radio = 10f;

        float angulo = personaje.getMirilla().getAnguloRad();

        float poscX = personaje.getX() + personaje.getSprite().getWidth() / 2f;
        float poscY = personaje.getY() + personaje.getSprite().getHeight() / 2f;

        float x = poscX + MathUtils.cos(angulo) * radio * personaje.getDireccionMultiplicador();
        float y = poscY + MathUtils.sin(angulo) * radio;

        float factorVelocidad = MathUtils.lerp(0.5f, 2.0f, potencia * potencia);
        float velocidadFinal = velocidad * factorVelocidad;

        Roca roca = new Roca(x, y, angulo, velocidadFinal, danio, gestorProyectiles.getGestorColisiones(), personaje);
        gestorProyectiles.agregar(roca);

    }
}

