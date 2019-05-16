package pl.revolut.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.revolut.test.model.Account;
import pl.revolut.test.model.Transaction;
import pl.revolut.test.model.TransactionHistory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class TransactionServiceImpl implements TransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final AtomicLong count = new AtomicLong(1);
    private final AccountService accountService;
    private final List<TransactionHistory> transactions = Collections.synchronizedList(new LinkedList<>());

    TransactionServiceImpl(final AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doTransaction(final Transaction transaction) {
        TransactionValidator.validate(transaction);
        LOGGER.debug("start transaction");

        Account fromAccount = accountService.getAccount(transaction.getFromAccountId());
        Account toAccount = accountService.getAccount(transaction.getToAccountId());

        if (fromAccount == null || toAccount == null) {
            throw new IllegalStateException("Account don't exists");
        }

        Long firstBalance = fromAccount.getId() > toAccount.getId() ? fromAccount.getId() : toAccount.getId();
        Long secondBalance = fromAccount.getId() > toAccount.getId() ? toAccount.getId() : fromAccount.getId();

        synchronized (accountService.getAccount(firstBalance)) {
            synchronized (accountService.getAccount(secondBalance)) {
                if (fromAccount.getBalance().compareTo(transaction.getAmount()) < 0) {
                    throw new IllegalStateException("Account don't have enough money");
                }
                TransactionHistory transactionHistory
                        = new TransactionHistory(count.getAndIncrement(), fromAccount.getId(), toAccount.getId(), transaction.getAmount());
                LOGGER.debug("start transaction with id: " + transactionHistory.getTransactionId());

                transactions.add(transactionHistory);
                fromAccount.setBalance(fromAccount.getBalance().add(transaction.getAmount().negate()));
                transactionHistory.setStatus(TransactionHistory.Status.AMOUNT_TAKEN_FROM_ACCOUNT);
                toAccount.setBalance(toAccount.getBalance().add(transaction.getAmount()));
                transactionHistory.setStatus(TransactionHistory.Status.SUCCESS);
            }
        }
        LOGGER.debug("end transaction");

    }

    @Override
    public List<TransactionHistory> getTransactions() {
        return transactions;
    }
}
