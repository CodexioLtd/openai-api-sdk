package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequest;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequestBuilder;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;

public class ThreadRuntimeSelectionStage<R extends ThreadRequest>
        extends ThreadConfigurationStage<R>
        implements RuntimeSelectionStage {
    ThreadRuntimeSelectionStage(
            DefaultOpenAIHttpExecutor<R, ThreadResponse> httpExecutor,
            ThreadRequestBuilder<R> requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    @Override
    public ThreadImmediateContextStage<R> immediate() {
        return new ThreadImmediateContextStage<>(
                this.httpExecutor,
                this.requestBuilder
        );
    }

    @Override
    public ThreadAsyncContextStage<R> async() {
        return new ThreadAsyncContextStage<>(
                this.httpExecutor,
                this.requestBuilder
        );
    }

    @Override
    public ThreadReactiveContextStage<R> reactive() {
        return new ThreadReactiveContextStage<>(
                this.httpExecutor,
                this.requestBuilder
        );
    }
}
