package com.signupfacebook.Newlife_project_1.util;

import java.util.Random;

public class GenericUtil {

    public static String gennericId() {
        char startChar = 48;
        char endChar = 57;

        StringBuffer randomString = new StringBuffer(5);
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            int randomIndex = startChar + random.nextInt(endChar - startChar + 1);
            randomString.append((char) randomIndex);
        }
        return randomString.toString();
    }
}
