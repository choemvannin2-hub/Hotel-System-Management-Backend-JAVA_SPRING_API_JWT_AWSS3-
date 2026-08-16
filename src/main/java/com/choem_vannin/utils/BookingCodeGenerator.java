package com.choem_vannin.utils;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookingCodeGenerator {

    private static final String ALPHA_NUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateCode() {
        // Optional prefix with date: e.g. "BK-20260814-"
        String datePrefix = "BK-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-";

        // Generate 6 random alphanumeric characters
        StringBuilder randomPart = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int index = RANDOM.nextInt(ALPHA_NUMERIC.length());
            randomPart.append(ALPHA_NUMERIC.charAt(index));
        }

        return datePrefix + randomPart.toString();
    }
}
