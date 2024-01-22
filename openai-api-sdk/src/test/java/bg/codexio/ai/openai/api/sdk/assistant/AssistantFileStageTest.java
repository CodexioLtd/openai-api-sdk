package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.tool.CodeInterpreter;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.Authenticator;
import bg.codexio.ai.openai.api.sdk.auth.FromDeveloper;
import bg.codexio.ai.openai.api.sdk.file.Files;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.*;
import static bg.codexio.ai.openai.api.sdk.file.FilesTest.TEST_KEY;
import static bg.codexio.ai.openai.api.sdk.file.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
    void testFeed_withFile_expectCorrectBuilder() {
        var auth =
                Files.authenticate(FromDeveloper.doPass(new ApiCredentials(TEST_KEY)));
        try (
                var authUtils = mockStatic(Authenticator.class);
                var filesSimplified = FILE_SIMPLIFIED
        ) {

            authUtils.when(() -> Authenticator.autoAuthenticate(any()))
                     .thenReturn(auth);

            filesSimplified.when(() -> Files.defaults()
                                            .and()
                                            .forAssistants()
                                            .feed(file))
                           .thenReturn(FILE_RESPONSE.id());

            var nextStage = this.assistantFileStage.feed(file);

            this.previousValuesRemainsUnchanged(nextStage);
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