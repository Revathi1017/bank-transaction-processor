package com.transaction.transprocessor.controller;

import com.transaction.transprocessor.dto.*;
import com.transaction.transprocessor.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public AccountResponse createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return accountService.createAccount(request);
    }

    @GetMapping("/{accountId}")
    public AccountResponse getAccount(@PathVariable String accountId) {
        return accountService.getAccountById(accountId);
    }

    @PostMapping("/{accountId}/deposit")
    public AccountResponse deposit(@PathVariable String accountId, @Valid @RequestBody MoneyRequest request) {
        return accountService.deposit(accountId, request);
    }

    @PostMapping("/{accountId}/withdraw")
    public AccountResponse withdraw(@PathVariable String accountId, @Valid @RequestBody MoneyRequest request) {
        return accountService.withdraw(accountId, request);
    }

    @PostMapping("/transfer")
    public AccountResponse transfer(@Valid @RequestBody TransferRequest request) {
        return accountService.transfer(request);
    }

    @GetMapping("/{accountId}/transactions")
    public List<TransactionResponse> getTransactions(@PathVariable String accountId) {
        return accountService.getTransactions(accountId);
    }
}
