package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.tool.CodeInterpreter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssistantDescriptionStageTest {

    public AssistantDescriptionStage assistantDescriptionStage;

    @BeforeEach
    void setUp() {
        this.assistantDescriptionStage = new AssistantDescriptionStage(
                null,
                AssistantRequest.builder()
                                .withModel(ASSISTANT_MODEL_TYPE.name())
                                .addTools(new CodeInterpreter())
                                .withName(ASSISTANT_NAME)
                                .withInstructions(ASSISTANT_INSTRUCTION)
        );
    }

    @Test
    void testContext_expectCorrectBuilder() {
        var nextStage =
                this.assistantDescriptionStage.context(ASSISTANT_DESCRIPTION);

        this.previousValuesRemainsUnchanged(nextStage);

        assertEquals(
                ASSISTANT_DESCRIPTION,
                nextStage.requestBuilder.description()
        );
    }

    public void previousValuesRemainsUnchanged(AssistantConfigurationStage stage) {
        assertAll(
                () -> modelRemainsUnchanged(
                        this.assistantDescriptionStage,
                        stage
                ),
                () -> nameRemainsUnchanged(
                        this.assistantDescriptionStage,
                        stage
                ),
                () -> instructionsRemainsUnchanged(
                        this.assistantDescriptionStage,
                        stage
                ),
                () -> metadataRemainsUnchanged(
                        this.assistantDescriptionStage,
                        stage
                ),
                () -> fileIdsRemainsUnchanged(
                        this.assistantDescriptionStage,
                        stage
                ),
                () -> toolsRemainsUnchanged(
                        this.assistantDescriptionStage,
                        stage
                )
        );
    }
}
