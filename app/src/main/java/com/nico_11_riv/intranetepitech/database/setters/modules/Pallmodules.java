package com.nico_11_riv.intranetepitech.database.setters.modules;

import com.nico_11_riv.intranetepitech.database.Allmodules;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jimmy on 10/02/2016.
 */
public class Pallmodules {
    public Pallmodules(String api) {
        GUser u = new GUser();
        try {
            JSONArray arr = new JSONArray(api);
            for (int i = 0; i< arr.length(); i++) {
                JSONObject tmp = arr.getJSONObject(i);
                Allmodules allmodules = new Allmodules();
                allmodules.setToken(u.getToken());
                allmodules.setSemester(tmp.getString("semester"));
                allmodules.setBegin(tmp.getString("begin"));
                allmodules.setEnd(tmp.getString("end"));
                allmodules.setEndregister(tmp.getString("end_register"));
                allmodules.setScolaryear(tmp.getString("scolaryear"));
                allmodules.setCode(tmp.getString("code"));
                allmodules.setCodeinstance(tmp.getString("codeinstance"));
                allmodules.setLocationtitle(tmp.getString("location_title"));
                allmodules.setFlags(tmp.getString("flags"));
                allmodules.setCredits(tmp.getString("credits"));
                allmodules.setStatus(tmp.getString("status"));
                allmodules.setActivepromo(tmp.getString("active_promo"));
                allmodules.setOpen(tmp.getString("open"));
                allmodules.setTitle(tmp.getString("title"));
                allmodules.save();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}