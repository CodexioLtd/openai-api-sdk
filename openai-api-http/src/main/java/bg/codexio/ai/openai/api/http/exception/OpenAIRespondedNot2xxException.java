package bg.codexio.ai.openai.api.http.exception;

import bg.codexio.ai.openai.api.payload.error.ErrorResponseHolder;

public class OpenAIRespondedNot2xxException
        extends RuntimeException {

    private final ErrorResponseHolder errorHolder;
    private final int httpStatusCode;

    public OpenAIRespondedNot2xxException(
            ErrorResponseHolder errorHolder,
            int httpStatusCode
    ) {
        super(String.format(
                "HTTP status: %d. Response: %s",
                httpStatusCode,
                errorHolder
        ));

        this.errorHolder = errorHolder;
        this.httpStatusCode = httpStatusCode;
    }

    public ErrorResponseHolder getErrorHolder() {
        return errorHolder;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
