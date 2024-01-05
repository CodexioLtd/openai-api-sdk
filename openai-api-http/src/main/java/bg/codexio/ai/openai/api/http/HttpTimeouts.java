package bg.codexio.ai.openai.api.http;

import java.util.Objects;

/**
 * Context object hinting the variety of timeouts
 */
public final class HttpTimeouts {
    private final HttpTimeout call;
    private final HttpTimeout connect;
    private final HttpTimeout read;

    public HttpTimeouts(
            HttpTimeout call,
            HttpTimeout connect,
            HttpTimeout read
    ) {
        this.call = call;
        this.connect = connect;
        this.read = read;
    }

    public HttpTimeout call() {
        return call;
    }

    public HttpTimeout connect() {
        return connect;
    }

    public HttpTimeout read() {
        return read;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (HttpTimeouts) obj;
        return Objects.equals(
                this.call,
                that.call
        ) && Objects.equals(
                this.connect,
                that.connect
        ) && Objects.equals(
                this.read,
                that.read
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                call,
                connect,
                read
        );
    }

    @Override
    public String toString() {
        return "HttpTimeouts[" + "call=" + call + ", " + "connect=" + connect
                + ", " + "read=" + read + ']';
    }
}
