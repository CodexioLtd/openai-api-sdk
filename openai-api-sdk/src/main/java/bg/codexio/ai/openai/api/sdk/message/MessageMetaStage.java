package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.message.MessageHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.message.response.MessageResponse;

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

    public MessageResponse awareOf(String... metadata) {
        return this.httpExecutor.executeWithPathVariable(
                this.requestBuilder.addMetadata(metadata)
                                   .build(),
                this.threadId
        );
    }
}