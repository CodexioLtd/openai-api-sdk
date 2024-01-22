package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.tool.CodeInterpreter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssistantMetaStageTest {

    private AssistantMetaStage assistantMetaStage;

    @BeforeEach
    void setUp() {
        this.assistantMetaStage = new AssistantMetaStage(
                null,
                AssistantRequest.builder()
                                .withModel(ASSISTANT_MODEL_TYPE.name())
                                .addTools(new CodeInterpreter())
                                .withName(ASSISTANT_NAME)
                                .withInstructions(ASSISTANT_INSTRUCTION)
        );
    }

    @Test
    void testAwareOf_withMetadataVarArgs_expectCorrectBuilder() {
        var nextStage = this.assistantMetaStage.awareOf(METADATA_ARGS);

        this.previousValuesRemainsUnchanged(nextStage);

        assertEquals(
                ASSISTANT_METADATA,
                nextStage.requestBuilder.metadata()
        );
    }

    public void previousValuesRemainsUnchanged(AssistantConfigurationStage stage) {
        assertAll(
                () -> modelRemainsUnchanged(
                        this.assistantMetaStage,
                        stage
                ),
                () -> nameRemainsUnchanged(
                        this.assistantMetaStage,
                        stage
                ),
                () -> instructionsRemainsUnchanged(
                        this.assistantMetaStage,
                        stage
                ),
                () -> descriptionRemainsUnchanged(
                        this.assistantMetaStage,
                        stage
                ),
                () -> fileIdsRemainsUnchanged(
                        this.assistantMetaStage,
                        stage
                ),
                () -> toolsRemainsUnchanged(
                        this.assistantMetaStage,
                        stage
                )
        );
    }
}