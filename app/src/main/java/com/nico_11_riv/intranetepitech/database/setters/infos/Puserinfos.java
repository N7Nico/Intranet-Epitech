package com.nico_11_riv.intranetepitech.database.setters.infos;

import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.database.Userinfos;

import org.json.JSONException;
import org.json.JSONObject;

public class Puserinfos {
    public Puserinfos(String api) {
        try {
            GUser u = new GUser();
            JSONObject json = new JSONObject(api);
            if (json.has("login")) {
                Userinfos userinfos = new Userinfos(u.getToken());
                userinfos.setLogin(json.getString("login"));
                userinfos.setTitle(json.getString("title"));
                userinfos.setLastname(json.getString("lastname"));
                userinfos.setFirstname(json.getString("firstname"));
                userinfos.setEmail(u.getLogin() + "@epitech.eu");
                userinfos.setScolaryear(json.getString("scolaryear"));
                userinfos.setPicture(json.getString("picture"));
                userinfos.setPromo(json.getString("promo"));
                userinfos.setSemester(json.getString("semester"));
                userinfos.setLocation(json.getString("location"));
                userinfos.setCourse_code(json.getString("course_code"));
                userinfos.setStudentyear(json.getString("studentyear"));
                userinfos.setCredits(json.getString("credits"));
                userinfos.setGpa(json.getJSONArray("gpa").getJSONObject(0).getString("gpa"));
                userinfos.save();
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
