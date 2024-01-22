package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.tool.CodeInterpreter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssistantNameStageTest {

    private AssistantNameStage assistantNameStage;

    @BeforeEach
    void setUp() {
        this.assistantNameStage = new AssistantNameStage(
                null,
                AssistantRequest.builder()
                                .withModel(ASSISTANT_MODEL_TYPE.name())
                                .addTools(new CodeInterpreter())
        );
    }

    @Test
    void testCalled_withName_expectCorrectNameApplied() {
        var nextStage = this.assistantNameStage.called(ASSISTANT_NAME);
        this.previousValuesRemainsUnchanged(nextStage);

        assertEquals(
                ASSISTANT_NAME,
                nextStage.requestBuilder.name()
        );
    }

    private void previousValuesRemainsUnchanged(AssistantConfigurationStage stage) {
        assertAll(
                () -> modelRemainsUnchanged(
                        this.assistantNameStage,
                        stage
                ),
                () -> descriptionRemainsUnchanged(
                        this.assistantNameStage,
                        stage
                ),
                () -> toolsRemainsUnchanged(
                        this.assistantNameStage,
                        stage
                ),
                () -> instructionsRemainsUnchanged(
                        this.assistantNameStage,
                        stage
                ),
                () -> fileIdsRemainsUnchanged(
                        this.assistantNameStage,
                        stage
                ),
                () -> metadataRemainsUnchanged(
                        this.assistantNameStage,
                        stage
                )
        );
    }
}