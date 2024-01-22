package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.tool.CodeInterpreter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssistantInstructionStageTest {

    private AssistantInstructionStage assistantInstructionStage;

    @BeforeEach
    void setUp() {
        this.assistantInstructionStage = new AssistantInstructionStage(
                null,
                AssistantRequest.builder()
                                .withModel(ASSISTANT_MODEL_TYPE.name())
                                .addTools(new CodeInterpreter())
                                .withName(ASSISTANT_NAME)
        );
    }

    @Test
    void testInstruct_withInstruction_expectInstructionApplied() {
        var nextStage =
                this.assistantInstructionStage.instruct(ASSISTANT_INSTRUCTION);
        this.previousValuesRemainsUnchanged(nextStage);

        assertEquals(
                ASSISTANT_INSTRUCTION,
                nextStage.requestBuilder.instructions()
        );
    }

    private void previousValuesRemainsUnchanged(AssistantConfigurationStage stage) {
        assertAll(
                () -> modelRemainsUnchanged(
                        this.assistantInstructionStage,
                        stage
                ),
                () -> nameRemainsUnchanged(
                        this.assistantInstructionStage,
                        stage
                ),
                () -> descriptionRemainsUnchanged(
                        this.assistantInstructionStage,
                        stage
                ),
                () -> toolsRemainsUnchanged(
                        this.assistantInstructionStage,
                        stage
                ),
                () -> fileIdsRemainsUnchanged(
                        this.assistantInstructionStage,
                        stage
                ),
                () -> metadataRemainsUnchanged(
                        this.assistantInstructionStage,
                        stage
                )
        );
    }
}
