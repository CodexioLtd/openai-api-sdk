package bg.codexio.ai.openai.api.http;

import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.Streamable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

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

    /**
     * Executes HTTP request synchronously
     *
     * @param request The request (Input) model
     * @return deserialized HTTP Response in the Output model
     */
    O execute(I request);

    O execute(String... pathVariables);

    O executeWithPathVariable(
            I request,
            String pathVariable
    );

    /**
     * <p>
     * Executes HTTP request asynchronously.
     * Since response can be streamed, it can be
     * potentially beneficial for the developer,
     * to subscribe to each line, hence the callback
     * parameter.
     * </p>
     * <p>
     * It makes a little bit more sense to subscribe,
     * to the whole response, using the finalizer parameter.
     * </p>
     *
     * @param request   The request (Input) model
     * @param callBack  A callback of type stringLine -> consume(stringLine)
     * @param finalizer A callback of type outputModel -> consume(outputModel)
     */
    void executeAsync(
            I request,
            Consumer<String> callBack,
            Consumer<O> finalizer
    );

    /**
     * <p>
     * Executes HTTP request in reactive fashion.
     * We strongly recommend to use this only if
     * a real reactive runtime is present, such as
     * <a href="https://github.com/reactor/reactor-netty">Reactor netty</a>.
     * </p>
     *
     * @param request The request (Input) model
     * @return {@link ReactiveExecution<O>} object holding a single observable
     * ({@link Mono<O>}) to the whole response,
     * and a multi-emit observable ({@link Flux<String>})
     * to each response line.
     */
    ReactiveExecution<O> executeReactive(I request);

    /**
     * <p>
     * Denotes whether the response can be streamed,
     * mostly taking into account the value of {@link Streamable#stream()}
     * which usually is implemented like inputModel -> inputModel.stream() &&
     * specificApiLogic.
     * </p>
     *
     * @param input The request (Input) model
     * @return boolean saying if you can stream the response or not.
     * Usually beneficial for internal calls like {@link #execute(Streamable)}
     */
    boolean canStream(I input);

    /**
     * Data holder for the response of {@link #executeReactive(Streamable)}
     *
     * @param lines    Observable to all string lines of the response
     * @param response Observable to single emit of the whole response object
     * @param <O>      The response (Output) model
     */
    record ReactiveExecution<O>(
            Flux<String> lines,
            Mono<O> response
    ) {}
}
