package com.nico_11_riv.intranetepitech.api;

import com.nico_11_riv.intranetepitech.api.requests.LoginRequest;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.RequiresCookie;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;

/**
 * Created by Jimmy on 09/02/2016.
 */

@Rest(rootUrl = "https://intra.epitech.eu", converters = {StringHttpMessageConverter.class, FormHttpMessageConverter.class})
public interface IntrAPI extends RestClientErrorHandling {
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

    @Post("/module/{scolaryear}/{codemodule}/{codeinstance}/{codeacti}/{codeevent}/register?format=json")
    @RequiresCookie("PHPSESSID")
    String registerevent(String scolaryear, String codemodule, String codeinstance, String codeacti, String codeevent);

    @Post("/module/{scolaryear}/{codemodule}/{codeinstance}/{codeacti}/{codeevent}/unregister?format=json")
    @RequiresCookie("PHPSESSID")
    String unregisterevent(String scolaryear, String codemodule, String codeinstance, String codeacti, String codeevent);

    @Post("/module/{scolaryear}/{codemodule}/{codeinstance}/register?format=json")
    @RequiresCookie("PHPSESSID")
    String registermodule(String scolaryear, String codemodule, String codeinstance);

    @Post("/module/{scolaryear}/{codemodule}/{codeinstance}/unregister?format=json")
    @RequiresCookie("PHPSESSID")
    String unregistermodule(String scolaryear, String codemodule, String codeinstance);

    @Post("/module/{scolaryear}/{codemodule}/{codeinstance}/{codeacti}/project/register?format=json")
    @RequiresCookie("PHPSESSID")
    String registerproject(String scolaryear, String codemodule, String codeinstance, String codeacti);

    @Post("/module/{scolaryear}/{codemodule}/{codeinstance}/{codeacti}/project/unregister?format=json")
    @RequiresCookie("PHPSESSID")
    String unregisterproject(String scolaryear, String codemodule, String codeinstance, String codeacti);

    // Get Project row
    @Post("/module/{scolaryear}/{codemodule}/{codeinstance}/?format=json")
    @RequiresCookie("PHPSESSID")
    String getactivite(String scolaryear, String codemodule, String codeinstance);

    @Post("/module/{scolaryear}/{codemodule}/{codeinstance}/{codeacti}/project/?format=json")
    @RequiresCookie("PHPSESSID")
    String getproject(String scolaryear, String codemodule, String codeinstance, String codeacti);

    @Post("/module/{scolaryear}/{codemodule}/{codeinstance}/{codeacti}/project/file/?format=json")
    @RequiresCookie("PHPSESSID")
    String getprojectfile(String scolaryear, String codemodule, String codeinstance, String codeacti);

    void setCookie(String name, String value);
    String getCookie(String name);
}
