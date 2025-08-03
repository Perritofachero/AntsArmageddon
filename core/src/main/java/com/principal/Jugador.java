package com.principal;

import entidades.Personaje;
import java.util.ArrayList;

public class Jugador {

    private ArrayList<Personaje> personajes = new ArrayList<>();
    private boolean jugadorVivo;
    private int turnoPersonajes = 0;

    public Jugador(ArrayList<Personaje> personajes){
        this.personajes = personajes;
        this.jugadorVivo = true;
    }

    public void agregarPersonaje(Personaje nuevoPersonaje){
        personajes.add(nuevoPersonaje);
    }

    public void removerPersonaje(Personaje personajeRemovida){
        personajes.remove(personajeRemovida);

        if (personajes.size() <= 0){
            this.jugadorVivo = false;
        }
    }

    public void avanzarPersonaje(){

        if(turnoPersonajes < personajes.size()-1){
            turnoPersonajes++;
        }else{
            turnoPersonajes = 0;
        }
    }

    public Personaje getPersonajeActivo() { return personajes.get(turnoPersonajes); }

    public ArrayList<Personaje> getPersonajes() { return this.personajes; }

    public Personaje getPersonajeIndice(int indice) { return this.personajes.get(indice); }

    public boolean getJugadorVivo() { return this.jugadorVivo; }

}
