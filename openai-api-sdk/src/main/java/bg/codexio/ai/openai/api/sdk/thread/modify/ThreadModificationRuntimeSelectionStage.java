package bg.codexio.ai.openai.api.sdk.thread.modify;

import bg.codexio.ai.openai.api.http.thread.ModifyThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadModificationRequest;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;

public class ThreadModificationRuntimeSelectionStage
        extends ThreadModificationConfigurationStage
        implements RuntimeSelectionStage {
    ThreadModificationRuntimeSelectionStage(
            ModifyThreadHttpExecutor httpExecutor,
            ThreadModificationRequest.Builder requestBuilder,
            String threadId
    ) {
        super(
                httpExecutor,
                requestBuilder,
                threadId
        );
    }

    @Override
    public ThreadModificationImmediateContextStage immediate() {
        return new ThreadModificationImmediateContextStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }

    @Override
    public ThreadModificationAsyncContextStage async() {
        return new ThreadModificationAsyncContextStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }

    @Override
    public ThreadModificationReactiveContextStage reactive() {
        return new ThreadModificationReactiveContextStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }
}
