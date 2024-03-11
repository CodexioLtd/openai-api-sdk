package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadModificationRequest;
import bg.codexio.ai.openai.api.sdk.Authenticator;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import bg.codexio.ai.openai.api.sdk.thread.modify.ThreadModificationStage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.API_CREDENTIALS;
import static bg.codexio.ai.openai.api.sdk.message.Messages.authenticate;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.MODIFY_THREAD_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_ID;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

public class ThreadModificationStageTest {

    private ThreadModificationStage threadModificationStage;

    @BeforeEach
    void setUp() {
        this.threadModificationStage = new ThreadModificationStage(
                MODIFY_THREAD_HTTP_EXECUTOR,
                ThreadModificationRequest.builder(),
                THREAD_ID
        );
    }

    //    @Test
    //    void testWithMeta_expectCorrectBuilder() {
    //        var nextStage =
    //                this.threadModificationStage.withMeta(METADATA_VAR_ARGS);
    //
    //        assertAll(
    //                () -> assertEquals(
    //                        METADATA_MAP,
    //                        nextStage.requestBuilder.metadata()
    //                ),
    //                () -> assertNotNull(nextStage.httpExecutor),
    //                () -> assertNotNull(nextStage.requestBuilder)
    //        );
    //    }
    //
    //    @Test
    //    void testRespond_expectCorrectResponse() {
    //        executeWithPathVariable(this.threadModificationStage);
    //        var response = this.threadModificationStage.respond();
    //
    //        assertNotNull(response);
    //    }

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
