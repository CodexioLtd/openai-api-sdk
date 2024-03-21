package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.http.assistant.AssistantHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.response.AssistantResponse;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

public class AssistantImmediateContextStage
        extends AssistantConfigurationStage
        implements RuntimeExecutor {
    AssistantImmediateContextStage(
            AssistantHttpExecutor httpExecutor,
            AssistantRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    // think about better name of this method
    public AssistantResponse finishRaw() {
        return this.httpExecutor.immediate()
                                .execute(this.requestBuilder.build());
    }

    public String finish() {
        return this.finishRaw()
                   .id();
    }
}