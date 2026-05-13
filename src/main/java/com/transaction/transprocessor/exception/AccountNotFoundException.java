package com.transaction.transprocessor.exception;

public class AccountNotFoundException extends RuntimeException{

    public AccountNotFoundException(String message) {
        super(message);
    }

}
