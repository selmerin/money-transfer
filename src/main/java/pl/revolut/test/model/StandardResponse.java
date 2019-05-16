package pl.revolut.test.model;

import com.google.gson.JsonElement;

import java.util.Objects;

public class StandardResponse {
    private int status;
    private String message;
    private JsonElement data;

    public StandardResponse() {
    }

    public StandardResponse(final int status, final String message, final JsonElement data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(final int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(final JsonElement data) {
        this.data = data;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StandardResponse)) {
            return false;
        }
        final StandardResponse that = (StandardResponse) o;
        return Objects.equals(status, that.status)
                && Objects.equals(message, that.message)
                && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, message, data);
    }

    @Override
    public String toString() {
        return "StandardResponse{" + "status=" + status
                + ", message='" + message + '\''
                + ", data=" + data
                + '}';
    }
}
