package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.message.response.MessageResponse;
import bg.codexio.ai.openai.api.sdk.Authenticator;
import bg.codexio.ai.openai.api.sdk.HttpBuilder;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import bg.codexio.ai.openai.api.sdk.file.FileSimplified;
import bg.codexio.ai.openai.api.sdk.file.FileTargetingStage;
import bg.codexio.ai.openai.api.sdk.file.Files;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Arrays;

import static bg.codexio.ai.openai.api.sdk.SharedConstantsUtils.FILE;
import static bg.codexio.ai.openai.api.sdk.SharedConstantsUtils.FILE_IDS_VAR_ARGS;
import static bg.codexio.ai.openai.api.sdk.file.FilesTest.TEST_KEY;
import static bg.codexio.ai.openai.api.sdk.file.InternalAssertions.FILE_RESPONSE;
import static bg.codexio.ai.openai.api.sdk.file.InternalAssertions.UPLOAD_FILE_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.message.InternalAssertions.*;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

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
        var auth =
                Files.authenticate(FromDeveloper.doPass(new ApiCredentials(TEST_KEY)));
        try (var authUtils = mockStatic(Authenticator.class)) {
            authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                     .thenReturn(auth);
            when(UPLOAD_FILE_HTTP_EXECUTOR.execute(any())).thenAnswer(response -> FILE_RESPONSE);
            var nextStage = this.messageFileStage.feed(FILE);

            assertNotNull(nextStage);
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

    private void mockFileSimplified(
            MockedStatic<Authenticator> authUtils,
            HttpBuilder<FileTargetingStage> auth,
            MockedStatic<FileSimplified> filesSimplified
    ) {
        authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                 .thenReturn(auth);

        filesSimplified.when(() -> Files.defaults()
                                        .and()
                                        .forAssistants()
                                        .feed(FILE))
                       .thenReturn(FILE_RESPONSE.id());
    }
}
