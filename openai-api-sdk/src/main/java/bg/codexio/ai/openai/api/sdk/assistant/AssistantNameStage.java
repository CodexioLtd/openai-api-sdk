package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.http.assistant.AssistantHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;

public class AssistantNameStage
        extends AssistantConfigurationStage {
    AssistantNameStage(
            AssistantHttpExecutor httpExecutor,
            AssistantRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public AssistantInstructionStage called(String name) {
        return new AssistantInstructionStage(
                this.httpExecutor,
                this.requestBuilder.withName(name)
        );
    }
}