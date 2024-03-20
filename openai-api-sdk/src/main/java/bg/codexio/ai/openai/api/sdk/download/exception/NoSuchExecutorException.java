package bg.codexio.ai.openai.api.sdk.download.exception;

public class NoSuchExecutorException
        extends RuntimeException {
    public NoSuchExecutorException() {
        super("No such download executor.");
    }
}