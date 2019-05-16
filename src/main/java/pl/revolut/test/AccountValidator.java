package pl.revolut.test;

import pl.revolut.test.model.Account;
import spark.utils.StringUtils;

public final class AccountValidator {

    public static void validate(final Account account) {
        if (StringUtils.isEmpty(account.getFirstName())) {
            throw new IllegalArgumentException("empty account first name");
        }

        if (StringUtils.isEmpty(account.getLastName())) {
            throw new IllegalArgumentException("empty account last name");
        }

        if (account.getBalance() == null) {
            throw new IllegalArgumentException("field balance is required");
        }
    }

    private AccountValidator() {
    }
}
