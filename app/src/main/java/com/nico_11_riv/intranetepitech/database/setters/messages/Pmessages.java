package com.nico_11_riv.intranetepitech.database.setters.messages;

import com.nico_11_riv.intranetepitech.database.Message;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

public class PMessages {
    public PMessages() {
    }

    public void init(String api) {
        GUser user = new GUser();
        try {
            JSONArray messages = new JSONArray(api);
            List<Message> m = Message.findWithQuery(Message.class, "SELECT * FROM Message WHERE login = ?", user.getLogin());
            for (int n = 0; n < m.size(); n++) {
                Message message = m.get(n);
                message.setOld(true);
                message.save();
            }
            for (int i = 0; i < messages.length(); ++i) {
                JSONObject tmp = messages.getJSONObject(i);
                Message msg;
                m = Message.findWithQuery(Message.class, "SELECT * FROM Message WHERE login = ? AND idmessage = ?", user.getLogin(), !Objects.equals(tmp.getString("id"), "null") ? tmp.getString("id") : "n/a");
                if (m.size() > 0)
                    msg = m.get(0);
                else
                    msg = new Message(user.getLogin());
                msg.setOld(false);
                msg.setIdmessage(!Objects.equals(tmp.getString("id"), "null") ? tmp.getString("id") : "n/a");
                msg.setTitle(!Objects.equals(tmp.getString("title"), "null") ? tmp.getString("title") : "n/a");
                msg.setContent(!Objects.equals(tmp.getString("content"), "null") ? tmp.getString("content") : "n/a");
                msg.setDate(!Objects.equals(tmp.getString("date"), "null") ? tmp.getString("date") : "n/a");
                msg.setLogincorrector(!Objects.equals(tmp.getJSONObject("user").getString("title"), "null") ? tmp.getJSONObject("user").getString("title") : "n/a");
                msg.setPicture(!Objects.equals(tmp.getJSONObject("user").getString("picture"), "null") ? tmp.getJSONObject("user").getString("picture") : "n/a");
                msg.save();
            }
            m = Message.findWithQuery(Message.class, "SELECT * FROM Message WHERE login = ?", user.getLogin());
            for (int n = 0; n < m.size(); n++) {
                Message message = m.get(n);
                if (message.isOld())
                    message.delete();
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
