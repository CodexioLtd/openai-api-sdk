package bg.codexio.ai.openai.api.sdk.thread.modify;

import bg.codexio.ai.openai.api.http.thread.ModifyThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadModificationRequest;
import bg.codexio.ai.openai.api.sdk.thread.executor.ThreadAsyncRuntimeExecutor;

public class ThreadModificationAsyncContextStage
        extends ThreadModificationConfigurationStage
        implements ThreadAsyncRuntimeExecutor {
    ThreadModificationAsyncContextStage(
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
    public ThreadModificationAsyncPromiseStage finishRaw() {
        return new ThreadModificationAsyncPromiseStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }
}
