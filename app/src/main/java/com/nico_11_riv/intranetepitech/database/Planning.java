package com.nico_11_riv.intranetepitech.database;

import com.orm.SugarRecord;

public class Planning extends SugarRecord {
    private String login;
    private String scolaryear;
    private String codemodule;
    private String codeinstance;
    private String codeacti;
    private String codeevent;
    private String semester;
    private String titlemodule;
    private String actititle;
    private String start;
    private String end;
    private String resigtermodule;
    private String registerevent;
    private String regstudent;
    private String allowtoken;
    private String typecode;

    public Planning() {
    }

    public Planning(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getActititle() {
        return actititle;
    }

    public void setActititle(String actititle) {
        this.actititle = actititle;
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

    public String getAllowtoken() {
        return allowtoken;
    }

    public void setAllowtoken(String allowtoken) {
        this.allowtoken = allowtoken;
    }

    public String getTypecode() {
        return typecode;
    }

    public void setTypecode(String typecode) {
        this.typecode = typecode;
    }
}
