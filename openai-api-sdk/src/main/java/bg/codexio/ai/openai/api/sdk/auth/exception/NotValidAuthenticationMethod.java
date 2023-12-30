package bg.codexio.ai.openai.api.sdk.auth.exception;

import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;

/**
 * Usually thrown by {@link SdkAuth} if integral parts
 * of the authentication are missing.
 */
public class NotValidAuthenticationMethod
        extends RuntimeException {

    private NotValidAuthenticationMethod cause;
    private NotValidAuthenticationMethod lastCause;

    public NotValidAuthenticationMethod(String message) {
        super(message);
    }

    /**
     * Used if more than one {@link SdkAuth} methods are provided
     * to collect all of their errors in one exception.
     *
     * @param cause {@link NotValidAuthenticationMethod} thrown by previous
     *              {@link SdkAuth#credentials()} call.
     */
    public void addCause(NotValidAuthenticationMethod cause) {
        if (this.cause == null) {
            this.setCause(cause);
        } else {
            this.lastCause.setCause(cause);
        }

        this.lastCause = cause;
    }

    public Throwable getCause() {
        return this.cause;
    }

    private void setCause(NotValidAuthenticationMethod cause) {
        this.cause = cause;
    }
}
