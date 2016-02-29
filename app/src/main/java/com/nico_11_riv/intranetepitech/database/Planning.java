package com.nico_11_riv.intranetepitech.database;

import com.orm.SugarRecord;
import com.nico_11_riv.intranetepitech.database.setters.user.GUser;

public class Planning extends SugarRecord {
    private String token;
    private String scolaryear;
    private String codemodule;
    private String codeinstance;
    private String codeacti;
    private String codeevent;
    private String semester;
    private String titlemodule;
    private String acti_title;
    private String start;
    private String end;
    private String resigtermodule;
    private String registerevent;
    private String regstudent;
    private String allow_token;

    public Planning() {
        GUser gUser = new GUser();
        this.token = gUser.getToken();
    }

    public Planning(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getScolaryear() {
        return scolaryear;
    }

    public void setScolaryear(String scolaryear) {
        this.scolaryear = scolaryear;
    }

    public String getCodemodule() {
        return codemodule;
    }

    public void setCodemodule(String codemodule) {
        this.codemodule = codemodule;
    }

    public String getCodeinstance() {
        return codeinstance;
    }

    public void setCodeinstance(String codeinstance) {
        this.codeinstance = codeinstance;
    }

    public String getCodeacti() {
        return codeacti;
    }

    public void setCodeacti(String codeacti) {
        this.codeacti = codeacti;
    }

    public String getCodeevent() {
        return codeevent;
    }

    public void setCodeevent(String codeevent) {
        this.codeevent = codeevent;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getTitlemodule() {
        return titlemodule;
    }

    public void setTitlemodule(String titlemodule) {
        this.titlemodule = titlemodule;
    }

    public String getActi_title() {
        return acti_title;
    }

    public void setActi_title(String acti_title) {
        this.acti_title = acti_title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getResigtermodule() {
        return resigtermodule;
    }

    public void setResigtermodule(String resigtermodule) {
        this.resigtermodule = resigtermodule;
    }

    public String getRegisterevent() {
        return registerevent;
    }

    public void setRegisterevent(String registerevent) {
        this.registerevent = registerevent;
    }

    public String getRegstudent() {
        return regstudent;
    }

    public void setRegstudent(String regstudent) {
        this.regstudent = regstudent;
    }

    public String getAllow_token() {
        return allow_token;
    }

    public void setAllow_token(String allow_token) {
        this.allow_token = allow_token;
    }
}
