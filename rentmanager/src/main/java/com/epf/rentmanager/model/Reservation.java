package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Reservation {

    private final long id;

    private long vehicule_id;
    private long client_id;
    private Vehicle voiture;
    private Client client;
    private LocalDate debut;
    private LocalDate fin;

    public Reservation(long id, Vehicle voiture, Client client, LocalDate debut,LocalDate fin) {
        this.id = id;
        this.voiture = voiture;
        this.client = client;
        this.debut = debut;
        this.fin = fin;
    }

    public Reservation(int id, Vehicle voiture, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.voiture = voiture;
        this.debut = debut;
        this.fin = fin;
    }

    public Reservation(int id, Client client, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.client = client;
        this.debut = debut;
        this.fin = fin;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", voiture=" + voiture +
                ", client=" + client +
                ", debut=" + debut +
                ", fin=" + fin +
                '}';
    }

    public long getId() {
        return id;
    }

    public Vehicle getVoiture() {
        return voiture;
    }

    public Client getClient() {
        return client;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public LocalDate getFin() {
        return fin;
    }
}
