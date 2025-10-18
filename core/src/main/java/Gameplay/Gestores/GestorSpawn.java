package Gameplay.Gestores;

import Fisicas.Mapa;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GestorSpawn {

    //Reveer el spawn, porque tiende a espawnear siempre a la izquierda ya que es el primer lugar que revisa
    //randomizar las posibles regiones de spawn para evitar eso.

    private final Mapa mapa;
    private final Random random = new Random();
    private final List<Vector2> puntosValidos = new ArrayList<>();

    private int saltoColumnas = 2;
    private int aireExtraSuperior = 6;
    private float alturaSpawnExtra = 6f;
    private int margenLateral = 20;

    public GestorSpawn(Mapa mapa) {
        this.mapa = mapa;
    }

    public void precalcularPuntosValidos(float anchoPersonaje, float altoPersonaje) {
        puntosValidos.clear();

        int anchoMapa = mapa.getWidth();
        int altoMapa = mapa.getHeight();

        int inicioX = (int) (margenLateral + anchoPersonaje / 2);
        int finX = (int) (anchoMapa - margenLateral - anchoPersonaje / 2);

        for (int x = inicioX; x < finX; x += saltoColumnas) {
            for (int y = altoMapa - 2; y >= altoPersonaje; y--) {
                if (mapa.esSolido(x, y)) {

                    int alturaLibre = calcularAlturaLibre(x, y + 1, (int) (altoPersonaje + aireExtraSuperior));

                    if (alturaLibre >= altoPersonaje) {
                        if (esAreaLibre(x, y + 1, anchoPersonaje, altoPersonaje)) {
                            float ySpawn = y + altoPersonaje * 0.5f + alturaSpawnExtra;
                            puntosValidos.add(new Vector2(x, ySpawn));
                        }
                    }

                    break;
                }
            }
        }
    }

    private int calcularAlturaLibre(int x, int yInicio, int maxAltura) {
        for (int i = 0; i < maxAltura; i++) {
            if (mapa.esSolido(x, yInicio + i) ||
                mapa.esSolido(x - 1, yInicio + i) ||
                mapa.esSolido(x + 1, yInicio + i)) {
                return i;
            }
        }
        return maxAltura;
    }

    private boolean esAreaLibre(int xCentro, int yBase, float ancho, float alto) {
        int mitadAncho = (int)(ancho / 2f);
        int altura = (int)alto;

        for (int dx = -mitadAncho; dx <= mitadAncho; dx++) {
            for (int dy = 0; dy < altura; dy++) {
                if (mapa.esSolido(xCentro + dx, yBase + dy)) return false;
            }
        }
        return true;
    }

    public Vector2 generarSpawnPersonaje(float anchoPersonaje, float altoPersonaje) {
        if (puntosValidos.isEmpty()) precalcularPuntosValidos(anchoPersonaje, altoPersonaje);
        if (puntosValidos.isEmpty()) return null;
        return puntosValidos.get(random.nextInt(puntosValidos.size()));
    }

    public List<Vector2> generarVariosSpawnsPersonajes(int cantidad, float ancho, float alto, float distanciaMinima) {
        if (puntosValidos.isEmpty()) precalcularPuntosValidos(ancho, alto);
        List<Vector2> seleccionados = new ArrayList<>();
        List<Vector2> disponibles = new ArrayList<>(puntosValidos);

        while (!disponibles.isEmpty() && seleccionados.size() < cantidad) {
            Vector2 candidato = disponibles.remove(random.nextInt(disponibles.size()));

            boolean muyCerca = false;
            for (Vector2 existente : seleccionados) {
                if (existente.dst(candidato) < distanciaMinima) {
                    muyCerca = true;
                    break;
                }
            }

            if (!muyCerca) seleccionados.add(candidato);
        }

        return seleccionados;
    }

    public Vector2 generarSpawnPowerUp(float anchoPowerUp) {
        int anchoMapa = mapa.getWidth();
        int altoMapa = mapa.getHeight();

        float alturaSpawnFija = altoMapa - 50f;
        int alturaChequeo = 80;

        for (int intento = 0; intento < 200; intento++) {
            float x = margenLateral + random.nextFloat() * (anchoMapa - 2 * margenLateral - anchoPowerUp);

            boolean libre = true;
            for (int j = 0; j < alturaChequeo; j++) {
                if (mapa.esSolido((int) x, (int) (alturaSpawnFija - j))) {
                    libre = false;
                    break;
                }
            }

            if (libre) {
                return new Vector2(x, alturaSpawnFija);
            }
        }
        return null;
    }



}

