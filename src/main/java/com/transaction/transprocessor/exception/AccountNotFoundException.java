package com.transaction.transprocessor.exception;

import com.transaction.transprocessor.repository.AccountRepository;

public class AccountNotFoundException extends RuntimeException{

    public AccountNotFoundException(String message) {
        super(message);
    }

}
