package pl.revolut.test;

import pl.revolut.test.model.Transaction;

import java.math.BigDecimal;

public final class TransactionValidator {

    static void validate(final Transaction transaction) {
        if (transaction.getFromAccountId() <= 0) {
            throw new IllegalArgumentException("Bad from user Id");
        }

        if (transaction.getToAccountId() <= 0) {
            throw new IllegalArgumentException("Bad from user Id");
        }

        if (transaction.getFromAccountId().equals(transaction.getToAccountId())) {
            throw new IllegalArgumentException("Transaction from same account");
        }

        if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transaction amount smaller or equals than 0");
        }
    }

    private TransactionValidator() {
    }
}
