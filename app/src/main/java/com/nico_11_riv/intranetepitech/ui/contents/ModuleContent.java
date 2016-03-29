package com.nico_11_riv.intranetepitech.ui.contents;

public class ModuleContent {
    private String grade;
    private String modulename;
    private String date;
    private String codeModule;

    public ModuleContent(String grade, String modulename, String date, String codeModule) {
        super();
        this.grade = grade;
        this.modulename = modulename;
        this.date = date;
        this.codeModule = codeModule;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getModulename() {
        return modulename;
    }

    public void setModulename(String modulename) {
        this.modulename = modulename;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCodeModule() {
        return codeModule;
    }

    public void setCodeModule(String codeModule) {
        this.codeModule = codeModule;
    }
}
