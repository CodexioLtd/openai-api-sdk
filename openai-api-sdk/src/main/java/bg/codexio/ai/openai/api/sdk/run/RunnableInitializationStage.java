package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.http.run.RunnableHttpExecutor;
import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import bg.codexio.ai.openai.api.payload.run.response.RunnableResponse;

public class RunnableInitializationStage
        extends RunnableConfigurationStage {
    RunnableInitializationStage(
            RunnableHttpExecutor httpExecutor,
            RunnableRequest.Builder httpRequest,
            String threadId
    ) {
        super(
                httpExecutor,
                httpRequest,
                threadId
        );
    }

    public RunnableResponse run() {
        // continue with implementation here
        // add pathVariable + id of the assistant to instantiate the request
        // object
        return null;
    }
}
