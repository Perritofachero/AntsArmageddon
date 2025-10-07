package Gameplay.Gestores;

import Fisicas.Colisionable;
import Fisicas.Fisica;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import entidades.personajes.Personaje;
import entidades.proyectiles.Proyectil;

public class GestorFisica {

    private final Fisica fisica;
    private final GestorColisiones gestorColisiones;

    public GestorFisica(Fisica fisica, GestorColisiones gestorColisiones) {
        this.fisica = fisica;
        this.gestorColisiones = gestorColisiones;
    }

    public void aplicarFisica(Personaje personaje, float delta) {
        Rectangle hitbox = personaje.getHitbox();
        Vector2 velocidad = personaje.getVelocidad().cpy();

        boolean sobreAlgo = gestorColisiones.verificarSobreAlgo(personaje);
        personaje.setSobreAlgo(sobreAlgo);

        velocidad = fisica.aplicarGravedad(velocidad, delta, sobreAlgo);

        float nuevaX = hitbox.x + velocidad.x * delta;
        if (gestorColisiones.verificarMovimiento(personaje, nuevaX, hitbox.y)) {
            hitbox.x = nuevaX;
        } else {
            velocidad.x = 0;
        }

        float nuevaY = hitbox.y + velocidad.y * delta;
        if (gestorColisiones.verificarMovimiento(personaje, hitbox.x, nuevaY)) {
            hitbox.y = nuevaY;
        } else {
            if (velocidad.y < 0) {
                hitbox.y = gestorColisiones.buscarYsuelo(hitbox, 5);
                velocidad.y = 0;
                sobreAlgo = true;
            } else if (velocidad.y > 0) {
                velocidad.y = 0;
            }
        }

        personaje.setVelocidad(velocidad);
        personaje.setPosicion(hitbox.x, hitbox.y);
        personaje.updateHitbox();
        personaje.setSobreAlgo(sobreAlgo);
    }

    public Vector2 moverProyectilConRaycast(Proyectil proyectil, float delta, Personaje ignorar) {
        Vector2 inicio = new Vector2(
            proyectil.getX() + proyectil.getHitbox().getWidth() / 2f,
            proyectil.getY() + proyectil.getHitbox().getHeight() / 2f
        );

        Vector2 fin = new Vector2(
            proyectil.getX() + proyectil.getVelocidadVector().x * delta + proyectil.getHitbox().getWidth() / 2f,
            proyectil.getY() + proyectil.getVelocidadVector().y * delta + proyectil.getHitbox().getHeight() / 2f
        );

        Colisionable impactado = gestorColisiones.verificarTrayectoria(inicio, fin, ignorar, proyectil);
        if (impactado != null) {
            proyectil.setImpacto(true);
            Vector2 posImpacto = new Vector2(
                fin.x - proyectil.getHitbox().getWidth() / 2f,
                fin.y - proyectil.getHitbox().getHeight() / 2f
            );
            return posImpacto;
        }

        Vector2 impactoMapa = gestorColisiones.trayectoriaColisionaMapa(inicio, fin);
        if (impactoMapa != null) {
            proyectil.setImpacto(true);
            Vector2 posImpacto = new Vector2(
                impactoMapa.x - proyectil.getHitbox().getWidth() / 2f,
                impactoMapa.y - proyectil.getHitbox().getHeight() / 2f
            );
            return posImpacto;
        }

        proyectil.setImpacto(false);
        return new Vector2(
            proyectil.getX() + proyectil.getVelocidadVector().x * delta,
            proyectil.getY() + proyectil.getVelocidadVector().y * delta
        );
    }

}
