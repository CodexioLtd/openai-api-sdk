package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.http.assistant.AssistantHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.response.AssistantResponse;

public class AssistantInstructionStage
        extends AssistantConfigurationStage {
    public AssistantInstructionStage(
            AssistantHttpExecutor httpExecutor,
            AssistantRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public AssistantDescriptionStage instructToDescription(String instruction) {
        return new AssistantDescriptionStage(
                this.httpExecutor,
                this.requestBuilder.withInstructions(instruction)
        );
    }

    public AssistantFileStage instructToFile(String instruction) {
        return new AssistantFileStage(
                this.httpExecutor,
                this.requestBuilder.withInstructions(instruction)
        );
    }

    public AssistantMetadataStage instructToMetadata(String instruction) {
        return new AssistantMetadataStage(
                this.httpExecutor,
                this.requestBuilder.withInstructions(instruction)
        );
    }

    public AssistantResponse instruct(String instruction) {
        return this.httpExecutor.execute(this.requestBuilder.withInstructions(instruction)
                                                            .build());
    }
}