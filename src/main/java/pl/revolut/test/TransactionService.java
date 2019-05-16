package pl.revolut.test;

import pl.revolut.test.model.Transaction;
import pl.revolut.test.model.TransactionHistory;

import java.util.List;

public interface TransactionService {

    void doTransaction(Transaction transaction);

    List<TransactionHistory> getTransactions();
}
