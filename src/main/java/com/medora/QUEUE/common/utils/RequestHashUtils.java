package com.medora.QUEUE.common.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public final class RequestHashUtils {

    private RequestHashUtils() {
    }

    public static String sha250(String requestData) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashValue = digest.digest(requestData.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hashValue);
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm is unavailable!");
        }
    }
}
