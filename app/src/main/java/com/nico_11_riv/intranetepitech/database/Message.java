package com.nico_11_riv.intranetepitech.database;

import com.orm.SugarRecord;

public class Message extends SugarRecord {
    private String login;
    private boolean old;
    private String idmessage;
    private String title;
    private String content;
    private String date;
    private String picture;
    private String logincorrector;

    public Message() {
    }

    public Message(String login) {
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

    public String getIdmessage() {
        return idmessage;
    }

    public void setIdmessage(String idmessage) {
        this.idmessage = idmessage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLogincorrector() {
        return logincorrector;
    }

    public void setLogincorrector(String logincorrector) {
        this.logincorrector = logincorrector;
    }
}
