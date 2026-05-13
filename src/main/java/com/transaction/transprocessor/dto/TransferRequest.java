package com.transaction.transprocessor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class TransferRequest {

    @NotBlank
    private String fromAccountId;

    @NotBlank
    private String toAccountId;

    @NotNull
    @Positive
    private BigDecimal amount;

    public @NotBlank String getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(@NotBlank String fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public @NotBlank String getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(@NotBlank String toAccountId) {
        this.toAccountId = toAccountId;
    }

    public @NotNull @Positive BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(@NotNull @Positive BigDecimal amount) {
        this.amount = amount;
    }
}
