package com.medora.QUEUE.common.exceptions;

public class BookingNotAllowedException extends RuntimeException {
    public BookingNotAllowedException(String message) {
        super(message);
    }
}
