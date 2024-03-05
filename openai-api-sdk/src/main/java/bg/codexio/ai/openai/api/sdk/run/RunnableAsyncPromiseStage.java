package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.http.run.RunnableHttpExecutor;
import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import bg.codexio.ai.openai.api.payload.run.response.RunnableResponse;

import java.util.function.Consumer;

public class RunnableAsyncPromiseStage
        extends RunnableConfigurationStage {

    RunnableAsyncPromiseStage(
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

    public void then(Consumer<RunnableResponse> afterAll) {
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
            Consumer<RunnableResponse> afterAll
    ) {
        this.httpExecutor.executeAsyncWithPathVariable(
                this.requestBuilder.build(),
                this.threadId,
                onEachLine,
                afterAll
        );
    }
}
