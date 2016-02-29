package com.nico_11_riv.intranetepitech.database;

import com.orm.SugarRecord;

public class Current_Projets extends SugarRecord {
    private String token;
    private String title;
    private String title_link;
    private String timeline_start;
    private String timeline_end;
    private String id_activite;
    private String soutenance_name;
    private String soutenance_link;
    private String soutenance_salle;
    private String title_module;

    public Current_Projets() {

    }

    public Current_Projets(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_link() {
        return title_link;
    }

    public void setTitle_link(String title_link) {
        this.title_link = title_link;
    }

    public String getTimeline_start() {
        return timeline_start;
    }

    public void setTimeline_start(String timeline_start) {
        this.timeline_start = timeline_start;
    }

    public String getTimeline_end() {
        return timeline_end;
    }

    public void setTimeline_end(String timeline_end) {
        this.timeline_end = timeline_end;
    }

    public String getId_activite() {
        return id_activite;
    }

    public void setId_activite(String id_activite) {
        this.id_activite = id_activite;
    }

    public String getSoutenance_name() {
        return soutenance_name;
    }

    public void setSoutenance_name(String soutenance_name) {
        this.soutenance_name = soutenance_name;
    }

    public String getSoutenance_link() {
        return soutenance_link;
    }

    public void setSoutenance_link(String soutenance_link) {
        this.soutenance_link = soutenance_link;
    }

    public String getSoutenance_salle() {
        return soutenance_salle;
    }

    public void setSoutenance_salle(String soutenance_salle) {
        this.soutenance_salle = soutenance_salle;
    }

    public String getTitle_module() {
        return title_module;
    }

    public void setTitle_module(String title_module) {
        this.title_module = title_module;
    }
}
