package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.thread.ThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.response.MessageResponse;
import bg.codexio.ai.openai.api.payload.thread.request.CreateThreadRequest;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.message.MessageContentStage;
import bg.codexio.ai.openai.api.sdk.message.Messages;

public class ThreadAdvancedConfiguration
        extends ThreadConfigurationStage {
    ThreadAdvancedConfiguration(
            ThreadHttpExecutor httpExecutor,
            CreateThreadRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public ThreadMetaStage meta() {
        return new ThreadMetaStage(
                this.httpExecutor,
                this.requestBuilder
        );
    }

    public ThreadMessageContentStage message() {
        return new ThreadMessageContentStage(
                this.httpExecutor,
                this.requestBuilder
        );
    }

    public ThreadMessageFileStage file() {
        return new ThreadMessageFileStage(
                this.httpExecutor,
                this.requestBuilder,
                null
        );
    }

    public ThreadResponse andRespond() {
        return this.httpExecutor.execute(this.requestBuilder.build());
    }

    public MessageContentStage<MessageResponse> chat() {
        return Messages.defaults(this.andRespond())
                       .and()
                       .chat();
    }
}