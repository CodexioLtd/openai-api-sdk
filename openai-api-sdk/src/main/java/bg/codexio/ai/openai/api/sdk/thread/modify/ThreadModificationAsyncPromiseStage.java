package bg.codexio.ai.openai.api.sdk.thread.modify;

import bg.codexio.ai.openai.api.http.thread.ModifyThreadHttpExecutor;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadModificationRequest;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.thread.executor.ThreadAsyncPromiseRuntimeExecutor;

import java.util.function.Consumer;

public class ThreadModificationAsyncPromiseStage
        extends ThreadModificationRuntimeSelectionStage
        implements ThreadAsyncPromiseRuntimeExecutor {

    ThreadModificationAsyncPromiseStage(
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
    public void then(Consumer<ThreadResponse> afterAll) {
        this.then(
                x -> {},
                afterAll
        );
    }

    @Override
    public void onEachLine(Consumer<String> onEachLine) {
        this.then(
                onEachLine,
                x -> {}
        );
    }

    @Override
    public void then(
            Consumer<String> onEachLine,
            Consumer<ThreadResponse> afterAll
    ) {
        this.httpExecutor.executeAsyncWithPathVariable(
                this.requestBuilder.build(),
                this.threadId,
                onEachLine,
                afterAll
        );
    }
}