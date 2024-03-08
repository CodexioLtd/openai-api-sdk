package bg.codexio.ai.openai.api.sdk.thread.create;

import bg.codexio.ai.openai.api.http.thread.CreateThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import bg.codexio.ai.openai.api.sdk.thread.ThreadChatStage;

public class ThreadAdvancedConfigurationStage
        extends ThreadConfigurationStage {


    public ThreadAdvancedConfigurationStage(
            CreateThreadHttpExecutor httpExecutor,
            ThreadCreationRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public ThreadMetaStage meta() {
        return new ThreadMetaStage(
                this.httpExecutor,
                this.requestBuilder
        );
    }

    public ThreadMessageContentStage message() {
        return new ThreadMessageContentStage(
                this.httpExecutor,
                this.requestBuilder
        );
    }

    public ThreadMessageFileStage file() {
        return new ThreadMessageFileStage(
                this.httpExecutor,
                this.requestBuilder,
                null
        );
    }

    public ThreadChatStage chat() {
        return new ThreadChatStage(this.initializedRuntimeSelection());
    }

    public ThreadRuntimeSelectionStage andRespond() {
        return new ThreadRuntimeSelectionStage(
                this.httpExecutor,
                this.requestBuilder
        );
    }

    private ThreadRuntimeSelectionStage initializedRuntimeSelection() {
        return new ThreadRuntimeSelectionStage(
                this.httpExecutor,
                this.requestBuilder
        );
    }
}