package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.http.assistant.AssistantHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.response.AssistantResponse;

public class AssistantDescriptionStage
        extends AssistantConfigurationStage {
    public AssistantDescriptionStage(
            AssistantHttpExecutor httpExecutor,
            AssistantRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public AssistantFileStage contextToFile(String description) {
        return new AssistantFileStage(
                this.httpExecutor,
                this.requestBuilder.withDescription(description)
        );
    }

    public AssistantResponse context(String description) {
        return this.httpExecutor.execute(this.requestBuilder.withDescription(description)
                                                            .build());
    }
}