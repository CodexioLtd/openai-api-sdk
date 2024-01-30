package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.message.MessageHttpExecutor;
import bg.codexio.ai.openai.api.http.message.RetrieveListMessagesHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.message.response.MessageResponse;

public class MessageActionTypeStage {

    private final MessageHttpExecutor messageHttpExecutor;
    private final RetrieveListMessagesHttpExecutor listMessagesHttpExecutor;
    private final String threadId;

    MessageActionTypeStage(
            MessageHttpExecutor messageHttpExecutor,
            RetrieveListMessagesHttpExecutor listMessagesHttpExecutor,
            String threadId
    ) {
        this.messageHttpExecutor = messageHttpExecutor;
        this.listMessagesHttpExecutor = listMessagesHttpExecutor;
        this.threadId = threadId;
    }

    public MessageContentStage<MessageResponse> chat() {
        return new MessageContentStage<>(
                this.messageHttpExecutor,
                MessageRequest.builder(),
                this.threadId
        );
    }

    public MessageAnswersRetrievalTypeStage respond() {
        return new MessageAnswersRetrievalTypeStage(
                this.listMessagesHttpExecutor,
                this.threadId
        );
    }
}