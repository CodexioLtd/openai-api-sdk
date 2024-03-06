package bg.codexio.ai.openai.api.sdk.message.chat;

import bg.codexio.ai.openai.api.http.message.MessageHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.sdk.message.DefaultMessageConfigurationStage;

public class MessageConfigurationStage
        extends DefaultMessageConfigurationStage {

    protected final MessageHttpExecutor httpExecutor;

    protected final MessageRequest.Builder requestBuilder;

    protected MessageConfigurationStage(
            MessageHttpExecutor httpExecutor,
            MessageRequest.Builder requestBuilder,
            String threadId
    ) {
        super(threadId);
        this.requestBuilder = requestBuilder;
        this.httpExecutor = httpExecutor;
    }
}
