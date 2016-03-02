package com.nico_11_riv.intranetepitech.database.setters.infos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.nico_11_riv.intranetepitech.api.IntrAPI;
import com.nico_11_riv.intranetepitech.database.Allmodules;
import com.nico_11_riv.intranetepitech.database.Projects;
import com.nico_11_riv.intranetepitech.database.Userinfos;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

/**
 * Created by Jimmy on 02/03/2016.
 */

public class Pproject {

    GUser user = new GUser();
    List<Userinfos> info = Userinfos.findWithQuery(Userinfos.class, "SELECT * FROM Userinfos WHERE login = ?", user.getLogin());

    public Pproject(IntrAPI api) {
        String returnapi = "";
        List<Allmodules> allmodules = Allmodules.findWithQuery(Allmodules.class, "SELECT * FROM Allmodules WHERE token = ?", this.user.getToken());
        for (int i = 0; i < allmodules.size(); i++) {
            api.setCookie("PHPSESSID", user.getToken());
            returnapi = api.getactivite(allmodules.get(i).getScolaryear(), allmodules.get(i).getCode(), allmodules.get(i).getCodeinstance());
            JSONObject object = null;
            try {
                object = new JSONObject(returnapi);
                if (object.has("activites") && Objects.equals(object.getString("student_registered"), "1")) {
                    JSONArray array = object.getJSONArray("activites");
                    for (int a = 0; a < array.length(); a++) {
                        try {
                            JSONObject tmp = array.getJSONObject(a);
                            if (Objects.equals(tmp.getString("is_projet"), "true")) {
                                api.setCookie("PHPSESSID", user.getToken());
                                getProject(api.getproject(allmodules.get(i).getScolaryear(), allmodules.get(i).getCode(), allmodules.get(i).getCodeinstance(), tmp.getString("codeacti")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void getProject(String api) {
        JSONObject ori = null;
        try {
            ori = new JSONObject(api);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            Projects project = new Projects(this.user.getToken());
            project.setScolaryear(ori.getString("scolaryear"));
            project.setCodemodule(ori.getString("codemodule"));
            project.setCodeinstance(ori.getString("codeinstance"));
            project.setCodeacti(ori.getString("codeacti"));
            project.setInstancelocation(ori.getString("instance_location"));
            project.setModuletitle(ori.getString("module_title"));
            project.setIdactivite(ori.getString("id_activite"));
            project.setProjecttitle(ori.getString("project_title"));
            project.setTypecode(ori.getString("type_title"));
            project.setTypecode(ori.getString("type_code"));
            project.setRegister(ori.getString("register"));
            project.setNbmin(ori.getString("nb_min"));
            project.setNbmax(ori.getString("nb_max"));
            project.setBegin(ori.getString("begin"));
            project.setEnd(ori.getString("end"));
            project.setEndregister(ori.getString("end_register"));
            project.setDeadline(ori.getString("deadline"));
            project.setTitle(ori.getString("title"));
            project.setDescription(ori.getString("description"));
            project.setClosed(ori.getString("closed"));
            project.setInstanceregistered(ori.getString("instance_registered"));
            project.setUserprojectstatus(ori.getString("user_project_status"));
            //project.setFileurl(ori.getString("fileurl"));
            List<Projects> p = Projects.findWithQuery(Projects.class, "Select * FROM Projects WHERE codeacti = ?", ori.getString("codeacti"));
            if (p.size() == 0) {
                project.save();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
