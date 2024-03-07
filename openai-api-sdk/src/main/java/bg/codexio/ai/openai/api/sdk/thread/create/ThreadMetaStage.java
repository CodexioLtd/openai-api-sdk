package bg.codexio.ai.openai.api.sdk.thread.create;

import bg.codexio.ai.openai.api.http.thread.CreateThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;

public class ThreadMetaStage
        extends ThreadConfigurationStage {

    ThreadMetaStage(
            CreateThreadHttpExecutor httpExecutor,
            ThreadCreationRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public ThreadAdvancedConfigurationStage awareOf(String... metadata) {
        return new ThreadAdvancedConfigurationStage(
                this.httpExecutor,
                this.requestBuilder.addMetadata(metadata)
        );
    }
}