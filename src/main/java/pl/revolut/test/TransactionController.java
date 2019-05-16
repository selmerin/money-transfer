package pl.revolut.test;

import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import pl.revolut.test.model.StandardResponse;
import pl.revolut.test.model.Transaction;

import static spark.Spark.get;
import static spark.Spark.post;

public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(final TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void transactionRestEndpoints() {
        post("/transaction", (request, response) -> {
            Transaction transaction = new Gson().fromJson(request.body(), Transaction.class);
            transactionService.doTransaction(transaction);

            response.status(HttpStatus.CREATED_201);
            return new StandardResponse(HttpStatus.CREATED_201, HttpStatus.Code.CREATED.getMessage(), null);
        });
        get("/transactions", (request, response) -> {
            response.type("application/json");
            return new StandardResponse(HttpStatus.OK_200, HttpStatus.Code.OK.getMessage(),
                    new Gson().toJsonTree(transactionService.getTransactions()));
        });
    }
}
