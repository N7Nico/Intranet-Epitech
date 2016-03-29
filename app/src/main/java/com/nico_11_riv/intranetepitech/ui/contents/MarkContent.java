package com.nico_11_riv.intranetepitech.ui.contents;

public class MarkContent {
    private String mark;
    private String corrector;
    private String event;
    private String module;

    public MarkContent(String mark, String corrector, String event, String module) {
        super();
        this.mark = mark;
        this.corrector = corrector;
        this.event = event;
        this.module = module;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getCorrector() {
        return corrector;
    }

    public void setCorrector(String corrector) {
        this.corrector = corrector;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
