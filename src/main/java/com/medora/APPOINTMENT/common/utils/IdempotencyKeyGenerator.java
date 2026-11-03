package com.medora.APPOINTMENT.common.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class IdempotencyKeyGenerator {

    private IdempotencyKeyGenerator() {

    }

    public static String generate(Long queueId, Long patientId) {
        if (queueId == null || patientId == null) {
            throw new IllegalArgumentException("Queue ID and Patient ID cannot be null");
        }

        String input = "queue:" + queueId + "|patient:" + patientId;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 Algorithm is not available!");
        }
    }
}
