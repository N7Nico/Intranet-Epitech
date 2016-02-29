package com.nico_11_riv.intranetepitech.api;

import com.nico_11_riv.intranetepitech.api.requests.LoginRequest;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.RequiresCookie;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;

/**
 * Created by Jimmy on 09/02/2016.
 */

@Rest(rootUrl = "https://intra.epitech.eu", converters = {StringHttpMessageConverter.class, FormHttpMessageConverter.class})
public interface IntrAPI {
    @Post("/")
    @RequiresCookie("PHPSESSID")
    String sendToken(LoginRequest lr);

    @Get("/user/{login}/?format=json")
    @RequiresCookie("PHPSESSID")
    String getuserinfo(String login);

    @Get("/user/notification/message?format=json")
    @RequiresCookie("PHPSESSID")
    String getnotifs();

    @Get("/user/{login}/notes?format=json")
    @RequiresCookie("PHPSESSID")
    String getmarks(String login);

    @Get("/course/filter?format=json")
    @RequiresCookie("PHPSESSID")
    String getallmodules();

    @Get("/planning/load?format=json&start={start}&end={end}")
    @RequiresCookie("PHPSESSID")
    String getplanning(String start, String end);

    void setCookie(String name, String value);
    String getCookie(String name);
}
