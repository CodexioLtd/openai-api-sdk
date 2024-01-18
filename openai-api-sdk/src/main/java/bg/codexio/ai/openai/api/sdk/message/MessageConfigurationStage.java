package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;

public abstract class MessageConfigurationStage<O extends Mergeable<O>> {

    protected final DefaultOpenAIHttpExecutor<MessageRequest, O> httpExecutor;
    protected final MessageRequest.Builder requestBuilder;
    protected final String threadId;

    MessageConfigurationStage(
            DefaultOpenAIHttpExecutor<MessageRequest, O> httpExecutor,
            MessageRequest.Builder requestBuilder,
            String threadId
    ) {
        this.httpExecutor = httpExecutor;
        this.requestBuilder = requestBuilder;
        this.threadId = threadId;
    }
}