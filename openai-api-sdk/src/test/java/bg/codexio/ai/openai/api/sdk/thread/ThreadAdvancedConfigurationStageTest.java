package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import bg.codexio.ai.openai.api.sdk.Authenticator;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.SharedConstantsUtils.API_CREDENTIALS;
import static bg.codexio.ai.openai.api.sdk.message.Messages.authenticate;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

public class ThreadAdvancedConfigurationStageTest {

    private ThreadAdvancedConfigurationStage<ThreadCreationRequest> threadAdvancedConfigurationStage;

    @BeforeEach
    void setUp() {
        this.threadAdvancedConfigurationStage =
                new ThreadAdvancedConfigurationStage<>(
                CREATE_THREAD_HTTP_EXECUTOR,
                THREAD_CREATION_REQUEST_BUILDER
        );
    }

    @Test
    void testMeta_expectCorrectBuilder() {
        var nextStage = this.threadAdvancedConfigurationStage.meta();
        this.previousValuesRemainsUnchanged(nextStage);
        createThreadRequestIsPopulated(nextStage);
    }

    @Test
    void testMessage_expectCorrectBuilder() {
        var nextStage = this.threadAdvancedConfigurationStage.message();
        this.previousValuesRemainsUnchanged(nextStage);
        createThreadRequestIsPopulated(nextStage);
    }

    @Test
    void testFile_expectCorrectBuilder() {
        var nextStage = this.threadAdvancedConfigurationStage.file();
        this.previousValuesRemainsUnchanged(nextStage);
        createThreadRequestIsPopulated(nextStage);
    }

    @Test
    void testAndRespond_expectCorrectResponse() {
        execute(this.threadAdvancedConfigurationStage);
        var response = this.threadAdvancedConfigurationStage.andRespond();

        assertEquals(
                THREAD_RESPONSE,
                response
        );
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

            var nextStage = this.threadAdvancedConfigurationStage.chat();

            assertNotNull(nextStage);
        }
    }

    private void previousValuesRemainsUnchanged(ThreadConfigurationStage<ThreadCreationRequest> nextStage) {
        assertAll(
                () -> messagesRemainsUnchanged(
                        this.threadAdvancedConfigurationStage,
                        nextStage
                ),
                () -> metadataRemainsUnchanged(
                        this.threadAdvancedConfigurationStage,
                        nextStage
                )
        );
    }
}