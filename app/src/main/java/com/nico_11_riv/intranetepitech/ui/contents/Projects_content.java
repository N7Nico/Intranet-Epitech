package com.nico_11_riv.intranetepitech.ui.contents;

public class Projects_content {
    private String projectName;
    private String endDate;
    private String startDate;

    public Projects_content(String projectName, String endDate, String startDate) {
        this.projectName = projectName;
        this.endDate = endDate;
        this.startDate = startDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
