package bg.codexio.ai.openai.api.http;

import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.Streamable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

/**
 * Abstraction over HTTP execution to OpenAPI.
 * It takes a potentially {@link Streamable} request,
 * and sends HTTP request, which execution can be
 * immediately executed in a blocking manner, or
 * scheduled for execution in asynchronous
 * (reactive included) fashion.
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

    /**
     * Executes HTTP request asynchronously.
     * Since response can be streamed, it can be
     * potentially beneficial for the developer,
     * to subscribe to each line, hence the callback
     * parameter.
     * It makes a little bit more sense to subscribe,
     * to the whole response, using the finalizer parameter.
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
     * Executes HTTP request in reactive fashion.
     * We strongly recommend to use this only if
     * a real reactive runtime is present, such as
     * <a href="https://github.com/reactor/reactor-netty">Reactor netty</a>.
     *
     * @param request The request (Input) model
     * @return {@link ReactiveExecution<O>} object holding a single observable
     * ({@link Mono<O>}) to the whole response,
     * and a multi-emit observable ({@link Flux<String>})
     * to each response line.
     */
    ReactiveExecution<O> executeReactive(I request);

    /**
     * Denotes whether the response can be streamed,
     * mostly taking into account the value of {@link Streamable#stream()}
     * which usually is implemented like inputModel -> inputModel.stream() &&
     * specificApiLogic.
     *
     * @param input The request (Input) model
     * @return boolean saying if you can stream the response or not.
     * Usually beneficial for internal calls like {@link #execute(Streamable)}
     */
    boolean canStream(I input);

    /**
     * Data holder for the response of {@link #executeReactive(Streamable)}
     *
     * @param <O> The response (Output) model
     */
    class ReactiveExecution<O> {
        private final Flux<String> lines;
        private final Mono<O> response;

        public ReactiveExecution(
                Flux<String> lines,
                Mono<O> response
        ) {
            this.lines = lines;
            this.response = response;
        }

        public Flux<String> lines() {
            return lines;
        }

        public Mono<O> response() {
            return response;
        }
    }
}
