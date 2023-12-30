package bg.codexio.ai.openai.api.sdk;

import bg.codexio.ai.openai.api.sdk.auth.FromEnvironment;
import bg.codexio.ai.openai.api.sdk.auth.FromJson;
import bg.codexio.ai.openai.api.sdk.auth.SdkAuth;
import bg.codexio.ai.openai.api.sdk.auth.exception.NotValidAuthenticationMethod;

import java.util.List;
import java.util.function.Function;

public final class Authenticator {

    /**
     * Authentication methods it will be tested against,
     * when using {@link #autoAuthenticate(Function)} ()}.
     */
    public static final List<SdkAuth> DEFAULT_AUTHENTICATIONS = List.of(
            FromEnvironment.AUTH,
            FromJson.AUTH
    );

    private Authenticator() {
    }

    public static <T> T autoAuthenticate(Function<SdkAuth, T> authDelegate) {
        var error = new NotValidAuthenticationMethod(
                "None of the default " + "authentication " + "methods "
                        + "worked.");
        for (var auth : DEFAULT_AUTHENTICATIONS) {
            try {
                return authDelegate.apply(auth);
            } catch (NotValidAuthenticationMethod e) {
                error.addCause(e);
            }
        }

        throw error;
    }
}
