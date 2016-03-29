package com.nico_11_riv.intranetepitech.database;

import com.orm.SugarRecord;

public class Mark extends SugarRecord {
    private String login;
    private boolean old;
    private String scolyear;
    private String codemodule;
    private String titlemodule;
    private String codeinstance;
    private String codeacti;
    private String title;
    private String date;
    private String correcteur;
    private String finalnote;
    private String comment;

    public Mark() {
    }

    public Mark(String login) {
        this.login = login;
        this.old = false;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isOld() {
        return old;
    }

    public void setOld(boolean old) {
        this.old = old;
    }

    public String getScolyear() {
        return scolyear;
    }

    public void setScolyear(String scolyear) {
        this.scolyear = scolyear;
    }

    public String getCodemodule() {
        return codemodule;
    }

    public void setCodemodule(String codemodule) {
        this.codemodule = codemodule;
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

    public String getFinalnote() {
        return finalnote;
    }

    public void setFinalnote(String finalnote) {
        this.finalnote = finalnote;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}