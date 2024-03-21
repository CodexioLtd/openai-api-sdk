package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.http.assistant.AssistantHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.response.AssistantResponse;
import bg.codexio.ai.openai.api.sdk.AsyncPromiseStage;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AssistantAsyncPromiseStage
        extends AssistantConfigurationStage
        implements AsyncPromiseStage<AssistantResponse> {
    AssistantAsyncPromiseStage(
            AssistantHttpExecutor httpExecutor,
            AssistantRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public CompletableFuture<AssistantResponse> then() {
        var assistantResponseFuture =
                new CompletableFuture<AssistantResponse>();
        then(assistantResponseFuture::complete);

        return assistantResponseFuture;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void then(
            Consumer<String> onEachLine,
            Consumer<AssistantResponse> afterAll
    ) {
        var assistantResponseFuture =
                new CompletableFuture<AssistantResponse>();

        this.httpExecutor.async()
                         .execute(
                                 this.requestBuilder.build(),
                                 onEachLine,
                                 response -> {
                                     assistantResponseFuture.complete(response);
                                     afterAll.accept(response);
                                 }
                         );
    }
}
