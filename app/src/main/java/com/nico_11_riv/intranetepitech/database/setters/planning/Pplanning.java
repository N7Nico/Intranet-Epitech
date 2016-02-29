package com.nico_11_riv.intranetepitech.database.setters.planning;

/**
 * Created by victor on 19/01/2016.
 */

import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.database.Planning;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Pplanning {
    public Pplanning(String api) {
        GUser user = new GUser();
        try {
            JSONArray planning = new JSONArray(api);
            for (int i = 0; i < planning.length(); ++i) {
                JSONObject tmp = planning.getJSONObject(i);
                if (tmp.has("scolaryear")) {
                    Planning pl = new Planning(user.getToken());
                    pl.setScolaryear(tmp.getString("scolaryear"));
                    pl.setCodemodule(tmp.getString("codemodule"));
                    pl.setCodeinstance(tmp.getString("codeinstance"));
                    pl.setCodeacti(tmp.getString("codeacti"));
                    pl.setCodeevent(tmp.getString("codeevent"));
                    pl.setSemester(tmp.getString("semester"));
                    pl.setTitlemodule(tmp.getString("titlemodule"));
                    pl.setActi_title(tmp.getString("acti_title"));
                    pl.setStart(tmp.getString("start"));
                    pl.setEnd(tmp.getString("end"));
                    pl.setResigtermodule(tmp.getString("module_registered"));
                    pl.setAllow_token(tmp.getString("allow_token"));
                    pl.setRegisterevent(tmp.getString("event_registered"));
                    pl.save();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}