package com.nico_11_riv.intranetepitech.database;

import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.orm.SugarRecord;

public class Allmodules extends SugarRecord {
    private String token;
    private String semester;
    private String begin;
    private String end;
    private String endregister;
    private String scolaryear;
    private String code;
    private String codeinstance;
    private String locationtitle;
    private String flags;
    private String credits;
    private String status;
    private String activepromo;
    private String open;
    private String title;

    public Allmodules() {
        GUser user = new GUser();
        this.token = user.getToken();
    }

    public Allmodules(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getEndregister() {
        return endregister;
    }

    public void setEndregister(String endregister) {
        this.endregister = endregister;
    }

    public String getScolaryear() {
        return scolaryear;
    }

    public void setScolaryear(String scolaryear) {
        this.scolaryear = scolaryear;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeinstance() {
        return codeinstance;
    }

    public void setCodeinstance(String codeinstance) {
        this.codeinstance = codeinstance;
    }

    public String getLocationtitle() {
        return locationtitle;
    }

    public void setLocationtitle(String locationtitle) {
        this.locationtitle = locationtitle;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActivepromo() {
        return activepromo;
    }

    public void setActivepromo(String activepromo) {
        this.activepromo = activepromo;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
