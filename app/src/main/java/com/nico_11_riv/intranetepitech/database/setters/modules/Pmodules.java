package com.nico_11_riv.intranetepitech.database.setters.modules;


import com.nico_11_riv.intranetepitech.database.Module;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

public class Pmodules {
    public Pmodules() {
    }

    public void init(String api) {
        GUser user = new GUser();
        try {
            JSONObject obj = new JSONObject(api);
            JSONArray json = obj.getJSONArray("modules");
            List<Module> m = Module.findWithQuery(Module.class, "SELECT * FROM Module WHERE login = ?", user.getLogin());
            for (int n = 0; n < m.size(); n++) {
                Module module = m.get(n);
                module.setOld(true);
                module.save();
            }
            for (int i = 0; i < json.length(); i++) {
                JSONObject tmp = json.getJSONObject(i);
                Module module;
                m = Module.findWithQuery(Module.class, "SELECT * FROM Module WHERE login = ? AND codemodule = ?", user.getLogin(), !Objects.equals(tmp.getString("codemodule"), "null") ? tmp.getString("codemodule") : "n/a");
                if (m.size() > 0)
                    module = m.get(0);
                else
                    module = new Module(user.getLogin());
                module.setOld(false);
                module.setScolaryear(!Objects.equals(tmp.getString("scolaryear"), "null") ? tmp.getString("scolaryear") : "n/a");
                module.setCodemodule(!Objects.equals(tmp.getString("codemodule"), "null") ? tmp.getString("codemodule") : "n/a");
                module.setCodeinstance(!Objects.equals(tmp.getString("codeinstance"), "null") ? tmp.getString("codeinstance") : "n/a");
                module.setTitle(!Objects.equals(tmp.getString("title"), "null") ? tmp.getString("title") : "n/a");
                module.setDate(!Objects.equals(tmp.getString("date_ins"), "null") ? tmp.getString("date_ins") : "n/a");
                module.setCycle(!Objects.equals(tmp.getString("cycle"), "null") ? tmp.getString("cycle") : "n/a");
                module.setGrade(!Objects.equals(tmp.getString("grade"), "null") ? tmp.getString("grade") : "n/a");
                module.setCredits(!Objects.equals(tmp.getString("credits"), "null") ? tmp.getString("credits") : "n/a");
                module.setBarrage(!Objects.equals(tmp.getString("barrage"), "null") ? tmp.getString("barrage") : "n/a");
                module.save();
            }
            m = Module.findWithQuery(Module.class, "SELECT * FROM Module WHERE login = ?", user.getLogin());
            for (int n = 0; n < m.size(); n++) {
                Module module = m.get(n);
                if (module.isOld())
                    module.delete();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
