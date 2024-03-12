package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.tool.CodeInterpreter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AssistantAsyncContextStageTest {

    private AssistantAsyncContextStage assistantAsyncContextStage;

    @BeforeEach
    void setUp() {
        this.assistantAsyncContextStage = new AssistantAsyncContextStage(
                ASSISTANT_HTTP_EXECUTOR,
                AssistantRequest.builder()
                                .withModel(ASSISTANT_MODEL_TYPE.name())
                                .addTools(new CodeInterpreter())
                                .withName(ASSISTANT_NAME)
                                .withInstructions(ASSISTANT_INSTRUCTION)
        );
    }

    @Test
    public void testFinish_expectCorrectBuilder() {
        this.previousValuesRemainsUnchanged(this.assistantAsyncContextStage.finish());
    }


    public void previousValuesRemainsUnchanged(AssistantConfigurationStage stage) {
        assertAll(
                () -> modelRemainsUnchanged(
                        this.assistantAsyncContextStage,
                        stage
                ),
                () -> nameRemainsUnchanged(
                        this.assistantAsyncContextStage,
                        stage
                ),
                () -> instructionsRemainsUnchanged(
                        this.assistantAsyncContextStage,
                        stage
                ),
                () -> descriptionRemainsUnchanged(
                        this.assistantAsyncContextStage,
                        stage
                ),
                () -> toolsRemainsUnchanged(
                        this.assistantAsyncContextStage,
                        stage
                ),
                () -> fileIdsRemainsUnchanged(
                        this.assistantAsyncContextStage,
                        stage
                ),
                () -> metadataRemainsUnchanged(
                        this.assistantAsyncContextStage,
                        stage
                )
        );
    }
}
