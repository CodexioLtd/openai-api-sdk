package bg.codexio.ai.openai.api.sdk.run.exception;

import static bg.codexio.ai.openai.api.sdk.run.constant.RunnableExceptionMessageConstant.NON_INITIALIZED_RUNNABLE;

public class NonInitializedRunnableException
        extends RuntimeException {
    public NonInitializedRunnableException() {
        super(NON_INITIALIZED_RUNNABLE);
    }
}
