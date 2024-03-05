package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.http.OpenAIHttpExecutor.ReactiveExecution;
import bg.codexio.ai.openai.api.http.run.RunnableHttpExecutor;
import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import bg.codexio.ai.openai.api.payload.run.response.RunnableResponse;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;
import reactor.core.publisher.Mono;

public class RunnableReactiveContextStage
        extends RunnableConfigurationStage
        implements RuntimeExecutor {
    RunnableReactiveContextStage(
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

    public ReactiveExecution<RunnableResponse> executeRaw() {
        return this.httpExecutor.executeReactiveWithPathVariable(
                this.requestBuilder.build(),
                this.threadId
        );
    }

    public Mono<String> execute() {
        return this.executeRaw()
                   .response()
                   .map(RunnableResponse::id);
    }
}