package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.http.assistant.AssistantHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.response.AssistantResponse;

public class AssistantMetadataStage
        extends AssistantInstructionStage {
    public AssistantMetadataStage(
            AssistantHttpExecutor httpExecutor,
            AssistantRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public AssistantFileStage awareOfToFile(String... metadata) {
        return new AssistantFileStage(
                this.httpExecutor,
                this.requestBuilder.addMetadata(metadata)
        );
    }

    public AssistantResponse awareOf(String... metadata) {
        return this.httpExecutor.execute(this.requestBuilder.addMetadata(metadata)
                                                            .build());
    }
}