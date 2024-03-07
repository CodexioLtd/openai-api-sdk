package bg.codexio.ai.openai.api.sdk.thread.create;

import bg.codexio.ai.openai.api.http.thread.CreateThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;

public class ThreadRuntimeSelectionStage
        extends ThreadConfigurationStage
        implements RuntimeSelectionStage {

    ThreadRuntimeSelectionStage(
            CreateThreadHttpExecutor httpExecutor,
            ThreadCreationRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    @Override
    public ThreadImmediateContextStage immediate() {
        return new ThreadImmediateContextStage(
                this.httpExecutor,
                this.requestBuilder
        );
    }

    @Override
    public ThreadAsyncContextStage async() {
        return new ThreadAsyncContextStage(
                this.httpExecutor,
                this.requestBuilder
        );
    }

    @Override
    public ThreadReactiveContextStage reactive() {
        return new ThreadReactiveContextStage(
                this.httpExecutor,
                this.requestBuilder
        );
    }
}
