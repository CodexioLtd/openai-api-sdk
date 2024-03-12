package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.http.run.RunnableHttpExecutor;
import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import bg.codexio.ai.openai.api.sdk.message.MessageResult;
import bg.codexio.ai.openai.api.sdk.message.Messages;
import bg.codexio.ai.openai.api.sdk.message.answer.MessageAnswersRetrievalTypeStage;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.function.Consumer;

public class RunnableMessageResult
        extends RunnableConfigurationStage {

    RunnableMessageResult(
            RunnableHttpExecutor httpExecutor,
            RunnableRequest.Builder requestBuilder,
            String threadId
    ) {
        super(
                httpExecutor,
                requestBuilder,
                threadId
        );
    }

    public MessageResult answersImmediate() {
        return Messages.defaults(this.threadId)
                       .and()
                       .respond()
                       .immediate()
                       .answers()
                       .retrieve();
    }

    public void answersAsync(Consumer<MessageResult> result) {
        Messages.defaults(this.threadId)
                .and()
                .respond()
                .async()
                .answers()
                .then(res -> result.accept(new MessageAnswersRetrievalTypeStage(res).retrieve()));
    }

    public void answersAsyncSimply(
            File targetFolder,
            Consumer<String> result
    ) {
        this.answersAsync(res -> res.downloadAsync(
                targetFolder,
                result
        ));
    }

    public Mono<MessageResult> answersReactive() {
        return Messages.defaults(this.threadId)
                       .and()
                       .respond()
                       .reactive()
                       .answers()
                       .handle((response, sink) -> sink.next(response.retrieve()));
    }

    public Mono<String> answersReactiveSimply(File targetFolder) {
        return this.answersReactive()
                   .handle((response, sink) -> sink.next(response.downloadReactive(targetFolder)));
    }
}