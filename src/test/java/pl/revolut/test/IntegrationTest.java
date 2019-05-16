package pl.revolut.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.revolut.test.model.Account;
import pl.revolut.test.model.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegrationTest {

    private static final String TEST_FIRST_NAME = "testFirstName";
    private static final String TEST_LAST_NAME = "testLastName";
    private static final int MILLION = 1000000;
    private static final long FROM_ACCOUNT_ID = 1L;
    private static final long TO_ACCOUNT_ID = 2L;
    private static final int THREAD_POOL_SIZE = 200;


    private AccountService accountService;
    private TransactionService transactionService;

    @Before
    public void setUp() {
        accountService = new AccountServiceImpl();
        transactionService = new TransactionServiceImpl(accountService);

        accountService.addAccount(new Account(TEST_FIRST_NAME, TEST_LAST_NAME, new BigDecimal(MILLION)));
        accountService.addAccount(new Account(TEST_FIRST_NAME, TEST_LAST_NAME, new BigDecimal(MILLION)));
    }

    @After
    public void tearDown() {
        accountService.deleteAccount(FROM_ACCOUNT_ID);
        accountService.deleteAccount(TO_ACCOUNT_ID);
    }

    @Test
    public void shouldExecuteMillionTransactionInParallelStream() {
        IntStream.range(0, MILLION).parallel().forEach(
                value -> transactionService.doTransaction(new Transaction(FROM_ACCOUNT_ID, TO_ACCOUNT_ID, new BigDecimal(1)))
        );

        assertThat(accountService.getAccount(FROM_ACCOUNT_ID).getBalance()).isEqualTo(new BigDecimal(0));
        assertThat(accountService.getAccount(TO_ACCOUNT_ID).getBalance()).isEqualTo(new BigDecimal(2 * MILLION));
    }

    @Test
    public void shouldExecuteMillionTransaction() {
        IntStream.range(0, MILLION).forEach(value -> transactionService.doTransaction(
                new Transaction(FROM_ACCOUNT_ID, TO_ACCOUNT_ID, new BigDecimal(1))));

        assertThat(accountService.getAccount(FROM_ACCOUNT_ID).getBalance()).isEqualTo(new BigDecimal(0));
        assertThat(accountService.getAccount(TO_ACCOUNT_ID).getBalance()).isEqualTo(new BigDecimal(2 * MILLION));
    }

    @Test
    public void shouldExecuteMillionTransactionIn200Threads() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        Collection<Future<?>> futures = new ArrayList<>(MILLION);
        for (int t = 0; t < MILLION; t++) {
            futures.add(service.submit(() -> transactionService.doTransaction(
                    new Transaction(FROM_ACCOUNT_ID, TO_ACCOUNT_ID, new BigDecimal(1)))));
        }
        for (Future<?> f : futures) {
            f.get();
        }

        assertThat(accountService.getAccount(FROM_ACCOUNT_ID).getBalance()).isEqualTo(new BigDecimal(0));
        assertThat(accountService.getAccount(TO_ACCOUNT_ID).getBalance()).isEqualTo(new BigDecimal(2 * MILLION));
    }

}
