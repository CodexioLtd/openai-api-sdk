package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequest;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequestBuilder;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.message.Messages;
import bg.codexio.ai.openai.api.sdk.message.chat.MessageContentStage;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

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

    public MessageContentStage chatImmediate() {
        return Messages.defaults(this.andRespond()
                                     .immediate()
                                     .finish())
                       .and()
                       .chat();
    }

    public void chatAsync(Consumer<MessageContentStage> consumer) {
        this.andRespond()
            .async()
            .finishRaw()
            .then(threadId -> consumer.accept(Messages.defaults(threadId)
                                                      .and()
                                                      .chat()));
    }

    public Mono<MessageContentStage> chatReactive() {
        return this.andRespond()
                   .reactive()
                   .finish()
                   .handle((response, sink) -> sink.next(Messages.defaults(response)
                                                                 .and()
                                                                 .chat()));
    }

    public ThreadRuntimeSelectionStage<R> andRespond() {
        return new ThreadRuntimeSelectionStage<>(
                this.httpExecutor,
                this.requestBuilder
        );
    }
}