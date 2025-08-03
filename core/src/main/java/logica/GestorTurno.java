package logica;

import com.principal.Jugador;
import java.util.ArrayList;

public class GestorTurno {

    private final int TIEMPO_POR_TURNO = 7;

    private int turnoActual = 0;
    private int tiempoActual = TIEMPO_POR_TURNO;
    private float tiempoAcumulado = 0f;

    ArrayList<Jugador> jugadores;

    public GestorTurno( ArrayList<Jugador> jugadores){
        this.jugadores = jugadores;
    }

    public void correrContador(float delta){
        this.tiempoAcumulado += delta;
        if (this.tiempoAcumulado >= 1f) {
            this.tiempoActual--;
            if (this.tiempoActual <= 0) {
                actualizarTurno();
            }
            this.tiempoAcumulado = 0f;
        }
    }

    private void actualizarTurno(){
        this.turnoActual++;

        if(turnoActual >= jugadores.size()){
            this.turnoActual = 0;
        }

        jugadores.get(turnoActual).avanzarPersonaje();
        this.tiempoActual = TIEMPO_POR_TURNO;
    }

    public Jugador getJugadorActivo() { return jugadores.get(turnoActual); }
    public int getTurnoActual() { return this.turnoActual; }
    public int getTiempoActual() { return this.tiempoActual; }

}
