package com.example.libms.borrowing.exceptions;

public class BookingLimitReachedException extends RuntimeException {
    public BookingLimitReachedException(String message) {
        super(message);
    }
}
