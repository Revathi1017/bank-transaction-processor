package com.transaction.transprocessor.controller;

import com.transaction.transprocessor.dto.AccountResponse;
import com.transaction.transprocessor.dto.CreateAccountRequest;
import com.transaction.transprocessor.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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
}
