package bg.codexio.ai.openai.api.http.exception;

public class UnparseableRequestException
        extends RuntimeException {

    private static final String MESSAGE =
            "Request could not be converted to " + "JSON.";

    public UnparseableRequestException(Throwable cause) {
        super(
                MESSAGE,
                cause
        );
    }

    public UnparseableRequestException() {
        super(MESSAGE);
    }
}
