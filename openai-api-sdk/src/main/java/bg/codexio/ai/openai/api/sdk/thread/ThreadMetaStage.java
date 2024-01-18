package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.thread.ThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.CreateThreadRequest;

public class ThreadMetaStage
        extends ThreadConfigurationStage {

    ThreadMetaStage(
            ThreadHttpExecutor httpExecutor,
            CreateThreadRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public ThreadAdvancedConfiguration awareOf(String... metadata) {
        return new ThreadAdvancedConfiguration(
                this.httpExecutor,
                this.requestBuilder.addMetadata(metadata)
        );
    }
}
