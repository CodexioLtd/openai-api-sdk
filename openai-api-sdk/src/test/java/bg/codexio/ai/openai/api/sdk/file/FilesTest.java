package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.Authenticator;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.file.Files.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

public class FilesTest {

    public static final String TEST_KEY = "test_key";

    @Test
    public void testThroughHttp_expectExecutor() {
        var nextStage =
                throughHttp(InternalAssertions.UPLOAD_FILE_HTTP_EXECUTOR);

        assertEquals(
                InternalAssertions.UPLOAD_FILE_HTTP_EXECUTOR,
                nextStage.executor
        );
    }

    @Test
    public void testAuthenticate_withCredentials_expectHttpBuilderAndNextStage() {
        var ctx = new HttpExecutorContext(new ApiCredentials(TEST_KEY));
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
                Files.authenticate(FromDeveloper.doPass(new ApiCredentials(TEST_KEY)));

        assertNotNull(httpBuilder);

        var nextStage = httpBuilder.and();

        assertAll(
                () -> assertNotNull(nextStage.executor),
                () -> assertNotNull(nextStage.requestBuilder)
        );
    }

    @Test
    public void testDefaults_auto_expectBuilder() {
        var auth =
                Files.authenticate(FromDeveloper.doPass(new ApiCredentials(TEST_KEY)));
        try (var authUtils = mockStatic(Authenticator.class)) {
            authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                     .thenReturn(auth);

            var httpBuilder = defaults();

            assertNotNull(httpBuilder);

            var nextStage = httpBuilder.and();

            assertAll(
                    () -> assertNotNull(nextStage),
                    () -> assertNotNull(nextStage.requestBuilder)
            );
        }
    }
}
