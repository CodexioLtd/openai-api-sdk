package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.message.response.MessageResponse;
import bg.codexio.ai.openai.api.sdk.Authenticator;
import bg.codexio.ai.openai.api.sdk.MockedFileSimplifiedUtils;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import bg.codexio.ai.openai.api.sdk.file.Files;
import bg.codexio.ai.openai.api.sdk.file.upload.FileUploadSimplified;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.*;
import static bg.codexio.ai.openai.api.sdk.file.InternalAssertions.FILE_RESPONSE;
import static bg.codexio.ai.openai.api.sdk.message.InternalAssertions.*;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_ID;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

public class MessageFileStageTest {

    private MessageFileStage<MessageResponse> messageFileStage;

    @BeforeEach
    void setUp() {
        this.messageFileStage = new MessageFileStage<>(
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
    void testFeed_withFile_expectCorrectBuilder() {
        var auth = Files.authenticate(FromDeveloper.doPass(new ApiCredentials(API_CREDENTIALS)));
        try (
                var authUtils = mockStatic(Authenticator.class);
                var filesSimplified = mockStatic(FileUploadSimplified.class)
        ) {
            MockedFileSimplifiedUtils.mockFileSimplified(
                    authUtils,
                    auth,
                    filesSimplified
            );
            var nextStage = this.messageFileStage.feed(FILE);

            this.previousValuesRemainsUnchanged(nextStage);
        }
    }

    private void previousValuesRemainsUnchanged(MessageConfigurationStage<MessageResponse> nextStage) {
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
