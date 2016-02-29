package com.nico_11_riv.intranetepitech.database;

import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.orm.SugarRecord;

public class Modules extends SugarRecord {
    private String token;
    private String scolaryear;
    private String semester;
    private String codemodule;
    private String codeinstance;
    private String title;
    private String dateins;
    private String cycle;
    private String grade;
    private String credits;
    private String barrage;

    public Modules() {
        GUser user = new GUser();
        this.token = user.getToken();
    }

    public Modules(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getScolaryear() {
        return scolaryear;
    }

    public void setScolaryear(String scolaryear) {
        this.scolaryear = scolaryear;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCodemodule() {
        return codemodule;
    }

    public void setCodemodule(String codemodule) {
        this.codemodule = codemodule;
    }

    public String getCodeinstance() {
        return codeinstance;
    }

    public void setCodeinstance(String codeinstance) {
        this.codeinstance = codeinstance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateins() {
        return dateins;
    }

    public void setDateins(String dateins) {
        this.dateins = dateins;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getBarrage() {
        return barrage;
    }

    public void setBarrage(String barrage) {
        this.barrage = barrage;
    }
}
