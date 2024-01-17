package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.http.assistant.AssistantHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;

public class AssistantInstructionStage
        extends AssistantConfigurationStage {
    AssistantInstructionStage(
            AssistantHttpExecutor httpExecutor,
            AssistantRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public AssistantAdvancedConfigurationStage instruct(String instruction) {
        return new AssistantAdvancedConfigurationStage(
                this.httpExecutor,
                this.requestBuilder.withInstructions(instruction)
        );
    }
}