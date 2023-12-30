package bg.codexio.ai.openai.api.sdk.auth;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FromDeveloperTest {

    @Test
    public void testDoPass_shouldReturnSuppliedCredentials() {
        var auth = FromDeveloper.doPass(new ApiCredentials("test-key"));

        assertEquals(
                new ApiCredentials("test-key"),
                auth.credentials()
        );
    }
}
