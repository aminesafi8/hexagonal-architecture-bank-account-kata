package com.amine.katabankaccount.application.core.exception;

public class InvalidAmountException extends RuntimeException {

    public InvalidAmountException(final String message) {
        super(message);
    }
}
