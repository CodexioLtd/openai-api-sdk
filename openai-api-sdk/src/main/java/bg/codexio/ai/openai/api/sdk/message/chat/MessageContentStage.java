package bg.codexio.ai.openai.api.sdk.message.chat;

import bg.codexio.ai.openai.api.http.message.MessageHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;

public class MessageContentStage
        extends MessageConfigurationStage {

    public MessageContentStage(
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

    public MessageAdvancedConfigurationStage withContent(String content) {
        return new MessageAdvancedConfigurationStage(
                this.httpExecutor,
                this.requestBuilder.withContent(content),
                this.threadId
        );
    }
}