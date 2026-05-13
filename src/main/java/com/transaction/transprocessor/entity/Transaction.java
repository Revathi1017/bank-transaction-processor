package com.transaction.transprocessor.entity;

import com.transaction.transprocessor.dto.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Transaction {

    @Id
    private UUID transactionId;

    private UUID accountId;

    private TransactionType type;

    private BigDecimal amount;

    private BigDecimal balanceAfterTransaction;

    private LocalDateTime timestamp;

    private String reference;
}
