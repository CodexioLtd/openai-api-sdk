package bg.codexio.ai.openai.api.http;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Context object holding authentication
 * and timeout configuration
 */
public final class HttpExecutorContext {
    private final ApiCredentials credentials;
    private final HttpTimeouts timeouts;

    /**
     * @param credentials
     * @param timeouts
     */
    public HttpExecutorContext(
            ApiCredentials credentials,
            HttpTimeouts timeouts
    ) {
        this.credentials = credentials;
        this.timeouts = timeouts;
    }

    /**
     * Constructing the object with default timeouts.
     * 3 minutes for call, connect and read.
     */
    public HttpExecutorContext(ApiCredentials credentials) {
        this(
                credentials,
                new HttpTimeouts(
                        new HttpTimeout(
                                3,
                                TimeUnit.MINUTES
                        ),
                        new HttpTimeout(
                                3,
                                TimeUnit.MINUTES
                        ),
                        new HttpTimeout(
                                3,
                                TimeUnit.MINUTES
                        )
                )
        );
    }

    public HttpExecutorContext withCallTimeout(
            long timeout,
            TimeUnit timeUnit
    ) {
        return new HttpExecutorContext(
                this.credentials(),
                new HttpTimeouts(
                        new HttpTimeout(
                                timeout,
                                timeUnit
                        ),
                        this.timeouts()
                            .connect(),
                        this.timeouts()
                            .read()
                )
        );
    }

    public HttpExecutorContext withConnectTimeout(
            long timeout,
            TimeUnit timeUnit
    ) {
        return new HttpExecutorContext(
                this.credentials(),
                new HttpTimeouts(
                        this.timeouts()
                            .call(),
                        new HttpTimeout(
                                timeout,
                                timeUnit
                        ),
                        this.timeouts()
                            .read()
                )
        );
    }

    public HttpExecutorContext withReadTimeout(
            long timeout,
            TimeUnit timeUnit
    ) {
        return new HttpExecutorContext(
                this.credentials(),
                new HttpTimeouts(
                        this.timeouts()
                            .call(),
                        this.timeouts()
                            .connect(),
                        new HttpTimeout(
                                timeout,
                                timeUnit
                        )
                )
        );
    }

    public ApiCredentials credentials() {
        return credentials;
    }

    public HttpTimeouts timeouts() {
        return timeouts;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (HttpExecutorContext) obj;
        return Objects.equals(this.credentials,
                              that.credentials) && Objects.equals(this.timeouts,
                                                                  that.timeouts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(credentials,
                            timeouts);
    }

    @Override
    public String toString() {
        return "HttpExecutorContext[" + "credentials=" + credentials + ", "
                + "timeouts=" + timeouts + ']';
    }

}
