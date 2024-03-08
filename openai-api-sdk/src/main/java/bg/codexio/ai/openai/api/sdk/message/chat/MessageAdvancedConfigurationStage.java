package bg.codexio.ai.openai.api.sdk.message.chat;

import bg.codexio.ai.openai.api.http.message.MessageHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;

public class MessageAdvancedConfigurationStage
        extends MessageConfigurationStage {

    public MessageAdvancedConfigurationStage(
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

    public MessageFileStage file() {
        return new MessageFileStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }

    public MessageMetaStage meta() {
        return new MessageMetaStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }

    public MessageAssistantStage assistant() {
        return new MessageAssistantStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }

    public MessageRuntimeSelectionStage andRespond() {
        return new MessageRuntimeSelectionStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }
}