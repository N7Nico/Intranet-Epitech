package com.nico_11_riv.intranetepitech.database.setters.user;

import com.nico_11_riv.intranetepitech.database.Userinfos;

import org.json.JSONException;
import org.json.JSONObject;

public class PUserInfos {
    public PUserInfos() {
    }

    public void init(String api) {
        try {
            GUser user = new GUser();
            JSONObject tmp = new JSONObject(api);
            if (tmp.has("login")) {
                Userinfos userinfos = new Userinfos(user.getLogin());
                userinfos.setLogin(tmp.getString("login") != "null" ? tmp.getString("login") : "n/a");
                userinfos.setTitle(tmp.getString("title") != "null" ? tmp.getString("title") : "n/a");
                userinfos.setLastname(tmp.getString("lastname") != "null" ? tmp.getString("lastname") : "n/a");
                userinfos.setFirstname(tmp.getString("firstname") != "null" ? tmp.getString("firstname") : "n/a");
                userinfos.setEmail(user.getLogin() + "@epitech.eu");
                userinfos.setScolaryear(tmp.getString("scolaryear") != "null" ? tmp.getString("scolaryear") : "n/a");
                userinfos.setPicture(tmp.getString("picture") != "null" ? tmp.getString("picture") : "n/a");
                userinfos.setPromo(tmp.getString("promo") != "null" ? tmp.getString("promo") : "n/a");
                userinfos.setSemester(tmp.getString("semester") != "null" ? tmp.getString("semester") : "n/a");
                userinfos.setLocation(tmp.getString("location") != "null" ? tmp.getString("location") : "n/a");
                userinfos.setCoursecode(tmp.getString("course_code") != "null" ? tmp.getString("course_code") : "n/a");
                userinfos.setStudentyear(tmp.getString("studentyear") != "null" ? tmp.getString("studentyear") : "n/a");
                userinfos.setCredits(tmp.getString("credits") != "null" ? tmp.getString("credits") : "n/a");
                userinfos.setGpa(tmp.getJSONArray("gpa").getJSONObject(0).getString("gpa") != "null" ? tmp.getJSONArray("gpa").getJSONObject(0).getString("gpa") : "n/a");
                userinfos.save();
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
