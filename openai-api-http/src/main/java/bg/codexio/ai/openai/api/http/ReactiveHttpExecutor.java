package bg.codexio.ai.openai.api.http;

import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.Streamable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveHttpExecutor<I extends Streamable,
        O extends Mergeable<O>> {
    ReactiveExecution<O> execute(I request);

    ReactiveExecution<O> executeWithPathVariable(
            I request,
            String pathVariable
    );

    ReactiveExecution<O> retrieve(String... pathVariables);

    /**
     * Data holder for the response of {@link #execute(Streamable)}
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