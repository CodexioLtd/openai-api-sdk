package bg.codexio.ai.openai.api.sdk.message.chat;

import bg.codexio.ai.openai.api.http.message.MessageHttpExecutor;
import bg.codexio.ai.openai.api.payload.assistant.response.AssistantResponse;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.sdk.run.RunnableAdvancedConfigurationStage;
import bg.codexio.ai.openai.api.sdk.run.Runnables;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class MessageAssistantStage
        extends MessageConfigurationStage {

    MessageAssistantStage(
            MessageHttpExecutor httpExecutor,
            MessageRequest.Builder requestBuilder,
            String threadId
    ) {
        super(
                httpExecutor,
                requestBuilder,
                threadId
        );
    }

    public RunnableAdvancedConfigurationStage assistImmediate(String assistantId) {
        this.executeImmediate();

        return Runnables.defaults(this.threadId)
                        .and()
                        .deepConfigure(assistantId);
    }

    public RunnableAdvancedConfigurationStage assistImmediate(AssistantResponse assistantResponse) {
        this.executeImmediate();

        return Runnables.defaults(this.threadId)
                        .and()
                        .deepConfigure(assistantResponse);
    }

    public void assistAsync(
            CompletableFuture<AssistantResponse> assistantResponse,
            Consumer<RunnableAdvancedConfigurationStage> consumer
    ) {
        assistantResponse.thenAcceptAsync(assistant -> new MessageAsyncPromiseStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        ).then(message -> consumer.accept(Runnables.defaults(this.threadId)
                                                   .and()
                                                   .deepConfigure(assistant))));
    }

    public Mono<RunnableAdvancedConfigurationStage> assistReactive(Mono<AssistantResponse> assistantResponse) {
        return new MessageReactiveContextStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        ).finish()
         .response()
         .flatMap(message -> assistantResponse.flatMap(assistant -> Mono.just(Runnables.defaults(this.threadId)
                                                                                       .and()
                                                                                       .deepConfigure(assistant)))

         );
    }

    private void executeImmediate() {
        new MessageImmediateContextStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        ).finish();
    }
}