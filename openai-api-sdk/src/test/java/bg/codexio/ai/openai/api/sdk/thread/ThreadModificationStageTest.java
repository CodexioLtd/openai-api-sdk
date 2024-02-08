package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadModificationRequest;
import bg.codexio.ai.openai.api.sdk.Authenticator;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.API_CREDENTIALS;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.METADATA_MAP;
import static bg.codexio.ai.openai.api.sdk.message.Messages.authenticate;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

public class ThreadModificationStageTest {

    private ThreadModificationStage<ThreadModificationRequest> threadModificationStage;

    @BeforeEach
    void setUp() {
        this.threadModificationStage = new ThreadModificationStage<>(
                MODIFY_THREAD_HTTP_EXECUTOR,
                THREAD_MODIFICATION_REQUEST_THREAD_REQUEST_BUILDER,
                THREAD_ID
        );
    }

    @Test
    void testWithMeta_expectCorrectBuilder() {
        var nextStage = this.threadModificationStage.withMeta(METADATA_MAP);

        assertAll(
                () -> assertEquals(
                        METADATA_MAP,
                        nextStage.requestBuilder.metadata()
                ),
                () -> assertNotNull(nextStage.httpExecutor),
                () -> assertNotNull(nextStage.requestBuilder)
        );
    }

    @Test
    void testRespond_expectCorrectResponse() {
        executeWithPathVariable(this.threadModificationStage);
        var response = this.threadModificationStage.respond();

        assertNotNull(response);
    }

    @Test
    void testChat_expectCorrectBuilder() {
        var auth = authenticate(
                FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS)),
                THREAD_ID
        );
        try (var authUtils = mockStatic(Authenticator.class)) {
            authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                     .thenReturn(auth);

            var nextStage = this.threadModificationStage.chat();

            assertNotNull(nextStage);
        }
    }
}
