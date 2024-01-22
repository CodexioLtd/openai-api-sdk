package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.http.assistant.AssistantHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;

public abstract class AssistantConfigurationStage {

    protected final AssistantHttpExecutor httpExecutor;
    protected final AssistantRequest.Builder requestBuilder;

    AssistantConfigurationStage(
            AssistantHttpExecutor httpExecutor,
            AssistantRequest.Builder requestBuilder
    ) {
        this.httpExecutor = httpExecutor;
        this.requestBuilder = requestBuilder;
    }
}