package com.epf.rentmanager.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Client {

    private final long cle;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate dateNaissance;

    public Client(long cle, String nom, String prenom, String email, LocalDate dateNaissance) {
        this.cle = cle;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateNaissance = dateNaissance;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Client{" +
                "cle=" + cle +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", dateNaissance=" + dateNaissance +
                '}';
    }

    public long getCle() {
        return cle;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public long getAge() {
        long age = ChronoUnit.YEARS.between(this.dateNaissance, LocalDate.now());
        return age;
    }
}
