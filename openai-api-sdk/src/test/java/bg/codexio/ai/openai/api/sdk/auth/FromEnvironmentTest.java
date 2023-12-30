package bg.codexio.ai.openai.api.sdk.auth;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.auth.exception.NotValidAuthenticationMethod;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.payload.environment.AvailableEnvironmentVariables.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FromEnvironmentTest {

    @Test
    public void testFactory_shouldReturnApiKey() {
        var auth = spy(FromEnvironment.AUTH);
        doReturn("12345").when(auth)
                         .getEnv(eq(OPENAI_API_KEY.name()));
        doReturn(null).when(auth)
                      .getEnv(eq(OPENAI_ORG_ID.name()));
        doReturn(null).when(auth)
                      .getEnv(eq(OPENAI_API_BASE_URL.name()));

        assertEquals(
                new ApiCredentials(
                        "12345",
                        "",
                        null
                ),
                auth.credentials()
        );
    }

    @Test
    public void testFactory_shouldReturnApiKeyAndOrg() {
        var auth = spy(FromEnvironment.AUTH);
        doReturn("12345").when(auth)
                         .getEnv(eq(OPENAI_API_KEY.name()));
        doReturn("org-id").when(auth)
                          .getEnv(eq(OPENAI_ORG_ID.name()));
        doReturn(null).when(auth)
                      .getEnv(eq(OPENAI_API_BASE_URL.name()));

        assertEquals(
                new ApiCredentials(
                        "12345",
                        "org-id",
                        null
                ),
                auth.credentials()
        );
    }

    @Test
    public void testFactory_shouldReturnApiKeyAndOrgAndBaseUrl() {
        var auth = spy(FromEnvironment.AUTH);
        doReturn("12345").when(auth)
                         .getEnv(eq(OPENAI_API_KEY.name()));
        doReturn("org-id").when(auth)
                          .getEnv(eq(OPENAI_ORG_ID.name()));
        doReturn("http://base-url").when(auth)
                                   .getEnv(eq(OPENAI_API_BASE_URL.name()));

        assertEquals(
                new ApiCredentials(
                        "12345",
                        "org-id",
                        "http://base-url"
                ),
                auth.credentials()
        );
    }

    @Test
    public void testFactory_noApiKey_expectException() {
        var auth = spy(FromEnvironment.AUTH);
        doReturn(null).when(auth)
                      .getEnv(eq(OPENAI_API_KEY.name()));

        var exception = assertThrows(
                NotValidAuthenticationMethod.class,
                auth::credentials
        );
        assertEquals(
                "Environment variable OPENAI_API_KEY not present.",
                exception.getMessage()
        );
    }

    @Test
    public void testGetEnv_notSet_shouldBeNull() {
        assertNull(FromEnvironment.AUTH.getEnv("non-existing"));
    }
}
