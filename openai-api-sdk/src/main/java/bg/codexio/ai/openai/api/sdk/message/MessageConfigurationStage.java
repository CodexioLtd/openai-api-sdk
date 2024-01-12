package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.message.MessageHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;

public abstract class MessageConfigurationStage {

    protected final MessageHttpExecutor httpExecutor;
    protected final MessageRequest.Builder requestBuilder;
    protected final String threadId;

    MessageConfigurationStage(
            MessageHttpExecutor httpExecutor,
            MessageRequest.Builder requestBuilder,
            String threadId
    ) {
        this.httpExecutor = httpExecutor;
        this.requestBuilder = requestBuilder;
        this.threadId = threadId;
    }
}