package com.transaction.transprocessor.service;

import com.transaction.transprocessor.dto.*;
import com.transaction.transprocessor.entity.Account;
import com.transaction.transprocessor.entity.Transaction;
import com.transaction.transprocessor.exception.AccountNotFoundException;
import com.transaction.transprocessor.exception.InsufficientFundsException;
import com.transaction.transprocessor.exception.InvalidTransferException;
import com.transaction.transprocessor.repository.AccountRepository;
import com.transaction.transprocessor.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Formatter;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    public AccountServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
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

    @Override
    public AccountResponse getAccountById(String accountId) {
        Account account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));


        AccountResponse response = new AccountResponse();
        response.setAccountId(account.getAccountID());
        response.setBalance(account.getBalance());

        return response;
    }

    @Override
    public AccountResponse deposit(String accountId, MoneyRequest request) {
        Account account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        account.setBalance(account.getBalance().add(request.getAmount()));

        Account updatedAccount = accountRepository.save(account);

        recordTransaction(
                updatedAccount.getAccountID(),
                TransactionType.DEPOSIT,
                request.getAmount(),
                updatedAccount.getBalance()
        );

        AccountResponse response = new AccountResponse();
        response.setAccountId(updatedAccount.getAccountID());
        response.setBalance(updatedAccount.getBalance());

        return response;
    }

    @Override
    public AccountResponse withdraw(String accountId, MoneyRequest request) {

        Account account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if(account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientFundsException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(request.getAmount()));

        Account updatedAccount = accountRepository.save(account);

        recordTransaction(
                updatedAccount.getAccountID(),
                TransactionType.WITHDRAWAL,
                request.getAmount(),
                updatedAccount.getBalance()
        );

        AccountResponse response = new AccountResponse();
        response.setAccountId(updatedAccount.getAccountID());
        response.setBalance(updatedAccount.getBalance());

        return response;
    }

    @Override
    @Transactional
    public AccountResponse transfer(TransferRequest request) {
        if(request.getFromAccountId().equals(request.getToAccountId())) {
            throw  new InvalidTransferException("Cannot transfer to same account");
        }

        Account sender = accountRepository.findById(UUID.fromString(request.getFromAccountId()))
                .orElseThrow(() -> new AccountNotFoundException("Sender account not found"));

        Account receiver = accountRepository.findById(UUID.fromString(request.getToAccountId()))
                .orElseThrow(() -> new AccountNotFoundException("Receiver account not found"));

        if (sender.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientFundsException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance().subtract(request.getAmount()));

        receiver.setBalance(receiver.getBalance().add(request.getAmount()));

        accountRepository.save(sender);
        accountRepository.save(receiver);

        recordTransaction(
                sender.getAccountID(),
                TransactionType.TRANSFER_OUT,
                request.getAmount(),
                sender.getBalance()
        );

        recordTransaction(
                receiver.getAccountID(),
                TransactionType.TRANSFER_IN,
                request.getAmount(),
                receiver.getBalance()
        );

        AccountResponse response = new AccountResponse();
        response.setAccountId(sender.getAccountID());
        response.setBalance(sender.getBalance());

        return response;
    }


    private void recordTransaction(
            UUID accountId,
            TransactionType type,
            BigDecimal amount,
            BigDecimal balanceAfterTransaction) {

        Transaction transaction = new Transaction();

        transaction.setTransactionId(UUID.randomUUID());
        transaction.setAccountId(accountId);
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setBalanceAfterTransaction(balanceAfterTransaction);
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);

    }

}
