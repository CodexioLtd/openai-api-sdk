package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.http.assistant.AssistantHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;

public class AssistantRuntimeSelectionStage
        extends AssistantConfigurationStage
        implements RuntimeSelectionStage {
    AssistantRuntimeSelectionStage(
            AssistantHttpExecutor httpExecutor,
            AssistantRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    @Override
    public AssistantImmediateContextStage immediate() {
        return new AssistantImmediateContextStage(
                this.httpExecutor,
                this.requestBuilder
        );
    }

    @Override
    public AssistantAsyncContextStage async() {
        return new AssistantAsyncContextStage(
                this.httpExecutor,
                this.requestBuilder
        );
    }

    @Override
    public AssistantReactiveContextStage reactive() {
        return new AssistantReactiveContextStage(
                this.httpExecutor,
                this.requestBuilder
        );
    }
}