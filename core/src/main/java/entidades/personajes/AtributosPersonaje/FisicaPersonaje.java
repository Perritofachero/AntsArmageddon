package entidades.personajes.AtributosPersonaje;

import Gameplay.Gestores.Logicos.GestorColisiones;
import com.badlogic.gdx.math.Vector2;
import entidades.personajes.Personaje;

//Hacer que el resbale funcione en pendientes, y qyue no se pare en pixeles.
//Tal vez hacer que cuando esta en knockback rebote, en vez de resbalar

//Fijarse si podemos hacer que siempre que el personaje se este desplazando en x
//tenga tolerancia de subida o algo asi

public class FisicaPersonaje {

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
            if (!personaje.getSprite().isFlipX()) personaje.getSprite().flip(true, false);
        } else {
            personaje.setDireccion(true);
            if (personaje.getSprite().isFlipX()) personaje.getSprite().flip(true, false);
        }

        float nuevaX = personaje.getX() + deltaX * personaje.getVelocidadX() * deltaTiempo;

        boolean libreEnX = gestorColisiones.verificarMovimiento(personaje, nuevaX, personaje.getY());

        if (libreEnX) {
            personaje.setX(nuevaX);
        } else {
            if (personaje.getSobreAlgo()) {
                final int maxAscenso = 4;
                boolean subio = false;

                for (int i = 1; i <= maxAscenso; i++) {
                    float yCandidata = personaje.getY() + i;
                    if (gestorColisiones.verificarMovimiento(personaje, personaje.getX(), yCandidata) &&
                        gestorColisiones.verificarMovimiento(personaje, nuevaX, yCandidata)) {
                        personaje.setY(yCandidata);
                        personaje.setX(nuevaX);
                        subio = true;
                        break;
                    }
                }

                if (!subio) {
                    Vector2 vel = personaje.getVelocidad();
                    vel.x = 0;
                    personaje.setVelocidad(vel);
                }
            } else {
                Vector2 vel = personaje.getVelocidad();
                vel.x = 0;
                personaje.setVelocidad(vel);
            }
        }

        personaje.updateHitbox();
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

    public void aplicarKnockback(float fuerzaX, float fuerzaY) {
        float divisor = Math.max(0.5f, personaje.getPeso() * 0.1f);

        Vector2 vel = personaje.getVelocidad();
        vel.x = fuerzaX / divisor;
        vel.y = fuerzaY / divisor;

        personaje.setVelocidad(vel);
        enKnockback = true;
        personaje.setSobreAlgo(false);
        tiempoDesdeContacto = 0;
    }


    public float getTiempoResbale() { return tiempoResbale; }
    public boolean estaEnKnockback() { return enKnockback; }
}
