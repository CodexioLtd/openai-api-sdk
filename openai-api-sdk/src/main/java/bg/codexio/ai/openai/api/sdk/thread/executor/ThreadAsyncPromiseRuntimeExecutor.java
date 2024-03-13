package bg.codexio.ai.openai.api.sdk.thread.executor;

import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.AsyncPromiseStage;

public interface ThreadAsyncPromiseRuntimeExecutor
        extends AsyncPromiseStage<ThreadResponse> {}