package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.tool.CodeInterpreter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AssistantAdvancedConfigurationTest {

    private AssistantAdvancedConfigurationStage assistantAdvancedConfigurationStage;

    @BeforeEach
    void setUp() {
        this.assistantAdvancedConfigurationStage =
                new AssistantAdvancedConfigurationStage(
                ASSISTANT_HTTP_EXECUTOR,
                AssistantRequest.builder()
                                .withModel(ASSISTANT_MODEL_TYPE.name())
                                .addTools(new CodeInterpreter())
                                .withName(ASSISTANT_NAME)
                                .withInstructions(ASSISTANT_INSTRUCTION)
        );
    }

    @Test
    public void testFile_expectCorrectBuilder() {
        this.previousValuesRemainsUnchanged(this.assistantAdvancedConfigurationStage.file());
    }

    @Test
    public void testMeta_expectCorrectBuilder() {
        this.previousValuesRemainsUnchanged(this.assistantAdvancedConfigurationStage.meta());
    }

    @Test
    public void testDescription_expectCorrectBuilder() {
        this.previousValuesRemainsUnchanged(this.assistantAdvancedConfigurationStage.description());
    }

    @Test
    public void testAndRespond_expectCorrectResponse() {
        this.previousValuesRemainsUnchanged(this.assistantAdvancedConfigurationStage.andRespond());
    }

    public void previousValuesRemainsUnchanged(AssistantConfigurationStage stage) {
        assertAll(
                () -> modelRemainsUnchanged(
                        this.assistantAdvancedConfigurationStage,
                        stage
                ),
                () -> nameRemainsUnchanged(
                        this.assistantAdvancedConfigurationStage,
                        stage
                ),
                () -> instructionsRemainsUnchanged(
                        this.assistantAdvancedConfigurationStage,
                        stage
                ),
                () -> descriptionRemainsUnchanged(
                        this.assistantAdvancedConfigurationStage,
                        stage
                ),
                () -> toolsRemainsUnchanged(
                        this.assistantAdvancedConfigurationStage,
                        stage
                ),
                () -> fileIdsRemainsUnchanged(
                        this.assistantAdvancedConfigurationStage,
                        stage
                ),
                () -> metadataRemainsUnchanged(
                        this.assistantAdvancedConfigurationStage,
                        stage
                )
        );
    }
}
