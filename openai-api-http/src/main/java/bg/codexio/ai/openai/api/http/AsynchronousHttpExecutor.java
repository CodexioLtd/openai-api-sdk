package bg.codexio.ai.openai.api.http;

import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.Streamable;

import java.util.function.Consumer;

public interface AsynchronousHttpExecutor<I extends Streamable,
        O extends Mergeable<O>> {
    void execute(
            I request,
            Consumer<String> callBack,
            Consumer<O> finalizer
    );

    void executeWithPathVariable(
            I request,
            String pathVariable,
            Consumer<String> callBack,
            Consumer<O> finalizer
    );

    void retrieve(
            Consumer<String> callBack,
            Consumer<O> finalizer,
            String... pathVariables
    );
}