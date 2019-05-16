package pl.revolut.test.model;

import java.math.BigDecimal;
import java.util.Objects;

public class TransactionHistory {
    private final Long transactionId;
    private final Long accountFromId;
    private final Long accountToId;
    private final BigDecimal ammount;
    private Status status;

    public TransactionHistory(final Long transactionId, final Long accountFromId, final Long accountToId, final BigDecimal ammount) {
        this.transactionId = transactionId;
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.ammount = ammount;
        this.status = Status.NEW;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public Long getAccountFromId() {
        return accountFromId;
    }

    public Long getAccountToId() {
        return accountToId;
    }

    public BigDecimal getAmmount() {
        return ammount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionHistory)) {
            return false;
        }
        final TransactionHistory that = (TransactionHistory) o;
        return Objects.equals(transactionId, that.transactionId)
                && Objects.equals(accountFromId, that.accountFromId)
                && Objects.equals(accountToId, that.accountToId)
                && Objects.equals(ammount, that.ammount)
                && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, accountFromId, accountToId, ammount, status);
    }

    @Override
    public String toString() {
        return "TransactionHistory{" + "transactionId=" + transactionId
                + ", accountFromId=" + accountFromId
                + ", accountToId=" + accountToId
                + ", ammount=" + ammount
                + ", status=" + status
                + '}';
    }

    public enum Status {
        NEW, SUCCESS, AMOUNT_TAKEN_FROM_ACCOUNT, FAILURE;
    }
}
