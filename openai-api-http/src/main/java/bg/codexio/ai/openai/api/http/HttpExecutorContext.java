package bg.codexio.ai.openai.api.http;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;

import java.util.concurrent.TimeUnit;

/**
 * Context object holding authentication
 * and timeout configuration
 *
 * @param credentials
 * @param timeouts
 */
public record HttpExecutorContext(
        ApiCredentials credentials,
        HttpTimeouts timeouts
) {

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
}
