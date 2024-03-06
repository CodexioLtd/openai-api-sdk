package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequest;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequestBuilder;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;

import java.util.function.Consumer;

public class ThreadAsyncPromiseStage<R extends ThreadRequest>
        extends ThreadConfigurationStage<R> {
    ThreadAsyncPromiseStage(
            DefaultOpenAIHttpExecutor<R, ThreadResponse> httpExecutor,
            ThreadRequestBuilder<R> requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public void then(Consumer<ThreadResponse> afterAll) {
        this.then(
                x -> {},
                afterAll
        );
    }

    public void onEachLine(Consumer<String> onEachLine) {
        this.then(
                onEachLine,
                x -> {}
        );
    }

    public void then(
            Consumer<String> onEachLine,
            Consumer<ThreadResponse> afterAll
    ) {
        this.httpExecutor.executeAsync(
                this.requestBuilder.specificRequestCreator()
                                   .apply(this.requestBuilder.build()),
                onEachLine,
                afterAll
        );
    }
}
