package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.Authenticator;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestConstantsUtils.API_CREDENTIALS;
import static bg.codexio.ai.openai.api.sdk.assistant.Assistants.*;
import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_HTTP_EXECUTOR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

public class AssistantsTest {

    @Test
    void testThroughHttp_expectExecutor() {
        var nextStage = throughHttp(ASSISTANT_HTTP_EXECUTOR);

        assertEquals(
                ASSISTANT_HTTP_EXECUTOR,
                nextStage.httpExecutor
        );
    }

    @Test
    void testAuthenticate_withCredentials_expectHttpBuilderAndNextStage() {
        var ctx = new HttpExecutorContext(new ApiCredentials(API_CREDENTIALS));
        var httpBuilder = authenticate(ctx);

        assertNotNull(httpBuilder);

        var nextStage = httpBuilder.and();

        assertAll(
                () -> assertNotNull(nextStage.httpExecutor),
                () -> assertNotNull(nextStage.requestBuilder)
        );
    }

    @Test
    void testAuthenticate_withAuth_expectHttpBuilderAndNextStage() {
        var httpBuilder =
                authenticate(FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS)));

        assertNotNull(httpBuilder);

        var nextStage = httpBuilder.and();

        assertAll(
                () -> assertNotNull(nextStage.httpExecutor),
                () -> assertNotNull(nextStage.requestBuilder)
        );
    }

    @Test
    void testDefaults_auto_expectBuilder() {
        var auth =
                Assistants.authenticate(FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS)));
        try (var authUtils = mockStatic(Authenticator.class)) {
            authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                     .thenReturn(auth);

            var httpBuilder = defaults();

            assertNotNull(httpBuilder);

            var nextStage = httpBuilder.and();

            assertAll(
                    () -> assertNotNull(nextStage.httpExecutor),
                    () -> assertNotNull(nextStage.requestBuilder)
            );
        }
    }
}
