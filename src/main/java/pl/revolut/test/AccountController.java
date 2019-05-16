package pl.revolut.test;

import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import pl.revolut.test.model.Account;
import pl.revolut.test.model.StandardResponse;

import static spark.Spark.delete;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.post;

public class AccountController {

    private final AccountService accountService;

    public AccountController(final AccountService accountService) {
        this.accountService = accountService;
    }

    public void accountsRestEndpoints() {
        get("/users", (request, response) -> {
            response.type("application/json");
            return new StandardResponse(HttpStatus.OK_200, HttpStatus.Code.OK.getMessage(),
                    new Gson().toJsonTree(accountService.getAccounts()));
        });

        post("/users", (request, response) -> {
            Account account = new Gson().fromJson(request.body(), Account.class);
            accountService.addAccount(account);

            response.status(HttpStatus.CREATED_201);
            return new StandardResponse(HttpStatus.CREATED_201, HttpStatus.Code.CREATED.getMessage(), null);
        });

        delete("/users/:id", (request, response) -> {
            accountService.deleteAccount(Long.parseLong(request.params(":id")));
            return new StandardResponse(HttpStatus.NO_CONTENT_204, HttpStatus.Code.NO_CONTENT.getMessage(), null);
        });

        get("/users/:id", (request, response) ->
                new StandardResponse(HttpStatus.OK_200, HttpStatus.Code.OK.getMessage(),
                        new Gson().toJsonTree(accountService.getAccount(Long.parseLong(request.params(":id")))))
        );

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(HttpStatus.BAD_REQUEST_400);
            res.body(e.getMessage());
        });
    }
}
