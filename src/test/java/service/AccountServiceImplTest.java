package service;

import com.transaction.transprocessor.dto.MoneyRequest;
import com.transaction.transprocessor.dto.TransferRequest;
import com.transaction.transprocessor.entity.Account;
import com.transaction.transprocessor.exception.AccountNotFoundException;
import com.transaction.transprocessor.exception.InsufficientFundsException;
import com.transaction.transprocessor.exception.InvalidTransferException;
import com.transaction.transprocessor.repository.AccountRepository;
import com.transaction.transprocessor.repository.TransactionRepository;
import com.transaction.transprocessor.service.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private UUID accountId;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        accountId = UUID.randomUUID();
    }

    @Test
    void shouldDepositAmountSuccessfully() {

        Account account = new Account();
        account.setAccountID(accountId);
        account.setBalance(BigDecimal.valueOf(1000));

        MoneyRequest request = new MoneyRequest();
        request.setAmount(BigDecimal.valueOf(500));

        //If repository.findById() gets called,
        //return this fake account instead of going to DB.
        when(accountRepository.findById(accountId))
                .thenReturn(Optional.of(account));

        //If repository.save() gets called,
        //return this fake account instead of going to DB.
        when(accountRepository.save(account))
                .thenReturn(account);

        var response = accountService.deposit(accountId.toString(), request);

        assertEquals(BigDecimal.valueOf(1500), response.getBalance());
    }

    @Test
    void shouldThrowExceptionWhenAccountNotFound() {

        MoneyRequest request = new MoneyRequest();
        request.setAmount(BigDecimal.valueOf(100));

        when(accountRepository.findById(accountId))
                .thenReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> accountService.deposit(accountId.toString(), request)
        );
    }

    @Test
    void shouldWithdrawAmountSuccessfully() {

        Account account = new Account();
        account.setAccountID(accountId);
        account.setBalance(BigDecimal.valueOf(1000));

        MoneyRequest request = new MoneyRequest();
        request.setAmount(BigDecimal.valueOf(200));

        when(accountRepository.findById(accountId))
                .thenReturn(Optional.of(account));

        when(accountRepository.save(account))
                .thenReturn(account);

        var response = accountService.withdraw(accountId.toString(), request);

        assertEquals(BigDecimal.valueOf(800), response.getBalance());
    }


    @Test
    void shouldThrowExceptionWhenBalanceInsufficient() {

        Account account = new Account();
        account.setAccountID(accountId);
        account.setBalance(BigDecimal.valueOf(100));

        MoneyRequest request = new MoneyRequest();
        request.setAmount(BigDecimal.valueOf(500));

        when(accountRepository.findById(accountId))
                .thenReturn(Optional.of(account));

        assertThrows(
                InsufficientFundsException.class,
                () -> accountService.withdraw(accountId.toString(), request)
        );
    }


    @Test
    void shouldTransferAmountSuccessfully() {

        UUID senderId = UUID.randomUUID();
        UUID receiverId = UUID.randomUUID();

        Account sender = new Account();
        sender.setAccountID(senderId);
        sender.setBalance(BigDecimal.valueOf(1000));

        Account receiver = new Account();
        receiver.setAccountID(receiverId);
        receiver.setBalance(BigDecimal.valueOf(500));

        TransferRequest request = new TransferRequest();
        request.setFromAccountId(senderId.toString());
        request.setToAccountId(receiverId.toString());
        request.setAmount(BigDecimal.valueOf(300));

        when(accountRepository.findById(senderId))
                .thenReturn(Optional.of(sender));

        when(accountRepository.findById(receiverId))
                .thenReturn(Optional.of(receiver));

        when(accountRepository.save(sender))
                .thenReturn(sender);

        when(accountRepository.save(receiver))
                .thenReturn(receiver);

        var response = accountService.transfer(request);

        assertEquals(BigDecimal.valueOf(700), sender.getBalance());

        assertEquals(BigDecimal.valueOf(800), receiver.getBalance());

        assertEquals(senderId, response.getAccountId());
    }

    @Test
    void shouldRejectTransferToSameAccount() {

        UUID sameId = UUID.randomUUID();

        TransferRequest request = new TransferRequest();
        request.setFromAccountId(sameId.toString());
        request.setToAccountId(sameId.toString());
        request.setAmount(BigDecimal.valueOf(100));

        assertThrows(
                InvalidTransferException.class,
                () -> accountService.transfer(request)
        );
    }
}
