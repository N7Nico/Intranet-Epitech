package com.nico_11_riv.intranetepitech.database;

import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.orm.SugarRecord;

public class Marks extends SugarRecord {
    private String token;
    private String scolyear;
    private String codemodule;
    private String titlemodule;
    private String codeinstance;
    private String codeacti;
    private String title;
    private String date;
    private String correcteur;
    private String final_note;
    private String comment;

    public Marks () {
        GUser user = new GUser();
        token = user.getToken();
    }

    public Marks (String token) {
        this.token = token;
    }

    public String getCodemodule() {
        return codemodule;
    }

    public void setCodemodule(String codemodule) {
        this.codemodule = codemodule;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getScolyear() {
        return scolyear;
    }

    public void setScolyear(String scolyear) {
        this.scolyear = scolyear;
    }

    public String getTitlemodule() {
        return titlemodule;
    }

    public void setTitlemodule(String titlemodule) {
        this.titlemodule = titlemodule;
    }

    public String getCodeinstance() {
        return codeinstance;
    }

    public void setCodeinstance(String codeinstance) {
        this.codeinstance = codeinstance;
    }

    public String getCodeacti() {
        return codeacti;
    }

    public void setCodeacti(String codeacti) {
        this.codeacti = codeacti;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCorrecteur() {
        return correcteur;
    }

    public void setCorrecteur(String correcteur) {
        this.correcteur = correcteur;
    }

    public String getFinal_note() {
        return final_note;
    }

    public void setFinal_note(String final_note) {
        this.final_note = final_note;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}