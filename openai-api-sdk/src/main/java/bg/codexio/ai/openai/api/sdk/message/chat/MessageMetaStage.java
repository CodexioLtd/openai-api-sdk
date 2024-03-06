package bg.codexio.ai.openai.api.sdk.message.chat;

import bg.codexio.ai.openai.api.http.message.MessageHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;

public class MessageMetaStage
        extends MessageConfigurationStage {

    MessageMetaStage(
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

    public MessageAdvancedConfigurationStage awareOf(String... metadata) {
        return new MessageAdvancedConfigurationStage(
                this.httpExecutor,
                this.requestBuilder.addMetadata(metadata),
                this.threadId
        );
    }
}