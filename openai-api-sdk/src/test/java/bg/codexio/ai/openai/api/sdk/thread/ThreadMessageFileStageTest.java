package bg.codexio.ai.openai.api.sdk.thread;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import bg.codexio.ai.openai.api.sdk.Authenticator;
import bg.codexio.ai.openai.api.sdk.MockedFileSimplifiedUtils;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import bg.codexio.ai.openai.api.sdk.file.Files;
import bg.codexio.ai.openai.api.sdk.file.upload.FileUploadSimplified;
import bg.codexio.ai.openai.api.sdk.thread.create.ThreadConfigurationStage;
import bg.codexio.ai.openai.api.sdk.thread.create.ThreadMessageFileStage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.*;
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
        this.performFeedWithFile(this.threadMessageFileStage);
    }

    @Test
    void testFeed_withFileAndEmptyContent_expectCorrectResponse() {
        this.initializeStageWithEmptyContent();
        this.performFeedWithFile(this.threadMessageFileStage);
    }

    @Test
    void testFeed_withFileResponse_expectCorrectResponse() {
        this.performFeedWithFileResponse(this.threadMessageFileStage);
    }

    @Test
    void testFeed_withFileResponseAndEmptyContent_expectCorrectResponse() {
        this.initializeStageWithEmptyContent();
        this.performFeedWithFileResponse(this.threadMessageFileStage);
    }

    @Test
    void testFeed_withFileIdVarArgs_expectCorrectResponse() {
        execute(this.threadMessageFileStage);
        var response = this.threadMessageFileStage.feed(FILE_IDS_VAR_ARGS);

        assertNotNull(response);
    }

    @Test
    void testFeed_withFileIdVarArgsAndEmptyContent_expectCorrectResponse() {
        this.initializeStageWithEmptyContent();
        execute(this.threadMessageFileStage);
        var response = this.threadMessageFileStage.feed(FILE_IDS_VAR_ARGS);

        assertNotNull(response);
    }

    @Test
    void testAttach_withFile_expectCorrectBuilder() {
        var auth =
                Files.authenticate(FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS)));
        try (
                var authUtils = mockStatic(Authenticator.class);
                var filesSimplified = mockStatic(FileUploadSimplified.class)
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

    private void performFeedWithFile(ThreadConfigurationStage<ThreadCreationRequest> threadConfigurationStage) {
        var auth =
                Files.authenticate(FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS)));
        try (
                var authUtils = mockStatic(Authenticator.class);
                var filesSimplified = mockStatic(FileUploadSimplified.class)
        ) {
            MockedFileSimplifiedUtils.mockFileSimplified(
                    authUtils,
                    auth,
                    filesSimplified
            );

            execute(threadConfigurationStage);
            var response = this.threadMessageFileStage.feed(FILE);

            assertNotNull(response);
        }
    }

    private void performFeedWithFileResponse(ThreadConfigurationStage<ThreadCreationRequest> threadConfigurationStage) {
        execute(threadConfigurationStage);
        var response = this.threadMessageFileStage.feed(FILE_RESPONSE);

        assertNotNull(response);
    }

    private void initializeStageWithEmptyContent() {
        this.threadMessageFileStage = new ThreadMessageFileStage<>(
                CREATE_THREAD_HTTP_EXECUTOR,
                THREAD_CREATION_REQUEST_BUILDER,
                null
        );
    }
}