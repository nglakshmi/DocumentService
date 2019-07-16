package com.lg100m.storageservice.service;

import java.security.SecureRandom;

public class StorageUtils {

    public static String getUUID(int uuidLen) {
        int i = 0;
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        SecureRandom rand = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        while (i < uuidLen) {
            int index = rand.nextInt(allowedChars.length() - 1);
            sb.append(allowedChars.charAt(index));
            i++;
        }
        return sb.toString();
    }

}
