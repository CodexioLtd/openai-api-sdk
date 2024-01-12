package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.thread.ThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.CreateThreadRequest;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;

public class CreateThreadStage
        extends ThreadConfigurationStage {
    protected CreateThreadStage(
            ThreadHttpExecutor httpExecutor,
            CreateThreadRequest.Builder requestContext
    ) {
        super(
                httpExecutor,
                requestContext
        );
    }

    public ThreadResponse empty() {
        return this.httpExecutor.execute(this.requestContext.build());
    }

    public ThreadMetaStage creating() {
        return new ThreadMetaStage(
                this.httpExecutor,
                this.requestContext
        );
    }
}