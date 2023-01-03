package com.example.myapplication;

public class CurrentUser {

    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private String mail;

    public CurrentUser(int id, String nom, String prenom, String adresse, String mail){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.mail = mail;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getMail() {
        return mail;
    }

}
