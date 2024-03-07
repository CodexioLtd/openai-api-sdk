package bg.codexio.ai.openai.api.sdk.thread.modify;

import bg.codexio.ai.openai.api.http.OpenAIHttpExecutor.ReactiveExecution;
import bg.codexio.ai.openai.api.http.thread.ModifyThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadModificationRequest;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.thread.executor.ThreadReactiveRuntimeExecutor;
import reactor.core.publisher.Mono;

public class ThreadModificationReactiveContextStage
        extends ThreadModificationConfigurationStage
        implements ThreadReactiveRuntimeExecutor {
    ThreadModificationReactiveContextStage(
            ModifyThreadHttpExecutor httpExecutor,
            ThreadModificationRequest.Builder requestBuilder,
            String threadId
    ) {
        super(
                httpExecutor,
                requestBuilder,
                threadId
        );
    }

    @Override
    public ReactiveExecution<ThreadResponse> finishRaw() {
        return this.httpExecutor.executeReactiveWithPathVariable(
                this.requestBuilder.build(),
                this.threadId
        );
    }

    @Override
    public Mono<String> finish() {
        return this.finishRaw()
                   .response()
                   .map(ThreadResponse::id);
    }
}