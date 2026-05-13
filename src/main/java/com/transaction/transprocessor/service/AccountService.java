package com.transaction.transprocessor.service;

import com.transaction.transprocessor.dto.*;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    AccountResponse createAccount(CreateAccountRequest request);

    AccountResponse getAccountById(String accountId);

    AccountResponse deposit(String accountId, MoneyRequest request);

    AccountResponse withdraw(String accontId, MoneyRequest request);

    AccountResponse transfer(TransferRequest request);

    List<TransactionResponse> getTransactions(String accountId);
}
