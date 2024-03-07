package bg.codexio.ai.openai.api.sdk.thread.executor;

import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

public interface ThreadAsyncRuntimeExecutor
        extends RuntimeExecutor {

    ThreadAsyncPromiseRuntimeExecutor finishRaw();
}