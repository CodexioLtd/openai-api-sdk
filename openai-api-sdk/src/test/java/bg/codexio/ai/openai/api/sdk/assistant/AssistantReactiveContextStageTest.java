package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.tool.CodeInterpreter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssistantReactiveContextStageTest {

    private AssistantReactiveContextStage assistantReactiveContextStage;

    @BeforeEach
    void setUp() {
        this.assistantReactiveContextStage = new AssistantReactiveContextStage(
                ASSISTANT_HTTP_EXECUTOR,
                AssistantRequest.builder()
                                .withModel(ASSISTANT_MODEL_TYPE.name())
                                .addTools(new CodeInterpreter())
                                .withName(ASSISTANT_NAME)
                                .withInstructions(ASSISTANT_INSTRUCTION)
        );
    }

    @Test
    public void testFinishRaw_expectCorrectResponse() {
        mockReactiveExecution(this.assistantReactiveContextStage);

        assertEquals(
                ASSISTANT_RESPONSE,
                this.assistantReactiveContextStage.finishRaw()
                                                  .response()
                                                  .block()
        );
    }

    @Test
    public void testFinis_expectCorrectResponse() {
        mockReactiveExecution(this.assistantReactiveContextStage);

        assertEquals(
                ASSISTANT_RESPONSE.id(),
                this.assistantReactiveContextStage.finish()
                                                  .block()
        );
    }

}
