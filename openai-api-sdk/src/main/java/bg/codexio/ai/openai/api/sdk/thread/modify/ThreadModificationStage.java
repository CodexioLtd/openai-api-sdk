package bg.codexio.ai.openai.api.sdk.thread.modify;

import bg.codexio.ai.openai.api.http.thread.ModifyThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadModificationRequest;
import bg.codexio.ai.openai.api.sdk.thread.ThreadChatStage;

public class ThreadModificationStage
        extends ThreadModificationConfigurationStage {

    public ThreadModificationStage(
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

    public ThreadModificationStage withMeta(String... metadata) {
        return new ThreadModificationStage(
                this.httpExecutor,
                this.requestBuilder.addMetadata(metadata),
                this.threadId
        );
    }

    public ThreadModificationRuntimeSelectionStage andRespond() {
        return new ThreadModificationRuntimeSelectionStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }

    public ThreadChatStage chat() {
        return new ThreadChatStage(this.initializeModificationRuntimeSelection());
    }

    public ThreadModificationRuntimeSelectionStage initializeModificationRuntimeSelection() {
        return new ThreadModificationRuntimeSelectionStage(
                this.httpExecutor,
                this.requestBuilder,
                this.threadId
        );
    }
}