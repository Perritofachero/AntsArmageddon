package entidades.personajes;

import Gameplay.Gestores.GestorColisiones;
import com.badlogic.gdx.math.Vector2;

public class FisicaPersonaje {

    //Hacer que el resbale funcione en pendientes, y qyue no se pare en pixeles.
    private final Personaje personaje;
    private final GestorColisiones gestorColisiones;

    private boolean enKnockback = false;
    private float tiempoResbale = 0.6f;
    private float tiempoDesdeContacto = 0f;

    public FisicaPersonaje(Personaje personaje, GestorColisiones gestorColisiones) {
        this.personaje = personaje;
        this.gestorColisiones = gestorColisiones;
    }

    public void actualizar(float delta) {
        Vector2 vel = personaje.getVelocidad();

        if ((enKnockback || Math.abs(vel.x) > 0.1f) && personaje.getSobreAlgo()) {
            tiempoDesdeContacto += delta;

            float k = (float) (Math.log(50) / tiempoResbale);
            float factor = (float) Math.exp(-k * tiempoDesdeContacto * delta);

            vel.x *= factor;

            if (tiempoDesdeContacto > tiempoResbale || Math.abs(vel.x) < 1f) {
                vel.x = 0;
                enKnockback = false;
                tiempoDesdeContacto = 0;
            }

            personaje.setVelocidad(vel);
        } else if (!personaje.getSobreAlgo()) {
            tiempoDesdeContacto = 0;
        }
    }

    public void moverHorizontal(float deltaX, float deltaTiempo) {
        if (enKnockback || deltaX == 0) return;

        if (deltaX < 0) {
            personaje.setDireccion(false);
            if (!personaje.getSprite().isFlipX())
                personaje.getSprite().flip(true, false);
        } else {
            personaje.setDireccion(true);
            if (personaje.getSprite().isFlipX())
                personaje.getSprite().flip(true, false);
        }

        float nuevaX = personaje.getX() + deltaX * personaje.getVelocidadX() * deltaTiempo;
        boolean puedeMoverX = gestorColisiones.verificarMovimiento(personaje, nuevaX, personaje.getY());

        if (puedeMoverX) {
            personaje.setX(nuevaX);
        } else {
            int maxAscenso = 4;
            for (int i = 1; i <= maxAscenso; i++) {
                if (gestorColisiones.verificarMovimiento(personaje, nuevaX, personaje.getY() + i)) {
                    personaje.setX(nuevaX);
                    personaje.setY(personaje.getY() + i);
                    break;
                }
            }
        }

        personaje.updateHitbox();
    }

    public void moverVertical(float deltaY, float deltaTiempo) {
        if (enKnockback || deltaY == 0) return;

        float nuevaY = personaje.getY() + deltaY * personaje.getVelocidad().y * deltaTiempo;
        float pasoY = 1f;

        if (deltaY > 0) {
            while (personaje.getY() < nuevaY) {
                if (gestorColisiones.verificarMovimiento(personaje, personaje.getX(), personaje.getY() + pasoY)) {
                    personaje.setY(personaje.getY() + pasoY);
                } else {
                    personaje.setY((float) Math.floor(personaje.getY()));
                    personaje.getVelocidad().y = 0;
                    break;
                }
            }
            personaje.setSobreAlgo(false);
        } else {
            while (personaje.getY() > nuevaY) {
                if (gestorColisiones.verificarMovimiento(personaje, personaje.getX(), personaje.getY() - pasoY)) {
                    personaje.setY(personaje.getY() - pasoY);
                } else {
                    personaje.setY((float) Math.ceil(personaje.getY()));
                    personaje.getVelocidad().y = 0;
                    personaje.setSobreAlgo(true);
                    break;
                }
            }
            if (personaje.getVelocidad().y != 0)
                personaje.setSobreAlgo(false);
        }

        personaje.updateHitbox();
    }

    public void aplicarKnockback(float fuerzaX, float fuerzaY) {
        Vector2 vel = personaje.getVelocidad();
        vel.x = fuerzaX;
        vel.y = fuerzaY;
        personaje.setVelocidad(vel);
        enKnockback = true;
        personaje.setSobreAlgo(false);
        tiempoDesdeContacto = 0;
    }

    public void saltar(float fuerzaSalto) {
        if (personaje.getSobreAlgo() && !enKnockback) {
            Vector2 vel = personaje.getVelocidad();
            vel.y = fuerzaSalto;
            personaje.setVelocidad(vel);
            personaje.setSobreAlgo(false);
            tiempoDesdeContacto = 0;
        }
    }


    public float getTiempoResbale() { return tiempoResbale; }
    public boolean estaEnKnockback() { return enKnockback; }
}
