package com.principal;

import entidades.Personaje;

import java.util.ArrayList;
import java.util.List;


public class Jugador {

    private List<Personaje> personajes;
    private int turnoPersonajeActivo = 0;
    private boolean jugadorVivo;

    public Jugador(List<Personaje> personajes){
        this.personajes = new ArrayList<>(personajes);
        this.jugadorVivo = true;
    }


    public void removerPersonaje(Personaje personajeRemovido){
        int indiceRemovido = personajes.indexOf(personajeRemovido);

        if(indiceRemovido == -1) {
            System.out.println("Personaje no existente");

        }else {
            personajes.remove(indiceRemovido);

            if(personajes.isEmpty()){
                this.jugadorVivo = false;
            }else {
                if (turnoPersonajeActivo>= personajes.size()){
                    turnoPersonajeActivo = 0;
                }else if(indiceRemovido < turnoPersonajeActivo) {
                    turnoPersonajeActivo--;
                }
            }
        }
    }

    public void avanzarPersonaje(){

        if(turnoPersonajeActivo < personajes.size()-1){
            turnoPersonajeActivo++;
        }else{
            turnoPersonajeActivo = 0;
        }
    }

    public void agregarPersonaje(Personaje nuevoPersonaje){
        personajes.add(nuevoPersonaje);
    }
    public Personaje getPersonajeActivo() { return personajes.get(turnoPersonajeActivo); }
    public List<Personaje> getPersonajes() { return this.personajes; }
    public Personaje getPersonajeIndice(int indice) { return this.personajes.get(indice); }
    public boolean getJugadorVivo() { return this.jugadorVivo; }
}
