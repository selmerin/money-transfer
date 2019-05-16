package pl.revolut.test;

import pl.revolut.test.model.Account;

import java.util.Collection;

public interface AccountService {
    long addAccount(Account account);

    Collection<Account> getAccounts();

    Account getAccount(Long id);

    void deleteAccount(Long id);

    boolean isAccountExist(Long id);

}
