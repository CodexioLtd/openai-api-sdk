package bg.codexio.ai.openai.api.sdk.message.exception;

import static bg.codexio.ai.openai.api.sdk.message.constant.ExceptionMessageConstants.NON_PRESENT_THREAD_ID;

public class NonPresentThreadIdException
        extends RuntimeException {
    public NonPresentThreadIdException() {
        super(NON_PRESENT_THREAD_ID);
    }
}
