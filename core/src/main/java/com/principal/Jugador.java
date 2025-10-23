package com.principal;

import entidades.personajes.Personaje;
import entradas.ControlesJugador;
import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.Color;

public class Jugador {

    private final List<Personaje> personajes;
    private int indiceActivo;
    private boolean jugadorVivo;
    private ControlesJugador controlesJugador;
    private Color colorJugador = Color.WHITE;

    public Jugador(List<Personaje> personajes) {
        this.personajes = new ArrayList<>(personajes);
        this.indiceActivo = 0;
        this.jugadorVivo = !personajes.isEmpty();
    }

    public void removerPersonaje(Personaje personaje) {
        int index = personajes.indexOf(personaje);
        if (index == -1) return;

        personajes.remove(personaje);

        if (index < indiceActivo) {
            indiceActivo--;
        }

        if (indiceActivo >= personajes.size()) {
            indiceActivo = 0;
        }

        if (personajes.isEmpty()) {
            jugadorVivo = false;
        }
    }

    public void avanzarPersonaje() {
        if (personajes.isEmpty()) return;
        indiceActivo = (indiceActivo + 1) % personajes.size();
    }

    public void agregarPersonaje(Personaje nuevoPersonaje) {
        personajes.add(nuevoPersonaje);
        jugadorVivo = true;
    }

    public Personaje getPersonajeActivo() {
        if (personajes.isEmpty()) return null;
        if (indiceActivo >= personajes.size()) indiceActivo = 0;
        return personajes.get(indiceActivo);
    }

    public void setControlesJugador(ControlesJugador controlesJugador) { this.controlesJugador = controlesJugador; }
    public ControlesJugador getControlesJugador() { return this.controlesJugador; }
    public List<Personaje> getPersonajes() { return personajes; }
    public boolean estaVivo() { return jugadorVivo; }
    public void setColorJugador(Color color) { this.colorJugador = color; }
    public Color getColorJugador() { return colorJugador; }

}
