package com.nico_11_riv.intranetepitech.database.setters.user;

import com.nico_11_riv.intranetepitech.database.User;

import java.util.List;

public class GUser {
    private String login;
    private String passwd;
    private String token;
    private String connected;

    public GUser() {
        List<User> users = User.find(User.class, "connected = ?", "true");
        User user = users.get(0);
        this.login = user.getLogin();
        this.passwd = user.getPasswd();
        this.token = user.getToken();
        this.connected = user.getConnected();
    }

    public String getLogin() {
        return login;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getToken() {
        return token;
    }

    public String getConnected() {
        return connected;
    }
}
