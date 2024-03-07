package bg.codexio.ai.openai.api.sdk.thread.modify;

import bg.codexio.ai.openai.api.http.thread.ModifyThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadModificationRequest;

public abstract class ThreadModificationConfigurationStage {

    protected final ModifyThreadHttpExecutor httpExecutor;
    protected final ThreadModificationRequest.Builder requestBuilder;
    protected final String threadId;

    ThreadModificationConfigurationStage(
            ModifyThreadHttpExecutor httpExecutor,
            ThreadModificationRequest.Builder requestBuilder,
            String threadId
    ) {
        this.httpExecutor = httpExecutor;
        this.requestBuilder = requestBuilder;
        this.threadId = threadId;
    }
}