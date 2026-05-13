package service;

import com.transaction.transprocessor.dto.MoneyRequest;
import com.transaction.transprocessor.entity.Account;
import com.transaction.transprocessor.exception.AccountNotFoundException;
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

        when(accountRepository.findById(accountId))
                .thenReturn(Optional.of(account));

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
}
