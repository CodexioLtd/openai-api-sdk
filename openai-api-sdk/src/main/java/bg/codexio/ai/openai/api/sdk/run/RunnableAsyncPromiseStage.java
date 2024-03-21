package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.http.run.RunnableHttpExecutor;
import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import bg.codexio.ai.openai.api.payload.run.response.RunnableResponse;
import bg.codexio.ai.openai.api.sdk.AsyncPromiseStage;

import java.util.function.Consumer;

public class RunnableAsyncPromiseStage
        extends RunnableConfigurationStage
        implements AsyncPromiseStage<RunnableResponse> {

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

    /**
     * {@inheritDoc}
     */
    @Override
    public void then(
            Consumer<String> onEachLine,
            Consumer<RunnableResponse> afterAll
    ) {
        this.httpExecutor.async()
                         .executeWithPathVariable(
                                 this.requestBuilder.build(),
                                 this.threadId,
                                 onEachLine,
                                 afterAll
                         );
    }
}
