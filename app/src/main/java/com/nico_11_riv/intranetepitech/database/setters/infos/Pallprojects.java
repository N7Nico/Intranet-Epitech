package com.nico_11_riv.intranetepitech.database.setters.infos;

import com.nico_11_riv.intranetepitech.database.Allprojects;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jimmy on 10/02/2016.
 */
public class Pallprojects {
    public Pallprojects(String api) {
        GUser u = new GUser();
        try {
            JSONArray jsonArray = new JSONArray(api);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject tmp = jsonArray.getJSONObject(i);
                Allprojects allprojects = new Allprojects(u.getToken());
                allprojects.setCodemodule(tmp.getString("codemodule"));
                allprojects.setProject(tmp.getString("project"));
                allprojects.setEndacti(tmp.getString("end_acti"));
                allprojects.setActititle(tmp.getString("acti_title"));
                allprojects.setTitlemodule(tmp.getString("title_module"));
                allprojects.setBeginacti(tmp.getString("begin_acti"));
                allprojects.setScolaryear(tmp.getString("scolaryear"));
                allprojects.setCodeacti(tmp.getString("codeacti"));
                allprojects.setRegistered(tmp.getString("registered"));
                allprojects.setCodeinstance(tmp.getString("codeinstance"));
                allprojects.save();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
