package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequest;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequestBuilder;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.message.Messages;
import bg.codexio.ai.openai.api.sdk.message.chat.MessageContentStage;

import java.util.Map;

public class ThreadModificationStage<R extends ThreadRequest>
        extends ThreadConfigurationStage<R> {

    private final String threadId;

    ThreadModificationStage(
            DefaultOpenAIHttpExecutor<R, ThreadResponse> httpExecutor,
            ThreadRequestBuilder<R> requestBuilder,
            String threadId
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
        this.threadId = threadId;
    }

    public ThreadModificationStage<R> withMeta(Map<String, String> meta) {
        return new ThreadModificationStage<>(
                this.httpExecutor,
                this.requestBuilder.withMetadata(meta),
                this.threadId
        );
    }

    public ThreadResponse respond() {
        return this.httpExecutor.executeWithPathVariable(
                this.requestBuilder.specificRequestCreator()
                                   .apply(this.requestBuilder),
                this.threadId
        );
    }

    public MessageContentStage chat() {
        return Messages.defaults(this.respond())
                       .and()
                       .chat();
    }
}