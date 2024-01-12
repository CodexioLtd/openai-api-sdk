package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.message.MessageHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.message.response.MessageResponse;

public class MessageContentStage
        extends MessageConfigurationStage {
    MessageContentStage(
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

    public MessageResponse withContent(String content) {
        return this.httpExecutor.executeWithPathVariable(
                this.requestBuilder.withContent(content)
                                   .build(),
                this.threadId
        );
    }

    public MessageFileState withContentToFile(String content) {
        return new MessageFileState(
                this.httpExecutor,
                this.requestBuilder.withContent(content),
                this.threadId
        );
    }
}
