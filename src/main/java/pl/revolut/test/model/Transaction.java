package pl.revolut.test.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Transaction {

    private long fromAccountId;
    private long toAccountId;
    private BigDecimal amount;

    public Transaction() {
    }

    public Transaction(final long fromAccountId, final long toAccountId, final BigDecimal amount) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(final Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(final Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        final Transaction that = (Transaction) o;
        return Objects.equals(fromAccountId, that.fromAccountId)
                && Objects.equals(toAccountId, that.toAccountId)
                && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromAccountId, toAccountId, amount);
    }

    @Override
    public String toString() {
        return "Transaction{"
                + "fromUserId=" + fromAccountId
                + ", toUserId=" + toAccountId
                + ", amount=" + amount
                + '}';
    }
}
