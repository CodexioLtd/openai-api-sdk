package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.thread.ThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.CreateThreadRequest;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;

public class CreateThreadStage
        extends ThreadConfigurationStage {
    CreateThreadStage(
            ThreadHttpExecutor httpExecutor,
            CreateThreadRequest.Builder requestContext
    ) {
        super(
                httpExecutor,
                requestContext
        );
    }

    public ThreadResponse empty() {
        return this.httpExecutor.execute(this.requestBuilder.build());
    }

    public ThreadAdvancedConfiguration creating() {
        return new ThreadAdvancedConfiguration(
                this.httpExecutor,
                this.requestBuilder
        );
    }
}