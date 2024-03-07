package bg.codexio.ai.openai.api.sdk.thread.create;

import bg.codexio.ai.openai.api.http.thread.CreateThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;

public class ThreadCreationStage
        extends ThreadConfigurationStage {

    public ThreadCreationStage(
            CreateThreadHttpExecutor httpExecutor,
            ThreadCreationRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public ThreadRuntimeSelectionStage empty() {
        return new ThreadRuntimeSelectionStage(
                this.httpExecutor,
                this.requestBuilder
        );
    }

    public ThreadAdvancedConfigurationStage deepConfigure() {
        return new ThreadAdvancedConfigurationStage(
                this.httpExecutor,
                this.requestBuilder
        );
    }
}