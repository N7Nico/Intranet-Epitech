package com.nico_11_riv.intranetepitech.database.setters.marks;

import com.nico_11_riv.intranetepitech.database.Mark;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

public class PMarks {
    public PMarks() {
    }

    public void init(String api) {
        GUser user = new GUser();
        try {
            JSONObject json = new JSONObject(api);
            if (json.has("notes")) {
                JSONArray marks = json.getJSONArray("notes");
                List<Mark> m = Mark.findWithQuery(Mark.class, "SELECT * FROM Mark WHERE login = ?", user.getLogin());
                for (int n = 0; n < m.size(); n++) {
                    Mark mark = m.get(n);
                    mark.setOld(true);
                    mark.save();
                }
                for (int i = 0; i < marks.length(); ++i) {
                    JSONObject tmp = marks.getJSONObject(i);
                    Mark note;
                    m = Mark.findWithQuery(Mark.class, "SELECT * FROM Mark WHERE login = ? AND codeacti = ?", user.getLogin(), !Objects.equals(tmp.getString("codeacti"), "null") ? tmp.getString("codeacti") : "n/a");
                    if (m.size() > 0)
                        note = m.get(0);
                    else
                        note = new Mark(user.getLogin());
                    note.setOld(false);
                    note.setScolyear(!Objects.equals(tmp.getString("scolaryear"), "null") ? tmp.getString("scolaryear") : "n/a");
                    note.setCodemodule(!Objects.equals(tmp.getString("codemodule"), "null") ? tmp.getString("codemodule") : "n/a");
                    note.setTitlemodule(!Objects.equals(tmp.getString("titlemodule"), "null") ? tmp.getString("titlemodule") : "n/a");
                    note.setCodeinstance(!Objects.equals(tmp.getString("codeinstance"), "null") ? tmp.getString("codeinstance") : "n/a");
                    note.setCodeacti(!Objects.equals(tmp.getString("codeacti"), "null") ? tmp.getString("codeacti") : "n/a");
                    note.setTitle(!Objects.equals(tmp.getString("title"), "null") ? tmp.getString("title") : "n/a");
                    note.setDate(!Objects.equals(tmp.getString("date"), "null") ? tmp.getString("date") : "n/a");
                    note.setCorrecteur(!Objects.equals(tmp.getString("correcteur"), "null") ? tmp.getString("correcteur") : "n/a");
                    note.setFinalnote(!Objects.equals(tmp.getString("final_note"), "null") ? tmp.getString("final_note") : "n/a");
                    note.setComment(!Objects.equals(tmp.getString("comment"), "null") ? tmp.getString("comment") : "Aucun commentaire.");
                    note.save();
                }
                m = Mark.findWithQuery(Mark.class, "SELECT * FROM Mark WHERE login = ?", user.getLogin());
                for (int n = 0; n < m.size(); n++) {
                    Mark mark = m.get(n);
                    if (mark.isOld())
                        mark.delete();
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
