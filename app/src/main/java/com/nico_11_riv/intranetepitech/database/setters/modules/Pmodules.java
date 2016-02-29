package com.nico_11_riv.intranetepitech.database.setters.modules;


import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.database.Modules;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Pmodules {
    public Pmodules(String api) {
        GUser user = new GUser();
        try {
            JSONObject obj = new JSONObject(api);
            JSONArray json = obj.getJSONArray("modules");
            for (int i = 0; i < json.length(); i++) {
                JSONObject tmp = json.getJSONObject(i);
                Modules modules = new Modules(user.getToken());
                modules.setScolaryear(tmp.getString("scolaryear"));
                modules.setCodemodule(tmp.getString("codemodule"));
                modules.setCodeinstance(tmp.getString("codeinstance"));
                modules.setTitle(tmp.getString("title"));
                modules.setDateins(tmp.getString("date_ins"));
                modules.setCycle(tmp.getString("cycle"));
                modules.setGrade(tmp.getString("grade"));
                modules.setCredits(tmp.getString("credits"));
                modules.setBarrage(tmp.getString("barrage"));
                modules.save();
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
