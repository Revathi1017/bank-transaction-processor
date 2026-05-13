package com.transaction.transprocessor.service;

import com.transaction.transprocessor.dto.AccountResponse;
import com.transaction.transprocessor.dto.CreateAccountRequest;
import com.transaction.transprocessor.entity.Account;
import com.transaction.transprocessor.exception.AccountNotFoundException;
import com.transaction.transprocessor.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountResponse createAccount(CreateAccountRequest request) {

        Account account = new Account();
        account.setAccountID(UUID.randomUUID());
        account.setBalance(request.getInitialBalance());
        account.setCreatedAt(LocalDateTime.now());

        Account savedAccount = accountRepository.save(account);

        AccountResponse response = new AccountResponse();
        response.setAccountId(savedAccount.getAccountID());
        response.setBalance(savedAccount.getBalance());

        return response;
    }

    public AccountResponse getAccountById(String accountId) {
        Account account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));


        AccountResponse response = new AccountResponse();
        response.setAccountId(account.getAccountID());
        response.setBalance(account.getBalance());

        return response;
    }
}
