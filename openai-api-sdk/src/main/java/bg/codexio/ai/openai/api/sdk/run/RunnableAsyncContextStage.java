package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.http.run.RunnableHttpExecutor;
import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

public class RunnableAsyncContextStage
        extends RunnableConfigurationStage
        implements RuntimeExecutor {

    RunnableAsyncContextStage(
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

    public RunnableAsyncPromiseStage execute() {
        return new RunnableAsyncPromiseStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }
}
