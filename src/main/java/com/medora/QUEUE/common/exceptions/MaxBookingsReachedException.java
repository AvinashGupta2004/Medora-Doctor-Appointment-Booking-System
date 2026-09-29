package com.medora.QUEUE.common.exceptions;

public class MaxBookingsReachedException extends RuntimeException {
    public MaxBookingsReachedException(String message) {
        super(message);
    }
}
