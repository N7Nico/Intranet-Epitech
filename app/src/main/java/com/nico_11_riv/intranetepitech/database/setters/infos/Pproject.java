package com.nico_11_riv.intranetepitech.database.setters.infos;

import android.util.Log;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.nico_11_riv.intranetepitech.api.IntrAPI;
import com.nico_11_riv.intranetepitech.database.Allmodules;
import com.nico_11_riv.intranetepitech.database.Modules;
import com.nico_11_riv.intranetepitech.database.Projects;
import com.nico_11_riv.intranetepitech.database.Userinfos;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;

/**
 * Created by Jimmy on 02/03/2016.
 */

public class Pproject {

    GUser user = new GUser();
    List<Userinfos> info = Userinfos.findWithQuery(Userinfos.class, "SELECT * FROM Userinfos WHERE login = ?", user.getLogin());

    public void parsemoduleapi(String rslt, IntrAPI api) {
        try {
            JSONArray a = new JSONArray(rslt);
            for (int i = 0; i < a.length(); i++) {
                if (!Objects.equals(a.getJSONObject(i).getString("status"), "notregistered")) {
                    api.setCookie("PHPSESSID", user.getToken());
                    String returnapi = api.getactivite(a.getJSONObject(i).getString("scolaryear"), a.getJSONObject(i).getString("code"), a.getJSONObject(i).getString("codeinstance"));
                    JSONObject object = null;
                    try {
                        object = new JSONObject(returnapi);
                        if (object.has("activites") && Objects.equals(object.getString("student_registered"), "1")) {
                            JSONArray array = object.getJSONArray("activites");
                            for (int o = 0; o < array.length(); o++) {
                                try {
                                    JSONObject tmp = array.getJSONObject(o);
                                    if (Objects.equals(tmp.getString("is_projet"), "true")) {
                                        api.setCookie("PHPSESSID", user.getToken());
                                        getProject(api.getproject(a.getJSONObject(i).getString("scolaryear"), a.getJSONObject(i).getString("code"), a.getJSONObject(i).getString("codeinstance"), tmp.getString("codeacti")), api);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void parsemoduledb(List<Modules> allmodules, int i, IntrAPI api) {
        api.setCookie("PHPSESSID", user.getToken());
        String returnapi = api.getactivite(allmodules.get(i).getScolaryear(), allmodules.get(i).getCodemodule(), allmodules.get(i).getCodeinstance());
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
                            getProject(api.getproject(allmodules.get(i).getScolaryear(), allmodules.get(i).getCodemodule(), allmodules.get(i).getCodeinstance(), tmp.getString("codeacti")), api);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseallmoduledb(List<Allmodules> allmodules, int i, IntrAPI api) {
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

    public Pproject(IntrAPI api) {
        List<Allmodules> allmodules = Allmodules.findWithQuery(Allmodules.class, "SELECT * FROM Allmodules WHERE token = ?", this.user.getToken());
        List<Modules> modules= Modules.findWithQuery(Modules.class, "SELECT * FROM Modules WHERE token = ?", this.user.getToken());
        if (allmodules.size() == 0) {
            if (modules.size() == 0) {
                api.setCookie("PHPSESSID", user.getToken());
                parsemoduleapi(api.getallmodules(), api);
            }
            else if (modules.size() > 0) {
                for (int i = 0; i < modules.size(); i++) {
                    parsemoduledb(modules, i, api);
                }
            }
        }
        else {
            for (int i = 0; i < allmodules.size(); i++) {
                parseallmoduledb(allmodules, i, api);
            }
        }
    }


    public void getProject(String api, IntrAPI intra) {
        JSONObject ori = null;
        try {
            ori = new JSONObject(api);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            Projects project = null;
            List<Projects> p = Projects.findWithQuery(Projects.class, "SELECT * FROM Projects WHERE token = ? AND codeacti = ?", user.getToken(), ori.getString("codeacti"));
            if (p.size() > 0) {
                project = p.get(0);
            } else {
                project = new Projects(this.user.getToken());
            }
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
            project.setFileurl("");
            if (!Objects.equals(ori.getString("type_code"), "rdv") && URLUtil.isValidUrl("https://intra.epitech.eu/module/" + ori.getString("scolaryear") + "/" + ori.getString("codemodule") + "/" + ori.getString("codeinstance") + "/" + ori.getString("codeacti"))) {
                intra.setCookie("PHPSESSID", user.getToken());
                String path = "https://intra.epitech.eu";
                String in = null;
                try {
                    in = intra.getprojectfile(ori.getString("scolaryear"), ori.getString("codemodule"), ori.getString("codeinstance"), ori.getString("codeacti"));
                } catch (HttpClientErrorException e) {
                    Log.d("Response", e.getResponseBodyAsString());
                }
                try {
                    JSONArray json = new JSONArray(in);
                    JSONObject tmp = json.getJSONObject(0);
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
            /*List<Projects> pr = Projects.findWithQuery(Projects.class, "Select * FROM Projects WHERE codeacti = ?", ori.getString("codeacti"));
            if (pr.size() == 0) {
                project.save();
            }*/
            project.save();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
