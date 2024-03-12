package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.http.assistant.AssistantHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

public class AssistantAsyncContextStage
        extends AssistantConfigurationStage
        implements RuntimeExecutor {
    AssistantAsyncContextStage(
            AssistantHttpExecutor httpExecutor,
            AssistantRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public AssistantAsyncPromiseStage finish() {
        return new AssistantAsyncPromiseStage(
                this.httpExecutor,
                this.requestBuilder
        );
    }
}
