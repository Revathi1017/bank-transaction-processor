package com.transaction.transprocessor.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountResponse {

    private UUID accountId;
    private BigDecimal balance;

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
