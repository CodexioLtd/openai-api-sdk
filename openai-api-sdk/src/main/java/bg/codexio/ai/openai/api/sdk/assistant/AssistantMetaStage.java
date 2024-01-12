package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.http.assistant.AssistantHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;

public class AssistantMetaStage
        extends AssistantInstructionStage {
    AssistantMetaStage(
            AssistantHttpExecutor httpExecutor,
            AssistantRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public AdvancedConfigurationStage awareOf(String... metadata) {
        return new AdvancedConfigurationStage(
                this.httpExecutor,
                this.requestBuilder.addMetadata(metadata)
        );
    }
}