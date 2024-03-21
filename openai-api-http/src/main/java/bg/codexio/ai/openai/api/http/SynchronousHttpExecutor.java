package bg.codexio.ai.openai.api.http;

import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.Streamable;

public interface SynchronousHttpExecutor<I extends Streamable,
        O extends Mergeable<O>> {
    /**
     * Executes HTTP request synchronously
     *
     * @param request The request (Input) model
     * @return deserialized HTTP Response in the Output model
     */
    O execute(I request);

    O executeWithPathVariable(
            I request,
            String pathVariable
    );

    O retrieve(String... pathVariables);
}