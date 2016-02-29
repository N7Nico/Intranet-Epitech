package com.nico_11_riv.intranetepitech.database;

import com.orm.SugarRecord;

public class User extends SugarRecord {
    private String login;
    private String passwd;
    private String token;
    private String connected;

    public User() {

    }

    public User(String login, String passwd, String token, String connected) {
        this.login = login;
        this.passwd = passwd;
        this.token = token;
        this.connected = connected;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getConnected() {
        return connected;
    }

    public void setConnected(String connected) {
        this.connected = connected;
    }
}
