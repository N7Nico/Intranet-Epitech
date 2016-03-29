package com.nico_11_riv.intranetepitech.database.setters.projects;

import android.content.Context;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.nico_11_riv.intranetepitech.api.IntrAPI;
import com.nico_11_riv.intranetepitech.database.Allmodules;
import com.nico_11_riv.intranetepitech.database.Project;
import com.nico_11_riv.intranetepitech.database.setters.modules.PAllModules;
import com.nico_11_riv.intranetepitech.database.setters.modules.PModules;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Objects;

/**
 *
 * Created by Jimmy on 02/03/2016.
 *
 */

public class PProjects {

    private GUser user = new GUser();
    private Context context;
    private List<Project> m;

    public void getProject(String api, IntrAPI intra) {
        try {
            JSONObject tmp = new JSONObject(api);


            Project project;
            m = Project.findWithQuery(Project.class, "SELECT * FROM Project WHERE login = ? AND idactivite = ?", user.getLogin(), !Objects.equals(tmp.getString("id_activite"), "null") ? tmp.getString("id_activite") : "n/a");
            if (m.size() > 0)
                project = m.get(0);
            else
                project = new Project(user.getLogin());
            project.setOld(false);
            project.setScolaryear(!Objects.equals(tmp.getString("scolaryear"), "null") ? tmp.getString("scolaryear") : "n/a");
            project.setCodemodule(!Objects.equals(tmp.getString("codemodule"), "null") ? tmp.getString("codemodule") : "n/a");
            project.setCodeinstance(!Objects.equals(tmp.getString("codeinstance"), "null") ? tmp.getString("codeinstance") : "n/a");
            project.setCodeacti(!Objects.equals(tmp.getString("codeacti"), "null") ? tmp.getString("codeacti") : "n/a");
            project.setInstancelocation(!Objects.equals(tmp.getString("instance_location"), "null") ? tmp.getString("instance_location") : "n/a");
            project.setModuletitle(!Objects.equals(tmp.getString("module_title"), "null") ? tmp.getString("module_title") : "n/a");
            project.setIdactivite(!Objects.equals(tmp.getString("id_activite"), "null") ? tmp.getString("id_activite") : "n/a");
            project.setProjecttitle(!Objects.equals(tmp.getString("project_title"), "null") ? tmp.getString("project_title") : "n/a");
            project.setTypecode(!Objects.equals(tmp.getString("type_title"), "null") ? tmp.getString("type_title") : "n/a");
            project.setTypecode(!Objects.equals(tmp.getString("type_code"), "null") ? tmp.getString("type_code") : "n/a");
            project.setRegister(!Objects.equals(tmp.getString("register"), "null") ? tmp.getString("register") : "n/a");
            project.setNbmin(!Objects.equals(tmp.getString("nb_min"), "null") ? tmp.getString("nb_min") : "n/a");
            project.setNbmax(!Objects.equals(tmp.getString("nb_max"), "null") ? tmp.getString("nb_max") : "n/a");
            project.setBegin(!Objects.equals(tmp.getString("begin"), "null") ? tmp.getString("begin") : "n/a");
            project.setEnd(!Objects.equals(tmp.getString("end"), "null") ? tmp.getString("end") : "n/a");
            project.setEndregister(!Objects.equals(tmp.getString("end_register"), "null") ? tmp.getString("end_register") : "n/a");
            project.setDeadline(!Objects.equals(tmp.getString("deadline"), "null") ? tmp.getString("deadline") : "n/a");
            project.setTitle(!Objects.equals(tmp.getString("title"), "null") ? tmp.getString("title") : "n/a");
            project.setDescription(!Objects.equals(tmp.getString("description"), "null") ? tmp.getString("description") : "n/a");
            project.setClosed(!Objects.equals(tmp.getString("closed"), "null") ? tmp.getString("closed") : "n/a");
            project.setInstanceregistered(!Objects.equals(tmp.getString("instance_registered"), "null") ? tmp.getString("instance_registered") : "n/a");
            project.setUserprojectstatus(!Objects.equals(tmp.getString("user_project_status"), "null") ? tmp.getString("user_project_status") : "n/a");
            project.setFileurl("");
            if (!Objects.equals(!Objects.equals(tmp.getString("type_code"), "null") ? tmp.getString("type_code") : "n/a", "rdv") && URLUtil.isValidUrl(!Objects.equals("https://intra.epitech.eu/module/" + tmp.getString("scolaryear"), "null") ? tmp.getString("scolaryear") : !Objects.equals("n/a" + "/" + tmp.getString("codemodule"), "null") ? tmp.getString("codemodule") : !Objects.equals("n/a" + "/" + tmp.getString("codeinstance"), "null") ? tmp.getString("codeinstance") : !Objects.equals("n/a" + "/" + tmp.getString("codeacti"), "null") ? tmp.getString("codeacti") : "n/a")) {
                intra.setCookie("PHPSESSID", user.getToken());
                String path = "https://www.intra.epitech.eu";
                String in = null;
                try {
                    in = intra.getprojectfile(!Objects.equals(tmp.getString("scolaryear"), "null") ? tmp.getString("scolaryear") : "n/a", !Objects.equals(tmp.getString("codemodule"), "null") ? tmp.getString("codemodule") : "n/a", !Objects.equals(tmp.getString("codeinstance"), "n/a") ? tmp.getString("codeinstance") : "n/a", !Objects.equals(tmp.getString("codeacti"), "null") ? tmp.getString("codeacti") : "n/a");
                } catch (HttpClientErrorException e) {
                    Log.d("Response", e.getResponseBodyAsString());
                }
                try {
                    JSONArray json = new JSONArray(in);
                    tmp = json.getJSONObject(0);
                    if (tmp.has("fullpath")) {
                        path += tmp.getString("fullpath");
                        project.setFileurl(path);
                    }
                } catch (JSONException e) {
                    project.setFileurl("");
                    e.printStackTrace();
                } catch (HttpClientErrorException e) {
                    Log.d("Response", e.getResponseBodyAsString());
                }  catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
            project.save();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void parsemodules(List<Allmodules> allmodules, int i, IntrAPI api) {
        api.setCookie("PHPSESSID", user.getToken());
        String returnapi = api.getactivite(allmodules.get(i).getScolaryear(), allmodules.get(i).getCode(), allmodules.get(i).getCodeinstance());
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
                            getProject(api.getproject(allmodules.get(i).getScolaryear(), allmodules.get(i).getCode(), allmodules.get(i).getCodeinstance(), tmp.getString("codeacti")), api);
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

    public PProjects(Context context) {
        this.context = context;
    }

    public void init(IntrAPI api) {
        String s = null;
        api.setCookie("PHPSESSID", user.getToken());
        try {
            s = api.getmarksandmodules(user.getLogin());
        } catch (HttpClientErrorException e) {
            Log.d("Response", e.getResponseBodyAsString());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        PModules modules = new PModules();
        modules.init(s);
        api.setCookie("PHPSESSID", user.getToken());
        try {
            api.getuserinfo(user.getLogin());
        } catch (HttpClientErrorException e) {
            Log.d("Response", e.getResponseBodyAsString());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        api.setCookie("PHPSESSID", user.getToken());
        try {
            PAllModules mod = new PAllModules();
            mod.init(api.getallmodules());
        } catch (HttpClientErrorException e) {
            Log.d("Response", e.getResponseBodyAsString());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        List<Allmodules> ms = Allmodules.findWithQuery(Allmodules.class, "SELECT * FROM Allmodules WHERE login = ?", this.user.getLogin());
        m = Project.findWithQuery(Project.class, "SELECT * FROM Project WHERE login = ?", user.getLogin());
        for (int n = 0; n < m.size(); n++) {
            Project project = m.get(n);
            project.setOld(true);
            project.save();
        }
        for (int i = 0; i < ms.size(); i++) {
            parsemodules(ms, i, api);
        }
        m = Project.findWithQuery(Project.class, "SELECT * FROM Project WHERE login = ?", user.getLogin());
        for (int n = 0; n < m.size(); n++) {
            Project project = m.get(n);
            if (project.isOld())
                project.delete();
        }
    }
}
