package bg.codexio.ai.openai.api.http;

import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.Streamable;

/**
 * <p>
 * Abstraction over HTTP execution to OpenAPI.
 * It takes a potentially {@link Streamable} request,
 * and sends HTTP request, which execution can be
 * immediately executed in a blocking manner, or
 * scheduled for execution in asynchronous
 * (reactive included) fashion.
 * </p>
 *
 * @param <I> The request (Input) model
 * @param <O> The response (Output) model
 */
public interface OpenAIHttpExecutor<I extends Streamable,
        O extends Mergeable<O>> {
    SynchronousHttpExecutor<I, O> immediate();

    AsynchronousHttpExecutor<I, O> async();

    ReactiveHttpExecutor<I, O> reactive();

    boolean canStream(I input);
}