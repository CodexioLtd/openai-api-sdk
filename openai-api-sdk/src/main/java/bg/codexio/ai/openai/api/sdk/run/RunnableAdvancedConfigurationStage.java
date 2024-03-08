package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.http.run.RunnableHttpExecutor;
import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;

public class RunnableAdvancedConfigurationStage
        extends RunnableConfigurationStage {

    RunnableAdvancedConfigurationStage(
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

    public RunnableMetaStage meta() {
        return new RunnableMetaStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }

    public AIModelStage aiModel() {
        return new AIModelStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }

    public RunnableInstructionStage instruction() {
        return new RunnableInstructionStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }

    public RunnableResultStage messaging() {
        return new RunnableResultStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId,
                null
        );
    }

    public RunnableRuntimeSelectionStage andRespond() {
        return new RunnableRuntimeSelectionStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }

    public RunnableMessageResult result() {
        return new RunnableMessageResult(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }

    public RunnableFinalizationStage finish() {
        return new RunnableFinalizationStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }
}