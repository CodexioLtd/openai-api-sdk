package bg.codexio.ai.openai.api.sdk;

import bg.codexio.ai.openai.api.sdk.auth.FromEnvironment;
import bg.codexio.ai.openai.api.sdk.auth.FromJson;
import bg.codexio.ai.openai.api.sdk.auth.exception.NotValidAuthenticationMethod;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticatorTest {

    @Test
    public void testAutoAuthenticate_shouldSelectFirst() {
        var auth = Authenticator.autoAuthenticate(x -> x);

        assertInstanceOf(
                FromEnvironment.class,
                auth
        );
    }

    @Test
    public void testAutoAuthenticate_shouldFromLambda() {
        var auth = Authenticator.autoAuthenticate(x -> {
            if (x instanceof FromJson) {
                return x;
            }

            throw new NotValidAuthenticationMethod("non-valid");
        });

        assertInstanceOf(
                FromJson.class,
                auth
        );
    }

    @Test
    public void testAutoAuthenticate_expectException() {
        var exception = assertThrows(
                NotValidAuthenticationMethod.class,
                () -> Authenticator.autoAuthenticate(x -> {
                    throw new NotValidAuthenticationMethod(
                            "non-valid " + x.getClass()
                                            .getSimpleName());
                })
        );

        System.out.println(exception);

        var firstCause = exception.getCause();
        var secondCause = firstCause.getCause();

        System.out.println(firstCause);
        System.out.println(secondCause);

        assertAll(
                () -> assertInstanceOf(
                        NotValidAuthenticationMethod.class,
                        firstCause
                ),
                () -> assertEquals(
                        "non-valid FromEnvironment",
                        firstCause.getMessage()
                ),
                () -> assertInstanceOf(
                        NotValidAuthenticationMethod.class,
                        secondCause
                ),
                () -> assertEquals(
                        "non-valid FromJson",
                        secondCause.getMessage()
                )
        );
    }
}
