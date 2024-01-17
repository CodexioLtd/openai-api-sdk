package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.http.run.RunnableHttpExecutor;
import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;

public class RunnableInstructionStage
        extends RunnableConfigurationStage {
    RunnableInstructionStage(
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

    public RunnableAdvancedConfigurationStage instruct(String instruction) {
        return new RunnableAdvancedConfigurationStage(
                this.httpExecutor,
                this.requestBuilder.withAdditionalInstructions(instruction),
                this.threadId
        );
    }
}
