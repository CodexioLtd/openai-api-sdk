package bg.codexio.ai.openai.api.sdk.message.chat;

import bg.codexio.ai.openai.api.http.message.MessageHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.message.response.MessageResponse;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

public class MessageImmediateContextStage
        extends MessageConfigurationStage
        implements RuntimeExecutor {
    MessageImmediateContextStage(
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

    public MessageResponse finish() {
        return this.httpExecutor.executeWithPathVariable(
                this.requestBuilder.build(),
                this.threadId
        );
    }
}