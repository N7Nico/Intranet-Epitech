package com.nico_11_riv.intranetepitech.ui.contents;

public class Schedule_content {
    private String semester;
    private String event;
    private String startdate;
    private String enddate;
    private String scolaryear;
    private String eventreg;
    private String codemodule;
    private String codeinstance;
    private String codeacti;
    private String codeevent;
    private String allow_token;

    public Schedule_content(String semseter, String event, String startdate, String enddate, String scolaryear, String eventreg, String codemodule, String codeinstance, String codeacti, String codeevent, String allow_token) {
        this.semester = semseter;
        this.event = event;
        this.startdate = startdate;
        this.enddate = enddate;
        this.scolaryear = scolaryear;
        this.eventreg = eventreg;
        this.codemodule = codemodule;
        this.codeinstance = codeinstance;
        this.codeacti = codeacti;
        this.codeevent = codeevent;
        this.allow_token = allow_token;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
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

    public String getAllow_token() {
        return allow_token;
    }

    public void setAllow_token(String allow_token) {
        this.allow_token = allow_token;
    }

    public String getScolaryear() {
        return scolaryear;
    }

    public void setScolaryear(String scolaryear) {
        this.scolaryear = scolaryear;
    }

    public String getEventreg() {
        return eventreg;
    }

    public void setEventreg(String eventreg) {
        this.eventreg = eventreg;
    }
}
