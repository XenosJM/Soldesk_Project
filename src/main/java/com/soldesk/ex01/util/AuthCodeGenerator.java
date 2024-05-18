package com.soldesk.ex01.util;

import java.util.Random;

public class AuthCodeGenerator {

    public String generateAuthCode() {
    	
        Random random = new Random();
        StringBuilder authCodeBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            authCodeBuilder.append(random.nextInt(10));
        }
        return authCodeBuilder.toString();
    }
}
