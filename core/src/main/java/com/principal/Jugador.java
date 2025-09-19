package com.principal;

import entidades.personajes.Personaje;
import entradas.ControlesJugador;
import java.util.ArrayList;
import java.util.List;


public class Jugador {

    private final List<Personaje> personajes;
    private int indiceActivo;
    private boolean jugadorVivo;
    private ControlesJugador controlesJugador;

    public Jugador(List<Personaje> personajes) {
        this.personajes = new ArrayList<>(personajes);
        this.indiceActivo = 0;
        this.jugadorVivo = !personajes.isEmpty();
    }

    public void removerPersonaje(Personaje personaje) {
        if (!personajes.remove(personaje)) return;

        if (personajes.isEmpty()) {
            jugadorVivo = false;
            return;
        }

        if (indiceActivo >= personajes.size()) {
            indiceActivo = 0;
        }
    }

    public void avanzarPersonaje() {
        if (personajes.isEmpty()) return;
        indiceActivo = (indiceActivo + 1) % personajes.size();
    }

    public void agregarPersonaje(Personaje nuevoPersonaje) {
        personajes.add(nuevoPersonaje);
        if (!jugadorVivo) jugadorVivo = true;
    }

    public void setControlesJugador(ControlesJugador controlesJugador) { this.controlesJugador = controlesJugador; }
    public ControlesJugador getControlesJugador() { return this.controlesJugador; }
    public Personaje getPersonajeActivo() { return personajes.get(indiceActivo); }
    public List<Personaje> getPersonajes() { return personajes; }
    public boolean estaVivo() { return jugadorVivo; }
}
