package bg.codexio.ai.openai.api.sdk.thread.modify;

import bg.codexio.ai.openai.api.http.thread.ModifyThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadModificationRequest;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.thread.executor.ThreadImmediateRuntimeExecutor;

public class ThreadModificationImmediateContextStage
        extends ThreadModificationConfigurationStage
        implements ThreadImmediateRuntimeExecutor {
    ThreadModificationImmediateContextStage(
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
    public ThreadResponse finishRaw() {
        return this.httpExecutor.immediate()
                                .executeWithPathVariable(
                                        this.requestBuilder.build(),
                                        this.threadId
                                );
    }

    @Override
    public String finish() {
        return this.finishRaw()
                   .id();
    }
}