package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.Authenticator;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestConstantsUtils.API_CREDENTIALS;
import static bg.codexio.ai.openai.api.sdk.run.InternalAssertions.RUNNABLE_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.run.Runnables.*;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_RESPONSE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

public class RunnablesTest {

    @Test
    void testThroughHttp_withThreadId_expectCorrectBuilder() {
        var nextStage = throughHttp(
                RUNNABLE_HTTP_EXECUTOR,
                THREAD_ID
        );

        assertAll(
                () -> assertEquals(
                        RUNNABLE_HTTP_EXECUTOR,
                        nextStage.httpExecutor
                ),
                () -> assertEquals(
                        THREAD_ID,
                        nextStage.threadId
                )
        );
    }

    @Test
    void testThroughHttp_withThreadResponse_expectCorrectExecutorAndThreadId() {
        var nextStage = throughHttp(
                RUNNABLE_HTTP_EXECUTOR,
                THREAD_RESPONSE
        );

        assertAll(
                () -> assertEquals(
                        RUNNABLE_HTTP_EXECUTOR,
                        nextStage.httpExecutor
                ),
                () -> assertEquals(
                        THREAD_RESPONSE.id(),
                        nextStage.threadId
                )
        );
    }

    @Test
    void testAuthenticate_withCredentialsAndThreadId_expectCorrectBuilderAndThreadIdAndNextStage() {
        var ctx = new HttpExecutorContext(new ApiCredentials(API_CREDENTIALS));
        var httpBuilder = authenticate(
                ctx,
                THREAD_ID
        );

        assertNotNull(httpBuilder);

        var nextStage = httpBuilder.and();

        assertAll(
                () -> assertNotNull(nextStage.requestBuilder),
                () -> assertNotNull(nextStage.threadId),
                () -> assertNotNull(nextStage.httpExecutor)
        );
    }

    @Test
    void testAuthenticate_withCredentialsAndThreadResponse_expectCorrectBuilderAndThreadIdAndNextStage() {
        var ctx = new HttpExecutorContext(new ApiCredentials(API_CREDENTIALS));
        var httpBuilder = authenticate(
                ctx,
                THREAD_RESPONSE
        );

        assertNotNull(httpBuilder);

        var nextStage = httpBuilder.and();

        assertAll(
                () -> assertNotNull(nextStage.requestBuilder),
                () -> assertNotNull(nextStage.threadId),
                () -> assertNotNull(nextStage.httpExecutor)
        );
    }

    @Test
    void testAuthenticate_withAuthAndThreadId_expectCorrectBuilderAndThreadIdAndNextStage() {
        var httpBuilder = authenticate(
                FromDeveloper.doPass(new ApiCredentials((API_CREDENTIALS))),
                THREAD_ID
        );

        assertNotNull(httpBuilder);

        var nextStage = httpBuilder.and();

        assertAll(
                () -> assertNotNull(nextStage.requestBuilder),
                () -> assertNotNull(nextStage.threadId),
                () -> assertNotNull(nextStage.httpExecutor)
        );
    }

    @Test
    void testAuthenticate_withAuthAndThreadResponse_expectCorrectBuilderAndThreadIdAndNextStage() {
        var httpBuilder = authenticate(
                FromDeveloper.doPass(new ApiCredentials((API_CREDENTIALS))),
                THREAD_RESPONSE
        );

        assertNotNull(httpBuilder);

        var nextStage = httpBuilder.and();

        assertAll(
                () -> assertNotNull(nextStage.requestBuilder),
                () -> assertNotNull(nextStage.threadId),
                () -> assertNotNull(nextStage.httpExecutor)
        );
    }

    @Test
    void testDefaults_autoWithThreadId_expectCorrectBuilderAndThreadIdAndNextStage() {
        var auth = Runnables.authenticate(
                FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS)),
                THREAD_ID
        );
        try (var authUtils = mockStatic(Authenticator.class)) {
            authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                     .thenReturn(auth);

            var httpBuilder = defaults(THREAD_ID);

            assertNotNull(httpBuilder);

            var nextStage = httpBuilder.and();

            assertAll(
                    () -> assertNotNull(nextStage.requestBuilder),
                    () -> assertNotNull(nextStage.threadId),
                    () -> assertNotNull(nextStage.httpExecutor)
            );
        }
    }

    @Test
    void testDefaults_autoWithThreadResponse_expectCorrectBuilderAndThreadIdAndNextStage() {
        var auth = Runnables.authenticate(
                FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS)),
                THREAD_RESPONSE
        );
        try (var authUtils = mockStatic(Authenticator.class)) {
            authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                     .thenReturn(auth);

            var httpBuilder = defaults(THREAD_RESPONSE);

            assertNotNull(httpBuilder);

            var nextStage = httpBuilder.and();

            assertAll(
                    () -> assertNotNull(nextStage.requestBuilder),
                    () -> assertNotNull(nextStage.threadId),
                    () -> assertNotNull(nextStage.httpExecutor)
            );
        }
    }
}