package pl.revolut.test;

import org.junit.Before;
import org.junit.Test;
import pl.revolut.test.model.Account;
import pl.revolut.test.model.Transaction;
import pl.revolut.test.model.TransactionHistory;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionServiceImplTest {

    private static final int TEST_ACCOUNT_BALANCE = 10;
    private static final String TEST_LAST_NAME = "testLastName";
    private static final String TEST_FIRST_NAME = "testFirstName";

    private TransactionService transactionService;
    private AccountService accountService;

    @Before
    public void setUp() {
        accountService = mock(AccountService.class);
        transactionService = new TransactionServiceImpl(accountService);
    }

    @Test
    public void shouldNotThrowExceptionAccountIsValid() {
        Account accountFrom = new Account(TEST_FIRST_NAME, TEST_LAST_NAME, new BigDecimal(TEST_ACCOUNT_BALANCE));
        accountFrom.setId(1L);
        Account accountTo = new Account(TEST_FIRST_NAME, TEST_LAST_NAME, new BigDecimal(TEST_ACCOUNT_BALANCE));
        accountTo.setId(2L);

        when(accountService.getAccount(1L)).thenReturn(accountFrom);
        when(accountService.getAccount(2L)).thenReturn(accountTo);

        transactionService.doTransaction(new Transaction(1L, 2L, new BigDecimal(2)));

        verify(accountService, times(2)).getAccount(1L);
        verify(accountService, times(2)).getAccount(2L);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionBecauseTransactionAmountIsBiggerThanAccountBalance() {
        Account accountFrom = new Account(TEST_FIRST_NAME, TEST_LAST_NAME, new BigDecimal(TEST_ACCOUNT_BALANCE));
        accountFrom.setId(1L);
        Account accountTo = new Account(TEST_FIRST_NAME, TEST_LAST_NAME, new BigDecimal(TEST_ACCOUNT_BALANCE));
        accountTo.setId(2L);

        when(accountService.getAccount(1L)).thenReturn(accountFrom);
        when(accountService.getAccount(2L)).thenReturn(accountTo);

        transactionService.doTransaction(new Transaction(1L, 2L, new BigDecimal(2 * TEST_ACCOUNT_BALANCE)));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrownExceptionBecauseOneAccountDontExist() {
        Account accountFrom = new Account(TEST_FIRST_NAME, TEST_LAST_NAME, new BigDecimal(TEST_ACCOUNT_BALANCE));
        accountFrom.setId(1L);

        when(accountService.getAccount(1L)).thenReturn(accountFrom);

        transactionService.doTransaction(new Transaction(1L, 2L, new BigDecimal(2)));
    }

    @Test
    public void shouldReturnOneTransactionWithSuccess() {
        Account accountFrom = new Account(TEST_FIRST_NAME, TEST_LAST_NAME, new BigDecimal(TEST_ACCOUNT_BALANCE));
        accountFrom.setId(1L);
        Account accountTo = new Account(TEST_FIRST_NAME, TEST_LAST_NAME, new BigDecimal(TEST_ACCOUNT_BALANCE));
        accountTo.setId(2L);
        TransactionHistory transactionHistory = new TransactionHistory(1L, 1L, 2L, new BigDecimal(2));
        transactionHistory.setStatus(TransactionHistory.Status.SUCCESS);
        when(accountService.getAccount(1L)).thenReturn(accountFrom);
        when(accountService.getAccount(2L)).thenReturn(accountTo);

        transactionService.doTransaction(new Transaction(1L, 2L, new BigDecimal(2)));

        assertThat(transactionService.getTransactions()).containsExactly(transactionHistory);
    }
}
