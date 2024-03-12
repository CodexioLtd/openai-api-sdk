package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.tool.CodeInterpreter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AssistantRuntimeSelectionStageTest {

    private AssistantRuntimeSelectionStage assistantRuntimeSelectionStage;

    @BeforeEach
    void setUp() {
        this.assistantRuntimeSelectionStage =
                new AssistantRuntimeSelectionStage(
                ASSISTANT_HTTP_EXECUTOR,
                AssistantRequest.builder()
                                .withModel(ASSISTANT_MODEL_TYPE.name())
                                .addTools(new CodeInterpreter())
                                .withName(ASSISTANT_NAME)
                                .withInstructions(ASSISTANT_INSTRUCTION)
        );
    }

    @Test
    public void testImmediate_expectCorrectBuilder() {
        assertNotNull(this.assistantRuntimeSelectionStage.immediate());
    }

    @Test
    public void testAsync_expectCorrectBuilder() {
        assertNotNull(this.assistantRuntimeSelectionStage.async());
    }

    @Test
    public void testReactive_expectCorrectBuilder() {
        assertNotNull(this.assistantRuntimeSelectionStage.reactive());
    }
}