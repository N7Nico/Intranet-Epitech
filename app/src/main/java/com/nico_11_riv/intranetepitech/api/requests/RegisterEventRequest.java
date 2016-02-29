package com.nico_11_riv.intranetepitech.api.requests;

import org.springframework.util.LinkedMultiValueMap;

public class RegisterEventRequest extends LinkedMultiValueMap<String, String> {
    public  RegisterEventRequest(String token, String scolaryear, String codemodule, String codeinstance, String codeacti, String codeevent) {
        add("token", token);
        add("scolaryear", scolaryear);
        add("codemodule", codemodule);
        add("codeinstance", codeinstance);
        add("codeacti", codeacti);
        add("codeevent", codeevent);
    }
}
