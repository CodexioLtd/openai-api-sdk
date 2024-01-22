package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequest;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequestBuilder;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;

public abstract class ThreadConfigurationStage<R extends ThreadRequest> {

    protected final DefaultOpenAIHttpExecutor<R, ThreadResponse> httpExecutor;
    protected final ThreadRequestBuilder<R> requestBuilder;

    ThreadConfigurationStage(
            DefaultOpenAIHttpExecutor<R, ThreadResponse> httpExecutor,
            ThreadRequestBuilder<R> requestBuilder
    ) {
        this.httpExecutor = httpExecutor;
        this.requestBuilder = requestBuilder;
    }
}