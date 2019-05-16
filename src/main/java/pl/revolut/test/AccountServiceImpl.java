package pl.revolut.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.revolut.test.model.Account;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AtomicLong count = new AtomicLong(1);
    private final Map<Long, Account> longUserMap = new ConcurrentHashMap<>();

    @Override
    public long addAccount(final Account account) {
        AccountValidator.validate(account);
        long id = count.getAndIncrement();
        account.setId(id);
        longUserMap.put(id, account);
        LOGGER.debug("add account: " + id);
        return id;
    }

    @Override
    public Collection<Account> getAccounts() {
        return longUserMap.values();
    }

    @Override
    public Account getAccount(final Long id) {
        return longUserMap.get(id);
    }

    @Override
    public void deleteAccount(final Long id) {
        longUserMap.remove(id);
        LOGGER.debug("delete account: " + id);
    }

    @Override
    public boolean isAccountExist(final Long id) {
        return longUserMap.containsKey(id);
    }

}
