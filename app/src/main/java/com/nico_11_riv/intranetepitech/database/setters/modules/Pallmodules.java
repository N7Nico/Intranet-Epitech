package com.nico_11_riv.intranetepitech.database.setters.modules;

import android.app.Activity;

import com.nico_11_riv.intranetepitech.Notifications;
import com.nico_11_riv.intranetepitech.database.Allmodules;
import com.nico_11_riv.intranetepitech.database.Module;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

/**
 *
 * Created by Jimmy on 10/02/2016.
 *
 */
public class Pallmodules {

    private Activity activity;

    public Pallmodules(Activity activity) {
        this.activity = activity;
    }

    public void init(String api) {
        boolean notif = false;
        GUser user = new GUser();
        try {
            JSONArray arr = new JSONArray(api);
            List<Allmodules> m = Allmodules.findWithQuery(Allmodules.class, "SELECT * FROM Allmodules WHERE login = ?", user.getLogin());
            for (int n = 0; n < m.size(); n++) {
                Allmodules module = m.get(n);
                module.setOld(true);
                module.save();
            }
            for (int i = 0; i < arr.length(); i++) {
                JSONObject tmp = arr.getJSONObject(i);
                Allmodules allmodules;
                m = Allmodules.findWithQuery(Allmodules.class, "SELECT * FROM Allmodules WHERE login = ? AND id = ?", user.getLogin(), !Objects.equals(tmp.getString("id"), "null") ? tmp.getString("id") : "n/a");
                if (m.size() > 0)
                    allmodules = m.get(0);
                else {
                    notif = true;
                    allmodules = new Allmodules(user.getLogin());
                }
                allmodules.setOld(false);
                allmodules.setLogin(user.getLogin());
                List<Module> modules = Module.findWithQuery(Module.class, "SELECT * FROM Module WHERE login = ? AND codemodule = ?", user.getLogin(), !Objects.equals(tmp.getString("code"), "null") ? tmp.getString("code") : "n/a");
                if (modules.size() > 0)
                    allmodules.setGrade(modules.get(0).getGrade());
                else
                    allmodules.setGrade("-");
                allmodules.setSemester(!Objects.equals(tmp.getString("semester"), "null") ? tmp.getString("semester") : "n/a");
                allmodules.setBegin(!Objects.equals(tmp.getString("begin"), "null") ? tmp.getString("begin") : "n/a");
                allmodules.setEnd(!Objects.equals(tmp.getString("end"), "null") ? tmp.getString("end") : "n/a");
                allmodules.setEndregister(!Objects.equals(tmp.getString("end_register"), "null") ? tmp.getString("end_register") : "n/a");
                allmodules.setScolaryear(!Objects.equals(tmp.getString("scolaryear"), "null") ? tmp.getString("scolaryear") : "n/a");
                allmodules.setCode(!Objects.equals(tmp.getString("code"), "null") ? tmp.getString("code") : "n/a");
                allmodules.setCodeinstance(!Objects.equals(tmp.getString("codeinstance"), "null") ? tmp.getString("codeinstance") : "n/a");
                allmodules.setLocationtitle(!Objects.equals(tmp.getString("location_title"), "null") ? tmp.getString("location_title") : "n/a");
                allmodules.setFlags(!Objects.equals(tmp.getString("flags"), "null") ? tmp.getString("flags") : "n/a");
                allmodules.setCredits(!Objects.equals(tmp.getString("credits"), "null") ? tmp.getString("credits") : "n/a");
                allmodules.setStatus(!Objects.equals(tmp.getString("status"), "null") ? tmp.getString("status") : "n/a");
                allmodules.setActivepromo(!Objects.equals(tmp.getString("active_promo"), "null") ? tmp.getString("active_promo") : "n/a");
                allmodules.setOpen(!Objects.equals(tmp.getString("open"), "null") ? tmp.getString("open") : "n/a");
                allmodules.setTitle(!Objects.equals(tmp.getString("title"), "null") ? tmp.getString("title") : "n/a");
                allmodules.save();
            }
            if (notif) {
                Notifications notifications = new Notifications(activity, "Nouveau module ouvert", "", "Un module vient de s'ouvrir!", 0);
                notifications.initNotification();
            }
            m = Allmodules.findWithQuery(Allmodules.class, "SELECT * FROM Allmodules WHERE login = ?", user.getLogin());
            for (int n = 0; n < m.size(); n++) {
                Allmodules module = m.get(n);
                if (module.isOld())
                    module.delete();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}