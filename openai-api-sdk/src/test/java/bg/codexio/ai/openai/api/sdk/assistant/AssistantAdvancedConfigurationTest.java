package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.tool.CodeInterpreter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
        var nextStage = this.assistantAdvancedConfigurationStage.file();

        this.previousValuesRemainsUnchanged(nextStage);
    }

    @Test
    public void testMeta_expectCorrectBuilder() {
        var nextStage = this.assistantAdvancedConfigurationStage.meta();

        this.previousValuesRemainsUnchanged(nextStage);
    }

    @Test
    public void testDescription_expectCorrectBuilder() {
        var nextStage = this.assistantAdvancedConfigurationStage.meta();

        this.previousValuesRemainsUnchanged(nextStage);
    }

    @Test
    public void testAndRespond_expectCorrectResponse() {
        when(this.assistantAdvancedConfigurationStage.httpExecutor.execute(any())).thenAnswer(response -> ASSISTANT_RESPONSE);

        var response = this.assistantAdvancedConfigurationStage.andRespond();

        assertEquals(
                ASSISTANT_RESPONSE,
                response
        );
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
