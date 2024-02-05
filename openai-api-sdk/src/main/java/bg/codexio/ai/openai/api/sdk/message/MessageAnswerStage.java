package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.Mergeable;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;

public class MessageAnswerStage<O extends Mergeable<O>>
        extends MessageConfigurationStage<O> {

    MessageAnswerStage(
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

    public O answersRaw(ThreadResponse threadResponse) {
        return this.httpExecutor.executeWithPathVariables(threadResponse.id());
    }

    public O answersRaw(String threadId) {
        return this.httpExecutor.executeWithPathVariables(threadId);
    }

    public O answersRaw() {
        return this.httpExecutor.executeWithPathVariables(this.threadId);
    }
}