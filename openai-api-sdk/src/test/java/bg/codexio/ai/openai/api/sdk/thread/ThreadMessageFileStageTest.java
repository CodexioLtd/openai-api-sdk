package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import bg.codexio.ai.openai.api.sdk.Authenticator;
import bg.codexio.ai.openai.api.sdk.MockedFileSimplifiedUtils;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import bg.codexio.ai.openai.api.sdk.file.FileSimplified;
import bg.codexio.ai.openai.api.sdk.file.Files;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestConstantsUtils.*;
import static bg.codexio.ai.openai.api.sdk.file.InternalAssertions.FILE_RESPONSE;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

public class ThreadMessageFileStageTest {

    private ThreadMessageFileStage<ThreadCreationRequest> threadMessageFileStage;

    @BeforeEach
    void setUp() {
        this.threadMessageFileStage = new ThreadMessageFileStage<>(
                CREATE_THREAD_HTTP_EXECUTOR,
                THREAD_CREATION_REQUEST_BUILDER,
                THREAD_MESSAGE_CONTENT
        );
    }

    @Test
    void testFeed_withFile_expectCorrectResponse() {
        var auth = Files.authenticate(FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS)));
        try (
                var authUtils = mockStatic(Authenticator.class);
                var filesSimplified = mockStatic(FileSimplified.class)
        ) {
            MockedFileSimplifiedUtils.mockFileSimplified(
                    authUtils,
                    auth,
                    filesSimplified
            );

            execute(this.threadMessageFileStage);
            var response = this.threadMessageFileStage.feed(FILE);

            assertNotNull(response);
        }
    }

    @Test
    void testFeed_withFileResponse_expectCorrectResponse() {
        execute(this.threadMessageFileStage);
        var response = this.threadMessageFileStage.feed(FILE_RESPONSE);

        assertNotNull(response);
    }

    @Test
    void testFeed_withFileIdVarArgs_expectCorrectResponse() {
        execute(this.threadMessageFileStage);
        var response = this.threadMessageFileStage.feed(FILE_IDS_VAR_ARGS);

        assertNotNull(response);
    }

    @Test
    void testAttach_withFile_expectCorrectBuilder() {
        var auth = Files.authenticate(FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS)));
        try (
                var authUtils = mockStatic(Authenticator.class);
                var filesSimplified = mockStatic(FileSimplified.class)
        ) {
            MockedFileSimplifiedUtils.mockFileSimplified(
                    authUtils,
                    auth,
                    filesSimplified
            );

            execute(this.threadMessageFileStage);
            var response = this.threadMessageFileStage.attach(FILE);

            assertNotNull(response);
        }
    }

    @Test
    void testAttach_withFileResponse_expectCorrectBuilder() {
        execute(this.threadMessageFileStage);
        var response = this.threadMessageFileStage.attach(FILE_RESPONSE);

        assertNotNull(response);
    }

    @Test
    void testAttach_withFileIdVarArgs_expectCorrectResponse() {
        execute(this.threadMessageFileStage);
        var response = this.threadMessageFileStage.attach(FILE_IDS_VAR_ARGS);

        assertNotNull(response);
    }
}
