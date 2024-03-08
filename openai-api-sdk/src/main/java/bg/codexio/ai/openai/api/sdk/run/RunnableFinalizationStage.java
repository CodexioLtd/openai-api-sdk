package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.http.run.RunnableHttpExecutor;
import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import bg.codexio.ai.openai.api.payload.run.response.RunnableResponse;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.function.Consumer;

public class RunnableFinalizationStage
        extends RunnableConfigurationStage {

    RunnableFinalizationStage(
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

    public RunnableResultStage immediate() {
        return this.initializeRuntimeSelection()
                   .immediate()
                   .run();
    }

    public void async(Consumer<RunnableResultStage> consumer) {
        this.initializeRuntimeSelection()
            .async()
            .execute()
            .then(response -> consumer.accept(this.initializeRunnableResultStage(response)));
    }

    public void asyncSimply(
            File targetFolder,
            Consumer<String> result
    ) {
        this.initializeRuntimeSelection()
            .async()
            .execute()
            .then(response -> {
                this.initializeToMessageResultAsync(
                        response,
                        result,
                        targetFolder
                );
            });
    }

    public Mono<RunnableResponse> reactive() {
        return this.initializeRuntimeSelection()
                   .reactive()
                   .executeRaw()
                   .response();
    }

    public Mono<String> reactiveSimply(File targetFolder) {
        return this.reactive()
                   .flatMap(run -> this.initializeToMessageResultReactive(
                           run,
                           targetFolder
                   ));
    }

    private RunnableResultStage initializeRunnableResultStage(RunnableResponse run) {
        return new RunnableResultStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId,
                run
        );
    }

    private Mono<String> initializeToMessageResultReactive(
            RunnableResponse run,
            File targetFolder
    ) {
        return this.initializeRunnableResultStage(run)
                   .waitForCompletion()
                   .result()
                   .answersReactiveSimply(targetFolder);
    }

    private void initializeToMessageResultAsync(
            RunnableResponse run,
            Consumer<String> consumer,
            File targetFolder
    ) {
        this.initializeRunnableResultStage(run)
            .waitForCompletion()
            .result()
            .answersAsyncSimply(
                    targetFolder,
                    consumer
            );
    }

    private RunnableRuntimeSelectionStage initializeRuntimeSelection() {
        return new RunnableRuntimeSelectionStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }
}
