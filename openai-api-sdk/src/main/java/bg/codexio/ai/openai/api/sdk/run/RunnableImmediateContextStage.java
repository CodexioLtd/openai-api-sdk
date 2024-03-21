package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.http.run.RunnableHttpExecutor;
import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import bg.codexio.ai.openai.api.payload.run.response.RunnableResponse;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

public class RunnableImmediateContextStage
        extends RunnableConfigurationStage
        implements RuntimeExecutor {

    RunnableImmediateContextStage(
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

    public RunnableResponse executeRaw() {
        return this.httpExecutor.immediate()
                                .executeWithPathVariable(
                                        this.requestBuilder.build(),
                                        this.threadId
                                );
    }

    public String execute() {
        return this.executeRaw()
                   .id();
    }

    public RunnableResultStage run() {
        return new RunnableResultStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId,
                this.executeRaw()
        );
    }
}