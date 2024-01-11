package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.thread.ThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.CreateThreadRequest;

public abstract class ThreadConfigurationStage {

    protected final ThreadHttpExecutor httpExecutor;

    protected final CreateThreadRequest.Builder requestContext;

    protected ThreadConfigurationStage(
            ThreadHttpExecutor httpExecutor,
            CreateThreadRequest.Builder requestContext
    ) {
        this.httpExecutor = httpExecutor;
        this.requestContext = requestContext;
    }
}