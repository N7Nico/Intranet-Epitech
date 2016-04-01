package com.nico_11_riv.intranetepitech.database.setters.user;

import com.nico_11_riv.intranetepitech.database.Userinfos;

import java.util.List;

public class GUserInfos {
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
    private String scolaryear;
    private String credits;
    private String gpa;


    private String phone;

    public GUserInfos() {
        GUser gUser = new GUser();
        if (gUser.getLogin() != null) {
            List<Userinfos> infos = Userinfos.find(Userinfos.class, "login = ?", gUser.getLogin());
            Userinfos info = infos.get(0);
            this.login = info.getLogin();
            this.title = info.getTitle();
            this.lastname = info.getLastname();
            this.firstname = info.getFirstname();
            this.email = info.getEmail();
            this.picture = info.getPicture();
            this.promo = info.getPromo();
            this.semester = info.getSemester();
            this.location = info.getLocation();
            this.course_code = info.getCoursecode();
            this.studentyear = info.getStudentyear();
            this.scolaryear = info.getScolaryear();
            this.credits = info.getCredits();
            this.gpa = info.getGpa();
            this.phone = info.getPhone();
        }
    }

    public String getPhone() {return phone;}


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

    public String getScolaryear() {
        return scolaryear;
    }

    public String getCredits() {
        return credits;
    }

    public String getGpa() {
        return gpa;
    }
}
