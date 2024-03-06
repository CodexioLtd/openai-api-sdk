package bg.codexio.ai.openai.api.sdk.message.chat;

import bg.codexio.ai.openai.api.http.message.MessageHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;

public class MessageRuntimeSelectionStage
        extends MessageConfigurationStage
        implements RuntimeSelectionStage {
    MessageRuntimeSelectionStage(
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

    @Override
    public MessageImmediateContextStage immediate() {
        return new MessageImmediateContextStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }

    @Override
    public MessageAsyncContextStage async() {
        return new MessageAsyncContextStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }

    @Override
    public MessageReactiveContextStage reactive() {
        return new MessageReactiveContextStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }
}