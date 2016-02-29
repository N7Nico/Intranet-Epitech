package com.nico_11_riv.intranetepitech.ui.contents;

public class Modules_content {
    private String grade;
    private String modulename;
    private String timeline;
    private String codeModule;

    public Modules_content(String grade, String modulename, String timeline, String codeModule) {
        super();
        this.grade = grade;
        this.modulename = modulename;
        this.timeline = timeline;
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

    public String getTimeline() {
        return timeline;
    }

    public void setTimeline(String timeline) {
        this.timeline = timeline;
    }

    public String getCodeModule() {
        return codeModule;
    }

    public void setCodeModule(String codeModule) {
        this.codeModule = codeModule;
    }
}
