package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.http.run.RunnableHttpExecutor;
import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;

public abstract class RunnableConfigurationStage {

    protected final RunnableHttpExecutor httpExecutor;
    protected final RunnableRequest.Builder httpRequest;
    protected final String threadId;

    RunnableConfigurationStage(
            RunnableHttpExecutor httpExecutor,
            RunnableRequest.Builder httpRequest,
            String threadId
    ) {
        this.httpExecutor = httpExecutor;
        this.httpRequest = httpRequest;
        this.threadId = threadId;
    }
}