package bg.codexio.ai.openai.api.http.exception;

public class HttpCallFailedException
        extends RuntimeException {
    private static final String MESSAGE = "HTTP call to %s failed.";

    public HttpCallFailedException(
            String url,
            Throwable cause
    ) {
        super(
                String.format(
                        MESSAGE,
                        url
                ),
                cause
        );
    }
}
