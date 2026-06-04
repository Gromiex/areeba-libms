package com.example.libms.borrowing.exceptions;

public class FailedBookStatusUpdateException extends RuntimeException {
    public FailedBookStatusUpdateException(String message) {
        super(message);
    }
}
