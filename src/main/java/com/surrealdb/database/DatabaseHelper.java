package com.surrealdb.database;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class DatabaseHelper {

    private DatabaseHelper() {

    }

    private static final String HEX_CHARS = "0123456789abcdef";

    public static String generateXid(int length) {
        Random random = new SecureRandom();

        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(HEX_CHARS.charAt((b & 0xF0) >> 4))
                    .append(HEX_CHARS.charAt(b & 0x0F));
        }
        return sb.toString();
    }


    public static boolean isResponseArray(Object response) {
        var allowedValues = List.of("[]", "&[]", "*[]");
        var stringResponse = response.toString();
        return allowedValues.contains(stringResponse);
    }
}
