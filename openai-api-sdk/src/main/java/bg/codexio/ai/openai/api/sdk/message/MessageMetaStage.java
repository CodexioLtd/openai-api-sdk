package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;

public class MessageMetaStage<O extends Mergeable<O>>
        extends MessageConfigurationStage<O> {

    MessageMetaStage(
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

    public MessageAdvancedConfigurationStage<O> awareOf(String... metadata) {
        return new MessageAdvancedConfigurationStage<>(
                this.httpExecutor,
                this.requestBuilder.addMetadata(metadata),
                this.threadId
        );
    }
}