package bg.codexio.ai.openai.api.sdk.message.chat;

import bg.codexio.ai.openai.api.http.ReactiveHttpExecutor.ReactiveExecution;
import bg.codexio.ai.openai.api.http.message.MessageHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.message.response.MessageResponse;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;
import reactor.core.publisher.Mono;

public class MessageReactiveContextStage
        extends MessageConfigurationStage
        implements RuntimeExecutor {
    MessageReactiveContextStage(
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

    public ReactiveExecution<MessageResponse> finishRaw() {
        return this.httpExecutor.reactive()
                                .executeWithPathVariable(
                                        this.requestBuilder.build(),
                                        this.threadId
                                );
    }

    public Mono<MessageResponse> finish() {
        return this.finishRaw()
                   .response();
    }
}
