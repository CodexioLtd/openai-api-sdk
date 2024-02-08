package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.Authenticator;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.API_CREDENTIALS;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.*;
import static bg.codexio.ai.openai.api.sdk.thread.Threads.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

public class ThreadsTest {

    @Test
    public void testThroughHttp_withCreateExecutor_expectCreateExecutor() {
        var nextStage = throughHttp(CREATE_THREAD_HTTP_EXECUTOR);

        assertEquals(
                CREATE_THREAD_HTTP_EXECUTOR,
                nextStage.httpExecutor
        );
    }

    @Test
    public void testThroughHttp_withEditExecutor_expectEditExecutor() {
        var nextStage = throughHttp(
                MODIFY_THREAD_HTTP_EXECUTOR,
                THREAD_ID
        );

        assertNotNull(nextStage);
    }

    @Test
    public void testAuthenticate_withCredentials_expectNextStage() {
        var ctx = new HttpExecutorContext(new ApiCredentials(API_CREDENTIALS));
        var httpBuilder = authenticate(ctx);

        assertNotNull(httpBuilder);

        var nextStage = httpBuilder.and();

        assertNotNull(nextStage);
    }

    @Test
    public void testAuthenticate_withAuth_expectHttpBuilderAndNextStage() {
        var httpBuilder =
                authenticate(FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS)));

        assertNotNull(httpBuilder);

        var nextStage = httpBuilder.and();

        assertNotNull(nextStage);
    }

    @Test
    public void testDefaults_expectBuilder() {
        var auth =
                authenticate(FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS)));
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
