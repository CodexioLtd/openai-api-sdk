package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequest;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequestBuilder;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;

public class ThreadMetaStage<R extends ThreadRequest>
        extends ThreadConfigurationStage<R> {

    ThreadMetaStage(
            DefaultOpenAIHttpExecutor<R, ThreadResponse> httpExecutor,
            ThreadRequestBuilder<R> requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public ThreadAdvancedConfigurationStage<R> awareOf(String... metadata) {
        return new ThreadAdvancedConfigurationStage<>(
                this.httpExecutor,
                this.requestBuilder.addMetadata(metadata)
        );
    }
}