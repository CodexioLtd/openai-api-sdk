package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.message.MessageHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.message.response.MessageCreationResponse;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;

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

    public MessageCreationResponse withContent(String content) {
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

    public MessageCreationResponse answers(ThreadResponse threadResponse) {
        return this.httpExecutor.execute(threadResponse.id());
    }

    public MessageCreationResponse answers(String threadId) {
        return this.httpExecutor.execute(threadId);
    }

    public MessageCreationResponse answers() {
        return this.httpExecutor.execute(this.threadId);
    }
}