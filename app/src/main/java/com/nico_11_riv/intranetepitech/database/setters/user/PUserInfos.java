package com.nico_11_riv.intranetepitech.database.setters.user;

import com.nico_11_riv.intranetepitech.database.Userinfos;
import com.orm.query.Condition;
import com.orm.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PUserInfos {
    private String login;

    public PUserInfos(String login) {
        this.login = login;
    }

    public void init(String api) {
        try {
            GUser user = new GUser();
            JSONObject tmp = new JSONObject(api);
            if (tmp.has("login")) {
                Userinfos userinfos;
                List<Userinfos> list = Select.from(Userinfos.class).where(Condition.prop("login").eq(this.login)).list();
                if (list.size() > 0) {
                    userinfos = list.get(0);
                } else {
                    userinfos = new Userinfos(this.login);
                }
                userinfos.setLogin(this.login);
                userinfos.setTitle(tmp.getString("title") != "null" ? tmp.getString("title") : "n/a");
                userinfos.setLastname(tmp.getString("lastname") != "null" ? tmp.getString("lastname") : "n/a");
                userinfos.setFirstname(tmp.getString("firstname") != "null" ? tmp.getString("firstname") : "n/a");
                userinfos.setEmail(this.login + "@epitech.eu");
                userinfos.setScolaryear(tmp.getString("scolaryear") != "null" ? tmp.getString("scolaryear") : "n/a");
                userinfos.setPicture(tmp.getString("picture") != "null" ? tmp.getString("picture") : "n/a");
                userinfos.setPromo(tmp.getString("promo") != "null" ? tmp.getString("promo") : "n/a");
                userinfos.setSemester(tmp.getString("semester") != "null" ? tmp.getString("semester") : "n/a");
                userinfos.setLocation(tmp.getString("location") != "null" ? tmp.getString("location") : "n/a");
                userinfos.setCoursecode(tmp.getString("course_code") != "null" ? tmp.getString("course_code") : "n/a");
                userinfos.setStudentyear(tmp.getString("studentyear") != "null" ? tmp.getString("studentyear") : "n/a");
                userinfos.setCredits(tmp.getString("credits") != "null" ? tmp.getString("credits") : "n/a");
                if (tmp.has("userinfo")) {
                    if (tmp.getJSONObject("userinfo").has("telephone")) {
                        userinfos.setPhone(tmp.getJSONObject("userinfo").getJSONObject("telephone").getString("value"));
                    }
                    else {
                        userinfos.setPhone("");
                    }
                } else {
                        userinfos.setPhone("");
                }
                userinfos.setGpa(tmp.getJSONArray("gpa").getJSONObject(0).getString("gpa") != "null" ? tmp.getJSONArray("gpa").getJSONObject(0).getString("gpa") : "n/a");
                userinfos.save();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
