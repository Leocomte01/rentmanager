package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Vehicle {

    private long id;
    private String constructeur;
    private int nbPlaces;

    public Vehicle(long id, String constructeur, int nbPlaces) {
        this.id = id;
        this.constructeur = constructeur;
        this.nbPlaces = nbPlaces;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", constructeur='" + constructeur + '\'' +
                ", nbPlaces=" + nbPlaces +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getConstructeur() {
        return constructeur;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setConstructeur(String constructeur) {
        this.constructeur = constructeur;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }
}
