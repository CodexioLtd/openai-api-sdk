package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;

public class MessageContentStage<O extends Mergeable<O>>
        extends MessageConfigurationStage<O> {

    MessageContentStage(
            DefaultOpenAIHttpExecutor<MessageRequest, O> httpExecutor,
            MessageRequest.Builder requestBuilder,
            String threadId
    ) {
        super(
                httpExecutor,
                requestBuilder,
                threadId
        );
    }

    public MessageAdvancedConfigurationStage<O> withContent(String content) {
        return new MessageAdvancedConfigurationStage<>(
                this.httpExecutor,
                this.requestBuilder.withContent(content),
                this.threadId
        );
    }
}