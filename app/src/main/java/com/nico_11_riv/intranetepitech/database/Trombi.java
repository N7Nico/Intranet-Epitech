package com.nico_11_riv.intranetepitech.database;

import com.orm.SugarRecord;

/**
 * Created by Jimmy on 17/03/2016.
 */
public class Trombi extends SugarRecord {
    private String title;
    private String login;
    private String nom;
    private String prenom;
    private String picture;
    private String location;

    public Trombi() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
