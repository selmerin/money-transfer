package pl.revolut.test;

public final class Main {

    public static void main(final String[] args) {

        AccountService accountService = new AccountServiceImpl();
        TransactionService transactionService = new TransactionServiceImpl(accountService);

        AccountController accountController = new AccountController(accountService);
        TransactionController transactionController = new TransactionController(transactionService);

        accountController.accountsRestEndpoints();
        transactionController.transactionRestEndpoints();
    }

    private Main() {
    }
}
