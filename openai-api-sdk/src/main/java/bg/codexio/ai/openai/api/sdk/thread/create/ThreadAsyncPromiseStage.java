package bg.codexio.ai.openai.api.sdk.thread.create;

import bg.codexio.ai.openai.api.http.thread.CreateThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.thread.executor.ThreadAsyncPromiseRuntimeExecutor;

import java.util.function.Consumer;

public class ThreadAsyncPromiseStage
        extends ThreadConfigurationStage
        implements ThreadAsyncPromiseRuntimeExecutor {

    ThreadAsyncPromiseStage(
            CreateThreadHttpExecutor httpExecutor,
            ThreadCreationRequest.Builder requestBuilder
    ) {
        super(
                httpExecutor,
                requestBuilder
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void then(
            Consumer<String> onEachLine,
            Consumer<ThreadResponse> afterAll
    ) {
        this.httpExecutor.executeAsync(
                this.requestBuilder.build(),
                onEachLine,
                afterAll
        );
    }
}
