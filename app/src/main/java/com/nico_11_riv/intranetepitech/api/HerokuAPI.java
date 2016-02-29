package com.nico_11_riv.intranetepitech.api;

import com.nico_11_riv.intranetepitech.api.requests.InfosRequest;
import com.nico_11_riv.intranetepitech.api.requests.LoginRequest;
import com.nico_11_riv.intranetepitech.api.requests.RegisterEventRequest;
import com.nico_11_riv.intranetepitech.api.requests.TokenRequest;
import com.nico_11_riv.intranetepitech.api.requests.UnRegisterEventRequest;

import org.androidannotations.annotations.rest.Delete;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;

@Rest(rootUrl = "https://epitech-api.herokuapp.com", converters = {StringHttpMessageConverter.class, FormHttpMessageConverter.class})
public interface HerokuAPI extends RestClientErrorHandling {
    @Post("/login")
    String getToken(LoginRequest lr);

    @Post("/infos")
    String getInfos(InfosRequest ir);

    @Get("/planning?token={token}&start={start}&end={end}")
    String getPlanning(String token, String start, String end);

    @Get("/marks?token={token}")
    String getNotes(String token);

    @Get("/messages?token={token}")
    String getMessages(String token);

    @Get("/modules?token={token}")
    String getModules(String token);

    @Post("/token")
    String validateToken(TokenRequest T);

    @Get("/user?token={token}&user={user}")
    String getTrombi(String token, String user);

    @Post("/event")
    void registerEvent(RegisterEventRequest rer);

    @Delete("/event")
    void unregisterEvent(UnRegisterEventRequest rer);

    @Get("/projects?token={token}")
    String getallprojets(String token);
}
