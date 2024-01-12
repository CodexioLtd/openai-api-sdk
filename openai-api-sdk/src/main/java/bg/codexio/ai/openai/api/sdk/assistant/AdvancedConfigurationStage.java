package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.http.assistant.AssistantHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.response.AssistantResponse;

public class AdvancedConfigurationStage
        extends AssistantConfigurationStage {
    AdvancedConfigurationStage(
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

    public AssistantResponse andRespond() {
        return this.httpExecutor.execute(this.requestBuilder.build());
    }
}