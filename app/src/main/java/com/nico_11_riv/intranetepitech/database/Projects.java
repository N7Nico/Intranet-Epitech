package com.nico_11_riv.intranetepitech.database;

import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.orm.SugarRecord;

/**
 * Created by Jimmy on 02/03/2016.
 */
public class Projects extends SugarRecord {
    private String token;
    private String scolaryear;
    private String codemodule;
    private String codeinstance;
    private String codeacti;
    private String instancelocation;
    private String moduletitle;
    private String idactivite;
    private String projecttitle;
    private String typetitle;
    private String typecode;
    private String register;
    private String nbmin;
    private String nbmax;
    private String begin;
    private String end;
    private String endregister;
    private String deadline;
    private String title;
    private String description;
    private String closed;
    private String instanceregistered;
    private String userprojectstatus;
    private String fileurl;

    public Projects() {
        GUser user = new GUser();
        this.token = user.getToken();
    }

    public  Projects(String token) {
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

    public String getInstancelocation() {
        return instancelocation;
    }

    public void setInstancelocation(String instancelocation) {
        this.instancelocation = instancelocation;
    }

    public String getModuletitle() {
        return moduletitle;
    }

    public void setModuletitle(String moduletitle) {
        this.moduletitle = moduletitle;
    }

    public String getIdactivite() {
        return idactivite;
    }

    public void setIdactivite(String idactivite) {
        this.idactivite = idactivite;
    }

    public String getProjecttitle() {
        return projecttitle;
    }

    public void setProjecttitle(String projecttitle) {
        this.projecttitle = projecttitle;
    }

    public String getTypetitle() {
        return typetitle;
    }

    public void setTypetitle(String typetitle) {
        this.typetitle = typetitle;
    }

    public String getTypecode() {
        return typecode;
    }

    public void setTypecode(String typecode) {
        this.typecode = typecode;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public String getNbmin() {
        return nbmin;
    }

    public void setNbmin(String nbmin) {
        this.nbmin = nbmin;
    }

    public String getNbmax() {
        return nbmax;
    }

    public void setNbmax(String nbmax) {
        this.nbmax = nbmax;
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

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClosed() {
        return closed;
    }

    public void setClosed(String closed) {
        this.closed = closed;
    }

    public String getInstanceregistered() {
        return instanceregistered;
    }

    public void setInstanceregistered(String instanceregistered) {
        this.instanceregistered = instanceregistered;
    }

    public String getUserprojectstatus() {
        return userprojectstatus;
    }

    public void setUserprojectstatus(String userprojectstatus) {
        this.userprojectstatus = userprojectstatus;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }
}
