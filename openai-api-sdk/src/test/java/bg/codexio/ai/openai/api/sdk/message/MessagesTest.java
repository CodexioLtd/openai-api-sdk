package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.Authenticator;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.*;
import static bg.codexio.ai.openai.api.sdk.message.Messages.authenticate;
import static bg.codexio.ai.openai.api.sdk.message.Messages.defaults;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

public class MessagesTest {

    @Test
    public void testAuthenticate_withCredentialsAndThreadId_expectNextStage() {
        var ctx = new HttpExecutorContext(new ApiCredentials(API_CREDENTIALS));
        var httpBuilder = authenticate(
                ctx,
                THREAD_ID
        );

        assertNotNull(httpBuilder);

        var nextStage = httpBuilder.and();

        assertNotNull(nextStage);
    }

    @Test
    public void testAuthenticate_withCredentialsAndThreadResponse_expectNextStage() {
        var ctx = new HttpExecutorContext(new ApiCredentials(API_CREDENTIALS));
        var httpBuilder = authenticate(
                ctx,
                THREAD_RESPONSE
        );

        assertNotNull(httpBuilder);

        var nextStage = httpBuilder.and();

        assertNotNull(nextStage);
    }

    @Test
    public void testAuthenticate_withAuthAndThreadId_expectHttpBuilderAndNextStage() {
        var httpBuilder = authenticate(
                FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS)),
                THREAD_ID
        );

        assertNotNull(httpBuilder);

        var nextStage = httpBuilder.and();

        assertNotNull(nextStage);
    }

    @Test
    public void testAuthenticate_withAuthAndThreadResponse_expectHttpBuilderAndNextStage() {
        var httpBuilder = authenticate(
                FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS)),
                THREAD_RESPONSE
        );

        assertNotNull(httpBuilder);

        var nextStage = httpBuilder.and();

        assertNotNull(nextStage);
    }

    @Test
    public void testDefaults_withThreadId_expectBuilder() {
        var auth = authenticate(
                FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS)),
                THREAD_ID
        );
        try (var authUtils = mockStatic(Authenticator.class)) {
            authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                     .thenReturn(auth);

            var httpBuilder = defaults(THREAD_ID);

            assertNotNull(httpBuilder);

            var nextStage = httpBuilder.and();

            assertNotNull(nextStage);
        }
    }

    @Test
    public void testDefaults_withThreadResponse_expectBuilder() {
        var auth = authenticate(
                FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS)),
                THREAD_RESPONSE
        );
        try (var authUtils = mockStatic(Authenticator.class)) {
            authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                     .thenReturn(auth);

            var httpBuilder = defaults(THREAD_RESPONSE);

            assertNotNull(httpBuilder);

            var nextStage = httpBuilder.and();

            assertNotNull(nextStage);
        }
    }
}