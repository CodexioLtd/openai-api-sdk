package bg.codexio.ai.openai.api.sdk.thread.create;

import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import bg.codexio.ai.openai.api.sdk.Authenticator;
import bg.codexio.ai.openai.api.sdk.MockedFileSimplifiedUtils;
import bg.codexio.ai.openai.api.sdk.file.upload.simply.FileAsyncUploadSimplified;
import bg.codexio.ai.openai.api.sdk.file.upload.simply.FileImmediateUploadSimplified;
import bg.codexio.ai.openai.api.sdk.file.upload.simply.FileReactiveUploadSimplified;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static bg.codexio.ai.openai.api.sdk.AsyncCallbackUtils.prepareCallback;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.*;
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
    void testFeedImmediate_expectCorrectBuilder() {
        this.performImmediateFeedWithFile();
    }

    @Test
    void testFeedImmediate_withEmptyContent_expectCorrectBuilder() {
        this.initializeStageWithEmptyContent();
        this.performImmediateFeedWithFile();
    }

    @Test
    void testFeedAsync_expectCorrectBuilder() {
        this.performAsyncFeedWithFile();
    }

    @Test
    void testFeedAsync_withEmptyContent_expectCorrectBuilder() {
        this.initializeStageWithEmptyContent();
        this.performAsyncFeedWithFile();
    }

    @Test
    void testFeedReactive_expectCorrectBuilder() {
        this.performReactiveFeedWithFile();
    }

    @Test
    void testFeedReactive_withEmptyContent_expectCorrectBuilder() {
        this.initializeStageWithEmptyContent();
        this.performReactiveFeedWithFile();
    }

    @Test
    void testFeed_withFileResponse_expectCorrectBuilder() {
        assertNotNull(this.threadMessageFileStage.feed(FILE_RESPONSE));
    }

    @Test
    void testFeed_withFileResponseAndEmptyContent_expectCorrectBuilder() {
        this.initializeStageWithEmptyContent();
        assertNotNull(this.threadMessageFileStage.feed(FILE_RESPONSE));
    }

    @Test
    void testFeed_withFileIdVarArgs_expectCorrectBuilder() {
        assertNotNull(this.threadMessageFileStage.feed(FILE_IDS_VAR_ARGS));
    }

    @Test
    void testFeed_withFileIdVarArgsAndEmptyContent_expectCorrectBuilder() {
        this.initializeStageWithEmptyContent();
        assertNotNull(this.threadMessageFileStage.feed(FILE_IDS_VAR_ARGS));
    }

    @Test
    void testAttachImmediate_expectCorrectBuilder() {
        try (
                var authUtils = mockStatic(Authenticator.class);
                var filesSimplified =
                        mockStatic(FileImmediateUploadSimplified.class)
        ) {
            MockedFileSimplifiedUtils.mockImmediateFileSimplified(
                    authUtils,
                    filesSimplified
            );

            this.performFileIdAssertion(this.threadMessageFileStage.attachImmediate(FILE));
        }
    }

    @Test
    void testAttachAsync_expectCorrectBuilder() {
        var callback = prepareCallback(ThreadAdvancedConfigurationStage.class);

        try (
                var authUtils = mockStatic(Authenticator.class);
                var filesSimplified =
                        mockStatic(FileAsyncUploadSimplified.class)
        ) {
            MockedFileSimplifiedUtils.mockAsyncFileSimplified(
                    authUtils,
                    filesSimplified
            );

            this.threadMessageFileStage.attachAsync(
                    FILE,
                    callback.callback()
            );

            this.performFileIdAssertion(callback.data());
        }
    }

    @Test
    void testAttachReactive_expectCorrectBuilder() {
        try (
                var authUtils = mockStatic(Authenticator.class);
                var filesSimplified =
                        mockStatic(FileReactiveUploadSimplified.class)
        ) {
            MockedFileSimplifiedUtils.mockReactiveFileSimplified(
                    authUtils,
                    filesSimplified
            );

            this.performFileIdAssertion(Objects.requireNonNull(this.threadMessageFileStage.attachReactive(FILE)
                                                                                          .block()));
        }
    }

    @Test
    void testAttach_withFileResponse_expectCorrectBuilder() {
        this.performFileIdAssertion(this.threadMessageFileStage.attach(FILE_RESPONSE));
    }

    @Test
    void testAttach_withFileIdVarArgs_expectCorrectBuilder() {
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

    private void performImmediateFeedWithFile() {
        try (
                var authUtils = mockStatic(Authenticator.class);
                var filesSimplified =
                        mockStatic(FileImmediateUploadSimplified.class)
        ) {
            MockedFileSimplifiedUtils.mockImmediateFileSimplified(
                    authUtils,
                    filesSimplified
            );

            assertNotNull(this.threadMessageFileStage.feedImmediate(FILE));
        }
    }

    private void performAsyncFeedWithFile() {
        var callback = prepareCallback(ThreadRuntimeSelectionStage.class);

        try (
                var authUtils = mockStatic(Authenticator.class);
                var filesSimplified =
                        mockStatic(FileAsyncUploadSimplified.class)
        ) {
            MockedFileSimplifiedUtils.mockAsyncFileSimplified(
                    authUtils,
                    filesSimplified
            );

            this.threadMessageFileStage.feedAsync(
                    FILE,
                    callback.callback()
            );

            assertNotNull(callback.data());
        }
    }

    private void performReactiveFeedWithFile() {
        try (
                var authUtils = mockStatic(Authenticator.class);
                var filesSimplified =
                        mockStatic(FileReactiveUploadSimplified.class)
        ) {
            MockedFileSimplifiedUtils.mockReactiveFileSimplified(
                    authUtils,
                    filesSimplified
            );

            assertNotNull(this.threadMessageFileStage.feedReactive(FILE));
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