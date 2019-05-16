package pl.revolut.test;

import org.junit.Test;
import pl.revolut.test.model.Account;

import java.math.BigDecimal;


public class AccountValidatorTest {

    private static final int TEST_ACCOUNT_BALANCE = 10;
    private static final String TEST_LAST_NAME = "testLastName";
    private static final String TEST_FIRST_NAME = "testFirstName";

    @Test
    public void shouldNotThrowExceptionAccountIsValid() {
        Account newAccount = new Account(TEST_FIRST_NAME, TEST_LAST_NAME, new BigDecimal(TEST_ACCOUNT_BALANCE));

        AccountValidator.validate(newAccount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionBalanceIsNull() {
        Account newAccount = new Account(TEST_FIRST_NAME, TEST_LAST_NAME, new BigDecimal(TEST_ACCOUNT_BALANCE));
        newAccount.setBalance(null);

        AccountValidator.validate(newAccount);
    }


    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionFirstNameIsEmpty() {
        Account newAccount = new Account(TEST_FIRST_NAME, TEST_LAST_NAME, new BigDecimal(TEST_ACCOUNT_BALANCE));
        newAccount.setFirstName("");

        AccountValidator.validate(newAccount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionLastNameIsEmpty() {
        Account newAccount = new Account(TEST_FIRST_NAME, TEST_LAST_NAME, new BigDecimal(TEST_ACCOUNT_BALANCE));
        newAccount.setLastName("");

        AccountValidator.validate(newAccount);
    }
}
