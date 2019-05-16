package pl.revolut.test;

import org.junit.Test;
import pl.revolut.test.model.Transaction;

import java.math.BigDecimal;

public class TransactionValidatorTest {

    @Test
    public void shouldNotThrowExceptionTransactionIsValid() {
        Transaction transaction = new Transaction(1L, 2L, new BigDecimal(2));
        TransactionValidator.validate(transaction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIncorrectFromAccountId() {
        Transaction transaction = new Transaction(-1L, 2L, new BigDecimal(2));
        TransactionValidator.validate(transaction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionFromAccountIdIsZero() {
        Transaction transaction = new Transaction(0L, 2L, new BigDecimal(2));
        TransactionValidator.validate(transaction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionToAccountIdIsZero() {
        Transaction transaction = new Transaction(1L, 0L, new BigDecimal(2));
        TransactionValidator.validate(transaction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIncorrectToAccountId() {
        Transaction transaction = new Transaction(1L, -1L, new BigDecimal(2));
        TransactionValidator.validate(transaction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionFromAccountIdIsEqualsToAccountId() {
        Transaction transaction = new Transaction(1L, 1L, new BigDecimal(2));
        TransactionValidator.validate(transaction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionTransactionAmountIsZero() {
        Transaction transaction = new Transaction(1L, 2L, new BigDecimal(0));
        TransactionValidator.validate(transaction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionTransactionAmountIsNegative() {
        Transaction transaction = new Transaction(1L, 2L, new BigDecimal(-1));
        TransactionValidator.validate(transaction);
    }
}
