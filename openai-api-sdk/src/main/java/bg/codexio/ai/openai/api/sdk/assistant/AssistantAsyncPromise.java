package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.http.assistant.AssistantHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.response.AssistantResponse;

import java.util.function.Consumer;

public class AssistantAsyncPromise
        extends AssistantConfigurationStage {
    AssistantAsyncPromise(
            AssistantHttpExecutor httpExecutor,
            AssistantRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public void then(Consumer<AssistantResponse> afterAll) {
        this.then(
                x -> {},
                afterAll
        );
    }

    public void onEachLine(Consumer<String> onEachLine) {
        this.then(
                onEachLine,
                x -> {}
        );
    }

    public void then(
            Consumer<String> onEachLine,
            Consumer<AssistantResponse> afterAll
    ) {
        this.httpExecutor.executeAsync(
                this.requestBuilder.build(),
                onEachLine,
                afterAll
        );
    }
}
