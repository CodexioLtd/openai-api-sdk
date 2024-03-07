package bg.codexio.ai.openai.api.sdk.thread.executor;

import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;

import java.util.function.Consumer;

public interface ThreadAsyncPromiseRuntimeExecutor {
    void then(Consumer<ThreadResponse> afterAll);

    void onEachLine(Consumer<String> onEachLine);

    void then(
            Consumer<String> onEachLine,
            Consumer<ThreadResponse> afterAll
    );
}