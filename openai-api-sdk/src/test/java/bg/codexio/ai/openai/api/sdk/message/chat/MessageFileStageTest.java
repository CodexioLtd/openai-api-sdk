package bg.codexio.ai.openai.api.sdk.message.chat;

import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.sdk.Authenticator;
import bg.codexio.ai.openai.api.sdk.MockedFileSimplifiedUtils;
import bg.codexio.ai.openai.api.sdk.file.upload.simply.FileAsyncUploadSimplified;
import bg.codexio.ai.openai.api.sdk.file.upload.simply.FileImmediateUploadSimplified;
import bg.codexio.ai.openai.api.sdk.file.upload.simply.FileReactiveUploadSimplified;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static bg.codexio.ai.openai.api.sdk.AsyncCallbackUtils.prepareCallback;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.*;
import static bg.codexio.ai.openai.api.sdk.message.chat.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

public class MessageFileStageTest {

    private MessageFileStage messageFileStage;

    @BeforeEach
    void setUp() {
        this.messageFileStage = new MessageFileStage(
                MESSAGE_HTTP_EXECUTOR,
                MessageRequest.builder()
                              .withContent(MESSAGE_CONTENT),
                THREAD_ID
        );
    }

    @Test
    void testFeed_withFileIdsVarArgs_expectCorrectBuilder() {
        var nextStage = this.messageFileStage.feed(FILE_IDS_VAR_ARGS);
        this.previousValuesRemainsUnchanged(nextStage);

        assertEquals(
                Arrays.stream(FILE_IDS_VAR_ARGS)
                      .toList(),
                nextStage.requestBuilder.fileIds()
        );
    }

    @Test
    void testFeed_withFileResponse_expectCorrectBuilder() {
        var nextStage = this.messageFileStage.feed(FILE_RESPONSE);
        this.previousValuesRemainsUnchanged(nextStage);

        assertEquals(
                FILE_RESPONSE.id(),
                nextStage.requestBuilder.fileIds()
                                        .get(0)
        );
    }

    @Test
    void testFeedImmediate_expectCorrectBuilder() {
        try (
                var authUtils = mockStatic(Authenticator.class);
                var filesSimplified =
                        mockStatic(FileImmediateUploadSimplified.class)
        ) {
            MockedFileSimplifiedUtils.mockImmediateFileSimplified(
                    authUtils,
                    filesSimplified
            );
            var nextStage = this.messageFileStage.feedImmediate(FILE);

            this.previousValuesRemainsUnchanged(nextStage);
        }
    }

    @Test
    void testFeedAsync_expectCorrectBuilder() {
        var callback = prepareCallback(MessageAdvancedConfigurationStage.class);

        try (
                var authUtils = mockStatic(Authenticator.class);
                var filesSimplified =
                        mockStatic(FileAsyncUploadSimplified.class)
        ) {
            MockedFileSimplifiedUtils.mockAsyncFileSimplified(
                    authUtils,
                    filesSimplified
            );
            this.messageFileStage.feedAsync(
                    FILE,
                    callback.callback()
            );

            this.previousValuesRemainsUnchanged(callback.data());
        }
    }

    @Test
    void testFeedReactive_expectCorrectBuilder() {
        try (
                var authUtils = mockStatic(Authenticator.class);
                var filesSimplified =
                        mockStatic(FileReactiveUploadSimplified.class)
        ) {
            MockedFileSimplifiedUtils.mockReactiveFileSimplified(
                    authUtils,
                    filesSimplified
            );

            this.previousValuesRemainsUnchanged(this.messageFileStage.feedReactive(FILE)
                                                                     .block());
        }
    }

    private void previousValuesRemainsUnchanged(MessageConfigurationStage nextStage) {
        assertAll(
                () -> roleRemainsUnchanged(
                        this.messageFileStage,
                        nextStage
                ),
                () -> metadataRemainsUnchanged(
                        this.messageFileStage,
                        nextStage
                ),
                () -> contentRemainsUnchanged(
                        this.messageFileStage,
                        nextStage
                )
        );
    }
}
