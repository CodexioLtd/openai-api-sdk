package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.response.MessageResponse;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequest;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequestBuilder;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.message.MessageContentStage;
import bg.codexio.ai.openai.api.sdk.message.Messages;

public class ThreadAdvancedConfigurationStage<R extends ThreadRequest>
        extends ThreadConfigurationStage<R> {

    ThreadAdvancedConfigurationStage(
            DefaultOpenAIHttpExecutor<R, ThreadResponse> httpExecutor,
            ThreadRequestBuilder<R> requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public ThreadMetaStage<R> meta() {
        return new ThreadMetaStage<>(
                this.httpExecutor,
                this.requestBuilder
        );
    }

    public ThreadMessageContentStage<R> message() {
        return new ThreadMessageContentStage<>(
                this.httpExecutor,
                this.requestBuilder
        );
    }

    public ThreadMessageFileStage<R> file() {
        return new ThreadMessageFileStage<>(
                this.httpExecutor,
                this.requestBuilder,
                null
        );
    }

    public ThreadResponse andRespond() {
        return this.httpExecutor.execute(this.requestBuilder.specificRequestCreator()
                                                            .apply(this.requestBuilder.build()));
    }

    public MessageContentStage<MessageResponse> chat() {
        return Messages.defaults(this.andRespond())
                       .and()
                       .chat();
    }
}