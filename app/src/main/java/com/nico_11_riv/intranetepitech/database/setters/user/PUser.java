package com.nico_11_riv.intranetepitech.database.setters.user;

import com.nico_11_riv.intranetepitech.database.User;

import java.util.List;

public class PUser {
    public PUser() {
    }

    public void init(String login) {
        List<User> users = User.find(User.class, "login = ?", login);
        User user = users.get(0);
        user.setConnected("true");
        user.save();
    }
}
