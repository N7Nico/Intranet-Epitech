package com.nico_11_riv.intranetepitech.ui.contents;

public class Messages_content {
    private String titleMessage;
    private String date;
    private String messageContent;
    private String loginMessage;
    private String picture;

    public Messages_content(String titleMessage, String date, String messageContent, String loginMessage, String picture) {
        this.titleMessage = titleMessage;
        this.date = date;
        this.messageContent = messageContent;
        this.loginMessage = loginMessage;
        this.picture = picture;
    }

    public String getTitleMessage() {
        return titleMessage;
    }

    public void setTitleMessage(String titleMessage) {
        this.titleMessage = titleMessage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getLoginMessage() {
        return loginMessage;
    }

    public void setLoginMessage(String loginMessage) {
        this.loginMessage = loginMessage;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
