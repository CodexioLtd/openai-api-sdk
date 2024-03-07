package bg.codexio.ai.openai.api.sdk.thread.create;

import bg.codexio.ai.openai.api.http.thread.CreateThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import bg.codexio.ai.openai.api.sdk.thread.executor.ThreadAsyncRuntimeExecutor;

public class ThreadAsyncContextStage
        extends ThreadConfigurationStage
        implements ThreadAsyncRuntimeExecutor {

    ThreadAsyncContextStage(
            CreateThreadHttpExecutor httpExecutor,
            ThreadCreationRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    @Override
    public ThreadAsyncPromiseStage finishRaw() {
        return new ThreadAsyncPromiseStage(
                this.httpExecutor,
                this.requestBuilder
        );
    }
}