package com.transaction.transprocessor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Account {

    @Id
    private UUID accountID;

    private BigDecimal balance;

    private LocalDateTime createdAt;




}
