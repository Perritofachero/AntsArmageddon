package Gameplay.Gestores;

import Fisicas.Mapa;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import Fisicas.Colisionable;
import com.badlogic.gdx.math.Vector2;
import entidades.personajes.Personaje;
import entidades.proyectiles.Proyectil;
import java.util.List;
import java.util.ArrayList;

public class GestorColisiones {

    private final List<Colisionable> colisionables;
    private final Mapa mapa;
    private final Rectangle tempRect = new Rectangle();

    public GestorColisiones(Mapa mapa) {
        this.mapa = mapa;
        this.colisionables = new ArrayList<>();
    }

    public boolean verificarSobreAlgo(Colisionable objeto) {
        Rectangle hitbox = objeto.getHitbox();

        if (haySueloDebajo(hitbox, 1)) return true;

        for (Colisionable otro : colisionables) {
            if (otro == objeto) continue;
            Rectangle r = otro.getHitbox();
            boolean tocaVertical = hitbox.y - 1 <= r.y + r.height && hitbox.y > r.y + r.height;
            boolean dentroX = hitbox.x + hitbox.width > r.x && hitbox.x < r.x + r.width;
            if (tocaVertical && dentroX) return true;
        }

        return false;
    }

    public boolean colisionaConAlgo(Colisionable ejecutor, Rectangle area) {
        for (Colisionable otro : colisionables) {
            if (otro == ejecutor) continue;
            if (area.overlaps(otro.getHitbox())) return true;
        }
        return mapa != null && mapa.colisiona(area);
    }

    public boolean verificarMovimiento(Colisionable objeto, float nuevaX, float nuevaY) {
        tempRect.set(objeto.getHitbox());
        tempRect.setPosition(nuevaX, nuevaY);
        return !colisionaConAlgo(objeto, tempRect);
    }

    public boolean verificarMovimiento(Colisionable objeto, float nuevaX, float nuevaY, Personaje ignorar) {
        tempRect.set(objeto.getHitbox());
        tempRect.setPosition(nuevaX, nuevaY);

        for (Colisionable c : colisionables) {
            if (c == objeto) continue;
            if (c == ignorar) continue;
            if (!c.getActivo()) continue;

            if (tempRect.overlaps(c.getHitbox())) {
                return false;
            }
        }

        if (mapa != null && mapa.colisiona(tempRect)) {
            return false;
        }

        return true;
    }

    public float buscarYsuelo(Rectangle hitbox, int maxDescenso) {
        for (int i = 0; i <= maxDescenso; i++) {
            if (mapa.colisiona(new Rectangle(hitbox.x, hitbox.y - i, hitbox.width, 1))) {
                return hitbox.y - i + 1;
            }
        }
        return hitbox.y;
    }

    public boolean haySueloDebajo(Rectangle hitbox, int maxDescenso) {
        for (int i = 1; i <= maxDescenso; i++) {
            if (mapa.colisiona(new Rectangle(hitbox.x, hitbox.y - i, hitbox.width, 1))) return true;
        }
        return false;
    }

    public boolean hayTechoArriba(Rectangle hitbox, int maxAltura) {
        for (int i = 1; i <= maxAltura; i++) {
            if (mapa.colisiona(new Rectangle(hitbox.x, hitbox.y + hitbox.height + i, hitbox.width, 1))) return true;
        }
        return false;
    }

    public Vector2 trayectoriaColisionaMapa(Vector2 inicio, Vector2 fin) {
        if (mapa == null) return null;

        float dx = fin.x - inicio.x;
        float dy = fin.y - inicio.y;
        int pasos = (int) Math.ceil(Math.sqrt(dx * dx + dy * dy));
        if (pasos <= 0) return null;

        float pasoX = dx / pasos;
        float pasoY = dy / pasos;

        for (int i = 0; i <= pasos; i++) {
            int px = MathUtils.floor(inicio.x + pasoX * i);
            int py = MathUtils.floor(inicio.y + pasoY * i);
            if (mapa.esSolido(px, py)) return new Vector2(px, py);
        }
        return null;
    }

    public List<Colisionable> getColisionablesRadio(float x, float y, float radio) {
        List<Colisionable> resultado = new ArrayList<>();
        float radio2 = radio * radio;
        for (Colisionable c : colisionables) {
            Rectangle r = c.getHitbox();
            float cx = r.x + r.width / 2f;
            float cy = r.y + r.height / 2f;
            float dx = cx - x;
            float dy = cy - y;
            if (dx * dx + dy * dy <= radio2) resultado.add(c);
        }
        return resultado;
    }

    public Colisionable verificarTrayectoria(Vector2 inicio, Vector2 fin, Colisionable ignorar, Colisionable self) {
        Colisionable colisionado = null;
        float distanciaMin = Float.MAX_VALUE;

        for (Colisionable otro : colisionables) {
            if (otro == ignorar || otro == self) continue;
            Rectangle rect = otro.getHitbox();
            if (Intersector.intersectSegmentRectangle(inicio, fin, rect)) {
                float dist = inicio.dst2(rect.x + rect.width / 2f, rect.y + rect.height / 2f);
                if (dist < distanciaMin) {
                    distanciaMin = dist;
                    colisionado = otro;
                }
            }
        }

        return colisionado;
    }


    public void agregarObjeto(Colisionable objeto) {
        if (!colisionables.contains(objeto)) colisionables.add(objeto);
    }

    public void removerObjeto(Colisionable objeto) {
        colisionables.remove(objeto);
    }

    public List<Colisionable> getColisionables() {
        return colisionables;
    }

    public Mapa getMapa() {
        return mapa;
    }

    public boolean impactoVertical(Proyectil proyectil) {
        Rectangle hitbox = proyectil.getHitbox();
        Vector2 vel = proyectil.getVelocidadVector();
        if (vel.y < 0 && haySueloDebajo(hitbox, 1)) return true;
        if (vel.y > 0 && hayTechoArriba(hitbox, 1)) return true;
        return false;
    }

    public boolean impactoHorizontal(Proyectil proyectil) {
        Rectangle hitbox = proyectil.getHitbox();
        Vector2 vel = proyectil.getVelocidadVector();
        Rectangle derecha = new Rectangle(hitbox.x + hitbox.width + vel.x * 0.016f, hitbox.y, 1, hitbox.height);
        if (colisionaConAlgo(proyectil, derecha)) return true;
        Rectangle izquierda = new Rectangle(hitbox.x + vel.x * 0.016f - 1, hitbox.y, 1, hitbox.height);
        if (colisionaConAlgo(proyectil, izquierda)) return true;
        return false;
    }
}


