package com.transaction.transprocessor.exception;

import jakarta.persistence.criteria.CriteriaBuilder;

public class InsufficientFundsException extends RuntimeException{

    public InsufficientFundsException(String message) {
        super(message);
    }
}
