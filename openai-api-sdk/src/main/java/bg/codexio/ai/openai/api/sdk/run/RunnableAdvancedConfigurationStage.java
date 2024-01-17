package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.http.run.RunnableHttpExecutor;
import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import bg.codexio.ai.openai.api.payload.run.response.RunnableResponse;

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

    public AiModelStage aiModel() {
        return new AiModelStage(
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

    public RunnableResponse andRespond() {
        return this.httpExecutor.executeWithPathVariable(
                this.requestBuilder.build(),
                this.threadId
        );
    }
}