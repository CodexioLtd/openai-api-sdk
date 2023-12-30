package bg.codexio.ai.openai.api.sdk;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

/**
 * Default usage is to configure the HTTP connection and <br/>
 * serialization / deserialization methods using {@link ObjectMapper}. <br/>
 *
 * @param <T> The next stage to go after {@link #understanding(ObjectMapper)}
 *            or {@link #and()}.
 */
public class HttpBuilder<T> {

    private final HttpExecutorContext context;
    private final BiFunction<HttpExecutorContext, ObjectMapper, T> nextStepDelegate;

    /**
     * @param context          {@link HttpExecutorContext}
     * @param nextStepDelegate a supplier of {@link T} next stage,
     *                         which will receive {@link HttpExecutorContext}
     *                         and {@link ObjectMapper}
     *                         in order to create the instance.
     */
    public HttpBuilder(
            HttpExecutorContext context,
            BiFunction<HttpExecutorContext, ObjectMapper, T> nextStepDelegate
    ) {
        this.context = context;
        this.nextStepDelegate = nextStepDelegate;
    }

    /**
     * Sets {@link ObjectMapper} to be used from the HTTP client.
     *
     * @param mapper {@link ObjectMapper}
     * @return Next stage {@link T}
     */
    public T understanding(ObjectMapper mapper) {
        return this.nextStepDelegate.apply(
                this.context,
                mapper
        );
    }

    /**
     * Configures HTTP Read Timeout
     */
    public HttpBuilder<T> withReadTimeout(
            long timeout,
            TimeUnit timeUnit
    ) {
        return new HttpBuilder<>(
                this.context.withReadTimeout(
                        timeout,
                        timeUnit
                ),
                this.nextStepDelegate
        );
    }

    /**
     * Configures HTTP Connect Timeout
     */
    public HttpBuilder<T> withConnectTimeout(
            long timeout,
            TimeUnit timeUnit
    ) {
        return new HttpBuilder<>(
                this.context.withConnectTimeout(
                        timeout,
                        timeUnit
                ),
                this.nextStepDelegate
        );
    }

    /**
     * Configures HTTP Call Timeout
     */
    public HttpBuilder<T> withCallTimeout(
            long timeout,
            TimeUnit timeUnit
    ) {
        return new HttpBuilder<>(
                this.context.withCallTimeout(
                        timeout,
                        timeUnit
                ),
                this.nextStepDelegate
        );
    }

    /**
     * Goes to next stage with empty {@link ObjectMapper}
     *
     * @return Next stage {@link T}
     */
    public T and() {
        return this.understanding(new ObjectMapper());
    }
}
