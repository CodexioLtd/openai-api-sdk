package bg.codexio.ai.openai.api.sdk.thread.executor;

import bg.codexio.ai.openai.api.http.ReactiveHttpExecutor.ReactiveExecution;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;
import reactor.core.publisher.Mono;

public interface ThreadReactiveRuntimeExecutor
        extends RuntimeExecutor {
    ReactiveExecution<ThreadResponse> finishRaw();

    Mono<String> finish();
}