package bg.codexio.ai.openai.api.sdk.voice.transcription;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.Authenticator;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.voice.transcription.InternalAssertions.TEST_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.voice.transcription.Transcription.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

public class TranscriptionTest {

    @Test
    public void testThroughHttp_expectExecutor() {
        var nextStage = throughHttp(TEST_EXECUTOR);

        assertEquals(
                TEST_EXECUTOR,
                nextStage.executor
        );
    }

    @Test
    public void testAuthenticate_withCredentials_expectHttpBuilderAndNextStage() {
        var ctx = new HttpExecutorContext(new ApiCredentials("test-key"));
        var httpBuilder = authenticate(ctx);

        assertNotNull(httpBuilder);

        var nextStage = httpBuilder.and();

        assertAll(
                () -> assertNotNull(nextStage.executor),
                () -> assertNotNull(nextStage.requestBuilder)
        );
    }

    @Test
    public void testAuthenticate_withAuth_expectHttpBuilderAndNextStage() {
        var httpBuilder =
                authenticate(FromDeveloper.doPass(new ApiCredentials("test-key")));

        assertNotNull(httpBuilder);

        var nextStage = httpBuilder.and();

        assertAll(
                () -> assertNotNull(nextStage.executor),
                () -> assertNotNull(nextStage.requestBuilder)
        );
    }

    @Test
    public void testSimply_withAuth_expectNextStage() {
        var nextStage = simply(FromDeveloper.doPass(new ApiCredentials("test-key")));

        assertNotNull(nextStage);
    }

    @Test
    public void testDefaults_auto_expectBuilder() {
        var auth = authenticate(FromDeveloper.doPass(new ApiCredentials("test-key")));
        try (var authUtils = mockStatic(Authenticator.class)) {
            authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                     .thenReturn(auth);

            var httpBuilder = defaults();

            assertNotNull(httpBuilder);

            var nextStage = httpBuilder.and();

            assertAll(
                    () -> assertNotNull(nextStage.executor),
                    () -> assertNotNull(nextStage.requestBuilder)
            );
        }
    }

    @Test
    public void testSimply_auto_expectNextStage() {
        var auth = simply(FromDeveloper.doPass(new ApiCredentials("test-key")));
        try (var authUtils = mockStatic(Authenticator.class)) {
            authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                     .thenReturn(auth);

            var nextStage = simply();

            assertNotNull(nextStage);
        }
    }
}
