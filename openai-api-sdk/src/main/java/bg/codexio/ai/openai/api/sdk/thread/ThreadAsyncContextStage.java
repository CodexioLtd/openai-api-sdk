package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequest;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequestBuilder;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

public class ThreadAsyncContextStage<R extends ThreadRequest>
        extends ThreadConfigurationStage<R>
        implements RuntimeExecutor {
    ThreadAsyncContextStage(
            DefaultOpenAIHttpExecutor<R, ThreadResponse> httpExecutor,
            ThreadRequestBuilder<R> requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public ThreadAsyncPromiseStage<R> finishRaw() {
        return new ThreadAsyncPromiseStage<>(
                this.httpExecutor,
                this.requestBuilder
        );
    }
}
