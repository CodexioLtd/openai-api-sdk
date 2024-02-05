package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequest;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadRequestBuilder;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;

public class ThreadCreationStage<R extends ThreadRequest>
        extends ThreadConfigurationStage<R> {

    ThreadCreationStage(
            DefaultOpenAIHttpExecutor<R, ThreadResponse> httpExecutor,
            ThreadRequestBuilder<R> requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    public ThreadResponse empty() {
        return this.httpExecutor.execute(this.requestBuilder.specificRequestCreator()
                                                            .apply(this.requestBuilder.build()));
    }

    public ThreadAdvancedConfigurationStage<R> deepConfigure() {
        return new ThreadAdvancedConfigurationStage<>(
                this.httpExecutor,
                this.requestBuilder
        );
    }
}