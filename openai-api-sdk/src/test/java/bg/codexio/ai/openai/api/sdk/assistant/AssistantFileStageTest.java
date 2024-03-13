package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.tool.CodeInterpreter;
import bg.codexio.ai.openai.api.sdk.Authenticator;
import bg.codexio.ai.openai.api.sdk.MockedFileSimplifiedUtils;
import bg.codexio.ai.openai.api.sdk.file.upload.simply.FileAsyncUploadSimplified;
import bg.codexio.ai.openai.api.sdk.file.upload.simply.FileImmediateUploadSimplified;
import bg.codexio.ai.openai.api.sdk.file.upload.simply.FileReactiveUploadSimplified;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static bg.codexio.ai.openai.api.sdk.AsyncCallbackUtils.prepareCallback;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.FILE;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.FILE_RESPONSE;
import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

public class AssistantFileStageTest {

    private AssistantFileStage assistantFileStage;

    @BeforeEach
    void setUp() {
        this.assistantFileStage = new AssistantFileStage(
                null,
                AssistantRequest.builder()
                                .withModel(ASSISTANT_MODEL_TYPE.name())
                                .addTools(new CodeInterpreter())
                                .withName(ASSISTANT_NAME)
                                .withInstructions(ASSISTANT_INSTRUCTION)
        );
    }

    @Test
    void testFeed_withFileIdsVarArgs_expectCorrectBuilder() {
        var nextStage = this.assistantFileStage.feed(FILE_IDS);

        this.previousValuesRemainsUnchanged(nextStage);

        assertEquals(

                Arrays.stream(FILE_IDS)
                      .toList(),
                nextStage.requestBuilder.fileIds()
        );
    }

    @Test
    void testFeed_withFileResponse_expectCorrectBuilder() {
        var nextStage = this.assistantFileStage.feed(FILE_RESPONSE);

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

            var nextStage = this.assistantFileStage.feedImmediate(FILE);

            this.previousValuesRemainsUnchanged(nextStage);
        }
    }

    @Test
    void testFeedAsync_expectCorrectBuilder() {
        var callBack =
                prepareCallback(AssistantAdvancedConfigurationStage.class);
        try (
                var authUtils = mockStatic(Authenticator.class);
                var filesSimplified =
                        mockStatic(FileAsyncUploadSimplified.class)
        ) {
            MockedFileSimplifiedUtils.mockAsyncFileSimplified(
                    authUtils,
                    filesSimplified
            );

            this.assistantFileStage.feedAsync(
                    FILE,
                    callBack.callback()
            );

            this.previousValuesRemainsUnchanged(callBack.data());
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

            this.previousValuesRemainsUnchanged(this.assistantFileStage.feedReactive(FILE)
                                                                       .block());
        }
    }

    public void previousValuesRemainsUnchanged(AssistantConfigurationStage stage) {
        assertAll(
                () -> modelRemainsUnchanged(
                        this.assistantFileStage,
                        stage
                ),
                () -> nameRemainsUnchanged(
                        this.assistantFileStage,
                        stage
                ),
                () -> instructionsRemainsUnchanged(
                        this.assistantFileStage,
                        stage
                ),
                () -> descriptionRemainsUnchanged(
                        this.assistantFileStage,
                        stage
                ),
                () -> toolsRemainsUnchanged(
                        this.assistantFileStage,
                        stage
                ),
                () -> metadataRemainsUnchanged(
                        this.assistantFileStage,
                        stage
                )
        );
    }
}