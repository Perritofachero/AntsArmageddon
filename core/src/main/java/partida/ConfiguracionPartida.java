package partida;

import java.util.ArrayList;
import java.util.List;

// ver si podemos hacer que lo set los haga eventos boton y no pasar esa vaina por parametro

public class ConfiguracionPartida {

    public static final int[] OPCIONES_TIEMPO_TURNO = {15, 25, 30};
    public static final int[] OPCIONES_FRECUENCIA_PU = {1, 2, 3};

    public static final String[] TIPOS_HORMIGAS = {
        "Cuadro_HO_Up",
        "Cuadro_HG_Up",
        "Cuadro_HE_Up"
    };

    private int indiceMapa = 0;
    private int tiempoTurno = OPCIONES_TIEMPO_TURNO[0];
    private int frecuenciaPowerUps = OPCIONES_FRECUENCIA_PU[0];

    private final List<String> equipoJugador1 = new ArrayList<>();
    private final List<String> equipoJugador2 = new ArrayList<>();

    public void setMapa(int indice) {
        this.indiceMapa = indice;
        System.out.println("Mapa seleccionado: " + indice);
    }

    public void setTiempoTurnoPorIndice(int indice) {
        if (indice >= 0 && indice < OPCIONES_TIEMPO_TURNO.length) {
            tiempoTurno = OPCIONES_TIEMPO_TURNO[indice];
            System.out.println("Tiempo por turno: " + tiempoTurno + " segundos");
        }
    }

    public void setFrecuenciaPowerUpsPorIndice(int indice) {
        if (indice >= 0 && indice < OPCIONES_FRECUENCIA_PU.length) {
            frecuenciaPowerUps = OPCIONES_FRECUENCIA_PU[indice];
            System.out.println("Frecuencia de power-ups: cada " + frecuenciaPowerUps + " turnos");
        }
    }

    public void setHormiga(int jugador, int slot, int indiceHormiga) {
        List<String> equipo = (jugador == 1) ? equipoJugador1 : equipoJugador2;

        while (equipo.size() <= slot) equipo.add(null);

        if (indiceHormiga == -1) {
            equipo.set(slot, null);
            System.out.println("Jugador " + jugador + " slot " + slot + " vacío (índice -1)");
            return;
        }

        if (indiceHormiga >= 0 && indiceHormiga < TIPOS_HORMIGAS.length) {
            equipo.set(slot, TIPOS_HORMIGAS[indiceHormiga]);
            System.out.println("Jugador " + jugador + " slot " + slot + " = " + TIPOS_HORMIGAS[indiceHormiga]);
        } else {
            equipo.set(slot, null);
            System.out.println("Jugador " + jugador + " slot " + slot + " inválido → vacío");
        }
    }


    public int getIndiceMapa() { return indiceMapa; }
    public int getTiempoTurno() { return tiempoTurno; }
    public int getFrecuenciaPowerUps() { return frecuenciaPowerUps; }

    public List<String> getEquipoJugador1() { return equipoJugador1; }
    public List<String> getEquipoJugador2() { return equipoJugador2; }
}
