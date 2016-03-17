package com.nico_11_riv.intranetepitech.ui.contents;

/**
 * Created by victor on 17/03/2016.
 */
public class Trombi_content {
    private String login;
    private String name;
    private String picture;

    public Trombi_content (String login, String name, String picture){

        this.login = login;
        this.name = name;
        this.picture = picture;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
