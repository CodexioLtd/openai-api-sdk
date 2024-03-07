package bg.codexio.ai.openai.api.sdk.thread.create;

import bg.codexio.ai.openai.api.http.thread.CreateThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;

public abstract class ThreadConfigurationStage {

    protected final CreateThreadHttpExecutor httpExecutor;
    protected final ThreadCreationRequest.Builder requestBuilder;

    ThreadConfigurationStage(
            CreateThreadHttpExecutor httpExecutor,
            ThreadCreationRequest.Builder requestBuilder
    ) {
        this.httpExecutor = httpExecutor;
        this.requestBuilder = requestBuilder;
    }
}