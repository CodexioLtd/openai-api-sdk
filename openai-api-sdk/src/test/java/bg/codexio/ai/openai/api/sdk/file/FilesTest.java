package bg.codexio.ai.openai.api.sdk.file;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.Authenticator;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.API_CREDENTIALS;
import static bg.codexio.ai.openai.api.sdk.file.Files.authenticate;
import static bg.codexio.ai.openai.api.sdk.file.Files.defaults;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

public class FilesTest {

    //    @Test
    //    public void testThroughHttp_withUploadExecutor_expectExecutor() {
    //        var nextStage = throughHttp(UPLOAD_FILE_HTTP_EXECUTOR);
    //
    //        assertEquals(
    //                UPLOAD_FILE_HTTP_EXECUTOR,
    //                nextStage.executor
    //        );
    //    }
    //
    //    @Test
    //    public void
    //    testThroughHttp_withRetrieveExecutorAndFileId_expectExecutor() {
    //        var nextStage = throughHttp(
    //                RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR,
    //                FILE_TEST_ID
    //        );
    //
    //        assertEquals(
    //                RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR,
    //                nextStage.executor
    //        );
    //    }
    //
    //    @Test
    //    public void
    //    testThroughHttp_withRetrieveExecutorAndFileResponse_expectExecutor() {
    //        var nextStage = throughHttp(
    //                RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR,
    //                FILE_RESPONSE
    //        );
    //
    //        assertEquals(
    //                RETRIEVE_FILE_CONTENT_HTTP_EXECUTOR,
    //                nextStage.executor
    //        );
    //    }

    @Test
    public void testAuthenticate_withCredentials_expectHttpBuilderAndNextStage() {
        var ctx = new HttpExecutorContext(new ApiCredentials(API_CREDENTIALS));
        var httpBuilder = authenticate(ctx);

        assertNotNull(httpBuilder);

        var nextStage = httpBuilder.and();

        assertNotNull(nextStage);
    }

    @Test
    public void testAuthenticate_withAuth_expectHttpBuilderAndNextStage() {
        var httpBuilder = Files.authenticate(FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS)));

        assertNotNull(httpBuilder);

        var nextStage = httpBuilder.and();

        assertNotNull(nextStage);
    }

    @Test
    public void testDefaults_auto_expectBuilder() {
        var auth = Files.authenticate(FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS)));
        try (var authUtils = mockStatic(Authenticator.class)) {
            authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                     .thenReturn(auth);

            var httpBuilder = defaults();

            assertNotNull(httpBuilder);

            var nextStage = httpBuilder.and();

            assertNotNull(nextStage);
        }
    }
}