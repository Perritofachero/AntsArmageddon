package logica;

import com.principal.Jugador;
import java.util.ArrayList;

public class GestorTurno {

    private final float TIEMPO_POR_TURNO = 5f;

    private int turnoActual = 0;
    private float tiempoActual = TIEMPO_POR_TURNO;

    private boolean enTransicion = false;
    private float tiempoTransicion = 0f;
    private final float DURACION_TRANSICION = 3f;

    ArrayList<Jugador> jugadores;

    public GestorTurno( ArrayList<Jugador> jugadores){
        this.jugadores = jugadores;
    }

    public void correrContador(float delta) {

        if (enTransicion) {
            transicionarTurno(delta);
        } else {
            tiempoActual -= delta;
            if (tiempoActual <= 0) {
                iniciarTransicion();
                tiempoActual = 0;
            }
        }
    }

    private void iniciarTransicion() {
        this.enTransicion = true;
        this.tiempoTransicion = 0f;
    }

    private void transicionarTurno(float delta){
        tiempoTransicion += delta;

        if(tiempoTransicion >= DURACION_TRANSICION){
            tiempoTransicion = 0;
            enTransicion = false;
            actualizarTurno();
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
    public float getTiempoActual() { return this.tiempoActual; }

}
