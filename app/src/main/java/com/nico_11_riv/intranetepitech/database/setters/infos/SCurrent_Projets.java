package com.nico_11_riv.intranetepitech.database.setters.infos;

import com.nico_11_riv.intranetepitech.database.Current_Projets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SCurrent_Projets {
    public SCurrent_Projets(JSONObject json, String token) {

        try {
            JSONArray projets = json.getJSONArray("projets");
            for (int i = 0; i < projets.length(); i++) {
                Current_Projets cp = new Current_Projets(token);
                JSONObject obj = projets.getJSONObject(i);
                cp.setTitle(obj.getString("title"));
                cp.setTitle_link(obj.getString("title_link"));
                cp.setTimeline_start(obj.getString("timeline_start"));
                cp.setTimeline_end(obj.getString("timeline_end"));
                cp.setId_activite(obj.getString("id_activite"));
                cp.setSoutenance_name(obj.getString("soutenance_name"));
                cp.setSoutenance_link(obj.getString("soutenance_link"));
                cp.setSoutenance_salle(obj.getString("soutenance_salle"));
                cp.save();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
