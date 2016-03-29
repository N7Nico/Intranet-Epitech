package com.nico_11_riv.intranetepitech.database.setters.trombi;

import com.nico_11_riv.intranetepitech.database.Trombi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Jimmy on 17/03/2016.
 */
public class Ptrombi {

    public String scolaryear;
    public String tek;

    public Ptrombi(String scolaryear, String tek) {
        this.scolaryear = scolaryear;
        this.tek = tek;
    }

    public void load(String api) {
        try {
            JSONObject json = new JSONObject(api);
            if (json.has("items")) {
                JSONArray jsonArray = json.getJSONArray("items");
                for (int i = 0; i< jsonArray.length(); i++) {
                    JSONObject tmp = jsonArray.getJSONObject(i);
                    Trombi trombi = null;
                    List<Trombi> trombiList = Trombi.findWithQuery(Trombi.class, "SELECT * FROM Trombi WHERE login = ?", tmp.getString("login"));
                    if (trombiList.size() > 0) {
                        trombi = trombiList.get(0);
                    } else {
                        trombi = new Trombi();
                    }
                    trombi.setTitle(tmp.getString("title"));
                    trombi.setLogin(tmp.getString("login"));
                    trombi.setNom(tmp.getString("nom"));
                    trombi.setPrenom(tmp.getString("prenom"));
                    trombi.setPicture("https://cdn.local.epitech.eu/userprofil/" + tmp.getString("login") + ".bmp");
                    trombi.setLocation(tmp.getString("location"));
                    trombi.setYears(this.scolaryear);
                    trombi.setTek(this.tek);
                    trombi.save();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
