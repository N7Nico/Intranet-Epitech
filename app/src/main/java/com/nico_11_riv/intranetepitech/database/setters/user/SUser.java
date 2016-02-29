package com.nico_11_riv.intranetepitech.database.setters.user;

import com.nico_11_riv.intranetepitech.database.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SUser {
    private boolean error;

    public SUser(String login) {
        List<User> users = User.find(User.class, "login = ?", login);
        User user = users.get(0);
        user.setConnected("true");
        user.save();
    }

    public SUser(String login, String passwd, String jstoken) {
        String token;

        error = false;
        if (jstoken != null) {
            try {
                if (jstoken != null) {
                    JSONObject jsobj = new JSONObject(jstoken);
                    if (jsobj.has("token")) {
                        token = jsobj.getString("token");
                        User user = new User(login, passwd, token, "true");
                        user.save();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            error = true;
        }
    }

    public boolean isError() {
        return error;
    }
}
