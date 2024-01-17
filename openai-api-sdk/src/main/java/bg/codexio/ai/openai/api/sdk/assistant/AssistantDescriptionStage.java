package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.http.assistant.AssistantHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;

public class AssistantDescriptionStage
        extends AssistantConfigurationStage {
    AssistantDescriptionStage(
            AssistantHttpExecutor httpExecutor,
            AssistantRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public AssistantAdvancedConfigurationStage context(String description) {
        return new AssistantAdvancedConfigurationStage(
                this.httpExecutor,
                this.requestBuilder.withDescription(description)
        );
    }
}