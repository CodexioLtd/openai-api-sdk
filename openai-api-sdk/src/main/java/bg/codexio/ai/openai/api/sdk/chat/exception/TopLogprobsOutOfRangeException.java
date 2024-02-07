package bg.codexio.ai.openai.api.sdk.chat.exception;

import static bg.codexio.ai.openai.api.sdk.chat.constant.ExceptionMessageConstants.TOP_LOGPROBS_OUT_OF_RANGE;

public class TopLogprobsOutOfRangeException
        extends RuntimeException {
    public TopLogprobsOutOfRangeException() {
        super(TOP_LOGPROBS_OUT_OF_RANGE);
    }
}