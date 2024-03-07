package bg.codexio.ai.openai.api.sdk.thread.executor;

import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

public interface ThreadImmediateRuntimeExecutor
        extends RuntimeExecutor {
    ThreadResponse finishRaw();

    String finish();
}