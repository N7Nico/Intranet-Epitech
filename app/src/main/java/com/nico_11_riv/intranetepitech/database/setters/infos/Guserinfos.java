package com.nico_11_riv.intranetepitech.database.setters.infos;

import com.nico_11_riv.intranetepitech.database.setters.user.GUser;
import com.nico_11_riv.intranetepitech.database.Userinfos;

import java.util.List;

public class Guserinfos {
    private String token;
    private String login;
    private String title;
    private String lastname;
    private String firstname;
    private String email;
    private String picture;
    private String promo;
    private String semester;
    private String location;
    private String course_code;
    private String studentyear;
    private String credits;
    private String gpa;

    public Guserinfos() {
        GUser gUser = new GUser();
        if (gUser.getToken() != null) {
            List<Userinfos> infos = Userinfos.find(Userinfos.class, "token = ?", gUser.getToken());
            Userinfos info = infos.get(0);
            this.token = info.getToken();
            this.login = info.getLogin();
            this.title = info.getTitle();
            this.lastname = info.getLastname();
            this.firstname = info.getFirstname();
            this.email = info.getEmail();
            this.picture = info.getPicture();
            this.promo = info.getPromo();
            this.semester = info.getSemester();
            this.location = info.getLocation();
            this.course_code = info.getCourse_code();
            this.studentyear = info.getStudentyear();
            this.credits = info.getCredits();
            this.gpa = info.getGpa();
        }
    }

    public String getToken() {
        return token;
    }

    public String getLogin() {
        return login;
    }

    public String getTitle() {
        return title;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getEmail() {
        return email;
    }

    public String getPicture() {
        return picture;
    }

    public String getPromo() {
        return promo;
    }

    public String getSemester() {
        return semester;
    }

    public String getLocation() {
        return location;
    }

    public String getCourse_code() {
        return course_code;
    }

    public String getStudentyear() {
        return studentyear;
    }

    public String getCredits() {
        return credits;
    }

    public String getGpa() {
        return gpa;
    }
}
