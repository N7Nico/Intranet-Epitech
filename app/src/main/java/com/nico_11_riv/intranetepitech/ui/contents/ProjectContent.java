package com.nico_11_riv.intranetepitech.ui.contents;

public class ProjectContent {
    private String title;
    private String date;
    private String moduletitle;

    public ProjectContent(String title, String date, String moduletitle) {
        this.title = title;
        this.date = date;
        this.moduletitle = moduletitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getModuletitle() {
        return moduletitle;
    }

    public void setModuletitle(String moduletitle) {
        this.moduletitle = moduletitle;
    }
}
