package com.nico_11_riv.intranetepitech.api.requests;

import org.springframework.util.LinkedMultiValueMap;

public class LoginRequest extends LinkedMultiValueMap<String, String> {
    public LoginRequest(String login, String passwd) {
        add("login", login);
        add("password", passwd);
    }
}
