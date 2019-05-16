package pl.revolut.test;

import org.junit.Before;
import org.junit.Test;
import pl.revolut.test.model.Account;

import java.math.BigDecimal;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountServiceImplTest {

    private static final int TEST_ACCOUNT_BALANCE = 10;
    private static final String TEST_LAST_NAME = "testLastName";
    private static final String TEST_FIRST_NAME = "testFirstName";
    private AccountService accountService;

    @Before
    public void setUp() {
        accountService = new AccountServiceImpl();
    }

    @Test
    public void shouldAddNewAccountAndReturnCreatedAccountId() {
        Account newAccount = new Account(TEST_FIRST_NAME, TEST_LAST_NAME, new BigDecimal(TEST_ACCOUNT_BALANCE));

        long newAccountId = accountService.addAccount(newAccount);

        assertThat(newAccountId).isEqualTo(1L);
    }

    @Test
    public void shouldAddNewAccountsAndReturnTwoDifferentIds() {
        Account newAccount = new Account(TEST_FIRST_NAME, TEST_LAST_NAME, new BigDecimal(TEST_ACCOUNT_BALANCE));

        long newAccountId1 = accountService.addAccount(newAccount);
        long newAccountId2 = accountService.addAccount(newAccount);

        assertThat(newAccountId1).isEqualTo(1L);
        assertThat(newAccountId2).isEqualTo(2L);
    }

    @Test
    public void shouldReturnTwoAddedAccounts() {
        Account newAccount1 = new Account(TEST_FIRST_NAME, TEST_LAST_NAME, new BigDecimal(TEST_ACCOUNT_BALANCE));
        newAccount1.setId(1L);
        Account newAccount2 = new Account(TEST_FIRST_NAME, TEST_LAST_NAME, new BigDecimal(TEST_ACCOUNT_BALANCE));
        newAccount2.setId(2L);

        accountService.addAccount(new Account(TEST_FIRST_NAME, TEST_LAST_NAME, new BigDecimal(TEST_ACCOUNT_BALANCE)));
        accountService.addAccount(new Account(TEST_FIRST_NAME, TEST_LAST_NAME, new BigDecimal(TEST_ACCOUNT_BALANCE)));
        Collection<Account> accounts = accountService.getAccounts();

        assertThat(accounts).containsExactly(newAccount1, newAccount2);
    }

    @Test
    public void shouldReturnAccountById() {
        Account expectedAccount = new Account(TEST_FIRST_NAME, TEST_LAST_NAME, new BigDecimal(TEST_ACCOUNT_BALANCE));
        expectedAccount.setId(1L);
        accountService.addAccount(new Account(TEST_FIRST_NAME, TEST_LAST_NAME, new BigDecimal(TEST_ACCOUNT_BALANCE)));

        assertThat(expectedAccount).isEqualTo(accountService.getAccount(1L));
    }

    @Test
    public void shouldReturnTrueBecauseAccountExists() {
        Account newAccount = new Account(TEST_FIRST_NAME, TEST_LAST_NAME, new BigDecimal(TEST_ACCOUNT_BALANCE));
        accountService.addAccount(newAccount);

        boolean accountExist = accountService.isAccountExist(1L);

        assertThat(accountExist).isTrue();
    }

    @Test
    public void shouldReturnFalseBecauseAccountNotExists() {
        Account newAccount = new Account(TEST_FIRST_NAME, TEST_LAST_NAME, new BigDecimal(TEST_ACCOUNT_BALANCE));

        accountService.addAccount(newAccount);

        assertThat(accountService.isAccountExist(2L)).isFalse();
    }

    @Test
    public void shouldReturnFalseBecauseAccountIsDeleted() {
        Account newAccount = new Account(TEST_FIRST_NAME, TEST_LAST_NAME, new BigDecimal(TEST_ACCOUNT_BALANCE));
        long newAccountId = accountService.addAccount(newAccount);

        accountService.deleteAccount(newAccountId);

        assertThat(accountService.isAccountExist(newAccountId)).isFalse();
    }

}
