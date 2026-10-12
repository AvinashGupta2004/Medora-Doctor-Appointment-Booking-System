package com.medora.QUEUE.common.exceptions;

public class NoQueuesAvailableException extends RuntimeException {
    public NoQueuesAvailableException(String message) {
        super(message);
    }
}
