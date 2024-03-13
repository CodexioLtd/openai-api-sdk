package bg.codexio.ai.openai.api.sdk.message.chat;

import bg.codexio.ai.openai.api.http.message.MessageHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.message.response.MessageResponse;
import bg.codexio.ai.openai.api.sdk.AsyncPromiseStage;

import java.util.function.Consumer;

public class MessageAsyncPromiseStage
        extends MessageConfigurationStage
        implements AsyncPromiseStage<MessageResponse> {
    MessageAsyncPromiseStage(
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void then(
            Consumer<String> onEachLine,
            Consumer<MessageResponse> afterAll
    ) {
        this.httpExecutor.executeAsyncWithPathVariable(
                this.requestBuilder.build(),
                this.threadId,
                onEachLine,
                afterAll
        );
    }
}