package com.transaction.transprocessor.service;

import com.transaction.transprocessor.dto.AccountResponse;
import com.transaction.transprocessor.dto.CreateAccountRequest;
import com.transaction.transprocessor.dto.MoneyRequest;

import java.util.UUID;

public interface AccountService {

    AccountResponse createAccount(CreateAccountRequest request);

    AccountResponse getAccountById(String accountId);

    AccountResponse deposit(String accountId, MoneyRequest request);
}
