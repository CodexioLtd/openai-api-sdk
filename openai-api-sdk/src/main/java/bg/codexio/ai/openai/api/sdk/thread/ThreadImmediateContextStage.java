package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequest;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequestBuilder;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

public class ThreadImmediateContextStage<R extends ThreadRequest>
        extends ThreadConfigurationStage<R>
        implements RuntimeExecutor {
    ThreadImmediateContextStage(
            DefaultOpenAIHttpExecutor<R, ThreadResponse> httpExecutor,
            ThreadRequestBuilder<R> requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public ThreadResponse finishRaw() {
        return this.httpExecutor.execute(this.requestBuilder.specificRequestCreator()
                                                            .apply(this.requestBuilder.build()));
    }

    public String finish() {
        return this.finishRaw()
                   .id();
    }
}
