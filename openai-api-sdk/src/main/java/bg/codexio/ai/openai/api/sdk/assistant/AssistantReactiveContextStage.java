package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.http.OpenAIHttpExecutor.ReactiveExecution;
import bg.codexio.ai.openai.api.http.assistant.AssistantHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.response.AssistantResponse;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;
import reactor.core.publisher.Mono;

public class AssistantReactiveContextStage
        extends AssistantConfigurationStage
        implements RuntimeExecutor {
    AssistantReactiveContextStage(
            AssistantHttpExecutor httpExecutor,
            AssistantRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public ReactiveExecution<AssistantResponse> finishRaw() {
        return this.httpExecutor.executeReactive(this.requestBuilder.build());
    }

    public Mono<String> finish() {
        return this.finishRaw()
                   .response()
                   .map(AssistantResponse::id);
    }
}