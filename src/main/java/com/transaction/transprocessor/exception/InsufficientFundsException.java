package com.transaction.transprocessor.exception;

public class InsufficientFundsException extends RuntimeException{

    public InsufficientFundsException(String message) {
        super(message);
    }
}
