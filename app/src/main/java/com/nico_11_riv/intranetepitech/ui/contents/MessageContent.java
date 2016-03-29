package com.nico_11_riv.intranetepitech.ui.contents;

public class MessageContent {
    private String picture;
    private String date;
    private String titleMessage;
    private String loginMessage;
    private String messageContent;

    public MessageContent(String picture, String date, String titleMessage, String loginMessage, String messageContent) {
        this.picture = picture;
        this.date = date;
        this.titleMessage = titleMessage;
        this.loginMessage = loginMessage;
        this.messageContent = messageContent;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitleMessage() {
        return titleMessage;
    }

    public void setTitleMessage(String titleMessage) {
        this.titleMessage = titleMessage;
    }

    public String getLoginMessage() {
        return loginMessage;
    }

    public void setLoginMessage(String loginMessage) {
        this.loginMessage = loginMessage;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
