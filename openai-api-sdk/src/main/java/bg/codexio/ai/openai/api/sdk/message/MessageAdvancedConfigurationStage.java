package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;

public class MessageAdvancedConfigurationStage<O extends Mergeable<O>>
        extends MessageConfigurationStage<O> {
    MessageAdvancedConfigurationStage(
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

    public MessageFileStage<O> file() {
        return new MessageFileStage<>(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }

    public MessageMetaStage<O> meta() {
        return new MessageMetaStage<>(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }

    public MessageAssistantStage<O> assistant() {
        return new MessageAssistantStage<O>(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }

    public O andRespond() {
        return this.httpExecutor.executeWithPathVariable(
                this.requestBuilder.build(),
                this.threadId
        );
    }

}
