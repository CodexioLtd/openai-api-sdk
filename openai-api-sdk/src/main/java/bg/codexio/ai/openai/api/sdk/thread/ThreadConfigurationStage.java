package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.thread.ThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.CreateThreadRequest;

public abstract class ThreadConfigurationStage {

    protected final ThreadHttpExecutor httpExecutor;
    protected final CreateThreadRequest.Builder requestBuilder;

    ThreadConfigurationStage(
            ThreadHttpExecutor httpExecutor,
            CreateThreadRequest.Builder requestBuilder
    ) {
        this.httpExecutor = httpExecutor;
        this.requestBuilder = requestBuilder;
    }
}