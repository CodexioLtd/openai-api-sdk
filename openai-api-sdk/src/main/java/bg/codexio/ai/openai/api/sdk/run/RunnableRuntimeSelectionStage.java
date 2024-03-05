package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.http.run.RunnableHttpExecutor;
import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;

public class RunnableRuntimeSelectionStage
        extends RunnableConfigurationStage
        implements RuntimeSelectionStage {
    RunnableRuntimeSelectionStage(
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

    @Override
    public RunnableImmediateContextStage immediate() {
        return new RunnableImmediateContextStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }

    @Override
    public RunnableAsyncContextStage async() {
        return new RunnableAsyncContextStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }

    @Override
    public RunnableReactiveContextStage reactive() {
        return new RunnableReactiveContextStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }
}
