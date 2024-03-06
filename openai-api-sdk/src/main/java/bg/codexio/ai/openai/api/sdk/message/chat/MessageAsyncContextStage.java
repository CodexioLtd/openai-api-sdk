package bg.codexio.ai.openai.api.sdk.message.chat;

import bg.codexio.ai.openai.api.http.message.MessageHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

public class MessageAsyncContextStage
        extends MessageConfigurationStage
        implements RuntimeExecutor {

    MessageAsyncContextStage(
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

    public MessageAsyncPromiseStage finish() {
        return new MessageAsyncPromiseStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }
}