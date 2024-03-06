package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.http.OpenAIHttpExecutor.ReactiveExecution;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequest;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequestBuilder;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;
import reactor.core.publisher.Mono;

public class ThreadReactiveContextStage<R extends ThreadRequest>
        extends ThreadConfigurationStage<R>
        implements RuntimeExecutor {
    ThreadReactiveContextStage(
            DefaultOpenAIHttpExecutor<R, ThreadResponse> httpExecutor,
            ThreadRequestBuilder<R> requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public ReactiveExecution<ThreadResponse> finishRaw() {
        return this.httpExecutor.executeReactive(this.requestBuilder.specificRequestCreator()
                                                                    .apply(this.requestBuilder.build()));
    }

    public Mono<String> finish() {
        return this.finishRaw()
                   .response()
                   .map(ThreadResponse::id);
    }
}