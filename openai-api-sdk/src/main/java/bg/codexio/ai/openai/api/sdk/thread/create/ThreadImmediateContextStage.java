package bg.codexio.ai.openai.api.sdk.thread.create;

import bg.codexio.ai.openai.api.http.thread.CreateThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.thread.executor.ThreadImmediateRuntimeExecutor;

public class ThreadImmediateContextStage
        extends ThreadConfigurationStage
        implements ThreadImmediateRuntimeExecutor {

    ThreadImmediateContextStage(
            CreateThreadHttpExecutor httpExecutor,
            ThreadCreationRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    @Override
    public ThreadResponse finishRaw() {
        return this.httpExecutor.execute(this.requestBuilder.build());
    }

    @Override
    public String finish() {
        return this.finishRaw()
                   .id();
    }
}
