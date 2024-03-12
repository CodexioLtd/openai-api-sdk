package bg.codexio.ai.openai.api.sdk.thread.create;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import bg.codexio.ai.openai.api.sdk.Authenticator;
import bg.codexio.ai.openai.api.sdk.MockedFileSimplifiedUtils;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import bg.codexio.ai.openai.api.sdk.file.Files;
import bg.codexio.ai.openai.api.sdk.file.upload.FileUploadSimplified;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.*;
import static bg.codexio.ai.openai.api.sdk.file.InternalAssertions.FILE_RESPONSE;
import static bg.codexio.ai.openai.api.sdk.thread.create.InternalAssertions.CREATE_THREAD_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.thread.create.InternalAssertions.THREAD_MESSAGE_CONTENT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class ThreadMessageFileStageTest {

    private ThreadMessageFileStage threadMessageFileStage;

    @BeforeEach
    void setUp() {
        this.threadMessageFileStage = new ThreadMessageFileStage(
                CREATE_THREAD_HTTP_EXECUTOR,
                ThreadCreationRequest.builder(),
                THREAD_MESSAGE_CONTENT
        );
    }

    @Test
    void testFeed_withFile_expectCorrectResponse() {
        this.performFeedWithFile();
    }

    @Test
    void testFeed_withFileAndEmptyContent_expectCorrectResponse() {
        this.initializeStageWithEmptyContent();
        this.performFeedWithFile();
    }

    @Test
    void testFeed_withFileResponse_expectCorrectResponse() {
        assertNotNull(this.threadMessageFileStage.feed(FILE_RESPONSE));
    }

    @Test
    void testFeed_withFileResponseAndEmptyContent_expectCorrectResponse() {
        this.initializeStageWithEmptyContent();
        assertNotNull(this.threadMessageFileStage.feed(FILE_RESPONSE));
    }

    @Test
    void testFeed_withFileIdVarArgs_expectCorrectResponse() {
        assertNotNull(this.threadMessageFileStage.feed(FILE_IDS_VAR_ARGS));
    }

    @Test
    void testFeed_withFileIdVarArgsAndEmptyContent_expectCorrectResponse() {
        this.initializeStageWithEmptyContent();
        assertNotNull(this.threadMessageFileStage.feed(FILE_IDS_VAR_ARGS));
    }

    // add file uploading mocking
    @Test
    void testAttach_withFile_expectCorrectBuilder() {
        this.performFileIdAssertion(this.threadMessageFileStage.attach(FILE));
    }

    @Test
    void testAttach_withFileResponse_expectCorrectBuilder() {
        this.performFileIdAssertion(this.threadMessageFileStage.attach(FILE_RESPONSE));
    }

    @Test
    void testAttach_withFileIdVarArgs_expectCorrectResponse() {
        this.performFileIdAssertion(this.threadMessageFileStage.attach(FILE_IDS_VAR_ARGS));
    }

    private void performFileIdAssertion(ThreadConfigurationStage threadConfigurationStage) {
        var messageRequest = threadConfigurationStage.requestBuilder.messages()
                                                                    .get(0);

        assertAll(
                () -> assertTrue(messageRequest.content()
                                               .isEmpty()),
                () -> assertNotNull(messageRequest.fileIds())
        );
    }

    private void performFeedWithFile() {
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

            assertNotNull(this.threadMessageFileStage.feed(FILE));
        }
    }

    private void initializeStageWithEmptyContent() {
        this.threadMessageFileStage = new ThreadMessageFileStage(
                CREATE_THREAD_HTTP_EXECUTOR,
                ThreadCreationRequest.builder(),
                null
        );
    }
}