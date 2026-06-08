package com.example.libms.borrower.exceptions;

public class BorrowerHasActiveBorrowingsException extends RuntimeException {
    public BorrowerHasActiveBorrowingsException(String message) {
        super(message);
    }
}
