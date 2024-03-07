package bg.codexio.ai.openai.api.sdk.thread.create;

import bg.codexio.ai.openai.api.http.OpenAIHttpExecutor.ReactiveExecution;
import bg.codexio.ai.openai.api.http.thread.CreateThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.thread.executor.ThreadReactiveRuntimeExecutor;
import reactor.core.publisher.Mono;

public class ThreadReactiveContextStage
        extends ThreadConfigurationStage
        implements ThreadReactiveRuntimeExecutor {


    ThreadReactiveContextStage(
            CreateThreadHttpExecutor httpExecutor,
            ThreadCreationRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    @Override
    public ReactiveExecution<ThreadResponse> finishRaw() {
        return this.httpExecutor.executeReactive(this.requestBuilder.build());
    }

    @Override
    public Mono<String> finish() {
        return this.finishRaw()
                   .response()
                   .map(ThreadResponse::id);
    }
}