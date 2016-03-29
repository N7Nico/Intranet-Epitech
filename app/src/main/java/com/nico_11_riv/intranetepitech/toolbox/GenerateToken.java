package com.nico_11_riv.intranetepitech.toolbox;

import java.util.Random;

/**
 * Created by nicol on 27/03/2016.
 */
public class GenerateToken {

    public GenerateToken() {
    }

    public String gen() {
        char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 26; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return (sb.toString());
    }
}
