package com.nico_11_riv.intranetepitech.database.setters.marks;

import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.database.Marks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SMarks {
    public SMarks (String api) {
        GUser user = new GUser();
        try {
            JSONObject json = new JSONObject(api);
            if (json.has("notes")) {
                JSONArray marks = json.getJSONArray("notes");
                Marks toto = new Marks(user.getToken());
                toto.setTitle(marks.getJSONObject(0).getString("title"));
                toto.save();
                for (int i = 0; i < marks.length(); ++i) {
                    JSONObject tmp = marks.getJSONObject(i);
                    Marks note = new Marks(user.getToken());
                    note.setScolyear(tmp.getString("scolaryear"));
                    note.setCodemodule(tmp.getString("codemodule"));
                    note.setTitlemodule(tmp.getString("titlemodule"));
                    note.setCodeinstance(tmp.getString("codeinstance"));
                    note.setCodeacti(tmp.getString("codeacti"));
                    note.setTitle(tmp.getString("title"));
                    note.setDate(tmp.getString("date"));
                    note.setCorrecteur(tmp.getString("correcteur"));
                    note.setFinal_note(tmp.getString("final_note"));
                    note.setComment(tmp.getString("comment"));
                    note.save();
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
