package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.http.run.RunnableHttpExecutor;
import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import bg.codexio.ai.openai.api.payload.run.response.RunnableResponse;
import bg.codexio.ai.openai.api.sdk.message.MessageResult;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class RunnableAdvancedConfigurationStage
        extends RunnableConfigurationStage {

    RunnableAdvancedConfigurationStage(
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

    public RunnableMetaStage meta() {
        return new RunnableMetaStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }

    public AIModelStage aiModel() {
        return new AIModelStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }

    public RunnableInstructionStage instruction() {
        return new RunnableInstructionStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }

    public RunnableResultStage messaging() {
        return new RunnableResultStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId,
                null
        );
    }

    public RunnableRuntimeSelectionStage andRespond() {
        return new RunnableRuntimeSelectionStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }

    public RunnableMessageResult result() {
        return new RunnableMessageResult(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }

    public RunnableResultStage finishImmediate() {
        return this.andRespond()
                   .immediate()
                   .run();
    }

    public void finishAsync(Consumer<RunnableResultStage> consumer) {
        this.andRespond()
            .async()
            .execute()
            .then(response -> consumer.accept(this.initializeRunnableResultStage(response)));
    }

    public void finishAsyncSimply(
            File targetFolder,
            Consumer<String> result
    ) {
        this.andRespond()
            .async()
            .execute()
            .then(response -> {
                try {
                    result.accept(this.initializeToMessageResult(response)
                                      .download(targetFolder));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    public Mono<RunnableResponse> finishReactive() {
        return this.andRespond()
                   .reactive()
                   .executeRaw()
                   .response();
    }

    public Mono<String> finishReactiveSimply(File targetFolder) {
        return this.finishReactive()
                   .handle((response, sink) -> {
                       try {
                           sink.next(this.initializeToMessageResult(response)
                                         .download(targetFolder));
                       } catch (IOException e) {
                           sink.error(new RuntimeException(e));
                       }
                   });
    }

    private RunnableResultStage initializeRunnableResultStage(RunnableResponse run) {
        return new RunnableResultStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId,
                run
        );
    }

    private MessageResult initializeToMessageResult(RunnableResponse run) {
        return this.initializeRunnableResultStage(run)
                   .waitForCompletion()
                   .result()
                   .answers();
    }
}