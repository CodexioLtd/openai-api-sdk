package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.http.assistant.AssistantHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;

public class AssistantAdvancedConfigurationStage
        extends AssistantConfigurationStage {

    AssistantAdvancedConfigurationStage(
            AssistantHttpExecutor httpExecutor,
            AssistantRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public AssistantFileStage file() {
        return new AssistantFileStage(
                this.httpExecutor,
                this.requestBuilder
        );
    }

    public AssistantMetaStage meta() {
        return new AssistantMetaStage(
                this.httpExecutor,
                this.requestBuilder
        );
    }

    public AssistantDescriptionStage description() {
        return new AssistantDescriptionStage(
                this.httpExecutor,
                this.requestBuilder
        );
    }

    public AssistantRuntimeSelectionStage andRespond() {
        return new AssistantRuntimeSelectionStage(
                this.httpExecutor,
                this.requestBuilder
        );
    }
}