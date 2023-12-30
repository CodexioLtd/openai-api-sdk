package bg.codexio.ai.openai.api.http.exception;

public class UnparseableResponseException
        extends RuntimeException {

    private static final String MESSAGE =
            "Response %s cannot be serialized " + "to %s.";

    public UnparseableResponseException(
            String rawResponse,
            Class<?> type,
            Throwable cause
    ) {
        super(
                String.format(
                        MESSAGE,
                        rawResponse,
                        type.getName()
                ),
                cause
        );
    }

}
