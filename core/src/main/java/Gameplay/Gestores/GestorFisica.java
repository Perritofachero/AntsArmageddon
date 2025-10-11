package Gameplay.Gestores;

import Fisicas.Colisionable;
import Fisicas.Fisica;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import entidades.Entidad;
import entidades.personajes.Personaje;
import entidades.proyectiles.Proyectil;

public class GestorFisica {

    private final Fisica fisica;
    private final GestorColisiones gestorColisiones;

    private final Vector2 tmpInicio = new Vector2();
    private final Vector2 tmpFin = new Vector2();
    private final Vector2 tmpImpacto = new Vector2();

    public GestorFisica(Fisica fisica, GestorColisiones gestorColisiones) {
        this.fisica = fisica;
        this.gestorColisiones = gestorColisiones;
    }

    public void aplicarFisica(Entidad entidad, float delta) {
        Rectangle hitbox = entidad.getHitbox();
        Vector2 velocidad = entidad.getVelocidad();

        boolean sobreAlgo = gestorColisiones.verificarSobreAlgo(entidad);
        entidad.setSobreAlgo(sobreAlgo);

        if (!sobreAlgo) fisica.aplicarGravedad(velocidad, delta);

        float nuevaX = hitbox.x + velocidad.x * delta;
        if (gestorColisiones.verificarMovimiento(entidad, nuevaX, hitbox.y)) {
            hitbox.x = nuevaX;
        } else {
            velocidad.x = 0;
        }

        float nuevaY = hitbox.y + velocidad.y * delta;
        if (gestorColisiones.verificarMovimiento(entidad, hitbox.x, nuevaY)) {
            hitbox.y = nuevaY;
        } else {
            if (velocidad.y < 0) {
                hitbox.y = gestorColisiones.buscarYsuelo(hitbox, 5);
                sobreAlgo = true;
            }
            velocidad.y = 0;
        }

        entidad.setVelocidad(velocidad);
        entidad.setPosicion(hitbox.x, hitbox.y);
        entidad.updateHitbox();
        entidad.setSobreAlgo(sobreAlgo);
    }


    public Vector2 moverProyectilConRaycast(Proyectil proyectil, float delta, Personaje ignorar) {

        tmpInicio.set(
            proyectil.getX() + proyectil.getHitbox().getWidth() / 2f,
            proyectil.getY() + proyectil.getHitbox().getHeight() / 2f
        );

        tmpFin.set(
            tmpInicio.x + proyectil.getVelocidadVector().x * delta,
            tmpInicio.y + proyectil.getVelocidadVector().y * delta
        );

        Colisionable impactado = gestorColisiones.verificarTrayectoria(tmpInicio, tmpFin, ignorar, proyectil);
        if (impactado != null) {
            proyectil.setImpacto(true);
            tmpImpacto.set(
                tmpFin.x - proyectil.getHitbox().getWidth() / 2f,
                tmpFin.y - proyectil.getHitbox().getHeight() / 2f
            );
            return tmpImpacto;
        }

        Vector2 impactoMapa = gestorColisiones.trayectoriaColisionaMapa(tmpInicio, tmpFin);
        if (impactoMapa != null) {
            proyectil.setImpacto(true);
            tmpImpacto.set(
                impactoMapa.x - proyectil.getHitbox().getWidth() / 2f,
                impactoMapa.y - proyectil.getHitbox().getHeight() / 2f
            );
            return tmpImpacto;
        }

        proyectil.setImpacto(false);
        tmpImpacto.set(
            proyectil.getX() + proyectil.getVelocidadVector().x * delta,
            proyectil.getY() + proyectil.getVelocidadVector().y * delta
        );
        return tmpImpacto;
    }

    public Fisica getFisica() { return this.fisica; }
}
