package com.nico_11_riv.intranetepitech.api.requests;

import org.springframework.util.LinkedMultiValueMap;

public class PlanningRequest extends LinkedMultiValueMap<String, String> {
    public PlanningRequest(String token, String s, String e) {
        add("token", token);
        add("start", s);
        add("end", e);
    }
}
