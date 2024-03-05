package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.tool.CodeInterpreter;
import bg.codexio.ai.openai.api.payload.assistant.tool.Retrieval;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssistantToolStageTest {

    private AssistantToolStage assistantToolStage;

    @BeforeEach
    void setUp() {
        this.assistantToolStage = new AssistantToolStage(
                ASSISTANT_HTTP_EXECUTOR,
                AssistantRequest.builder()
                                .withModel(ASSISTANT_MODEL_TYPE.name())
        );
    }

    @Test
    void testFrom_withCodeInterpreter_expectAddedCodeInterpreterToolType() {
        var nextStage = this.assistantToolStage.from(new CodeInterpreter());
        this.previousValuesRemainsUnchanged(nextStage);

        assertEquals(
                CODE_INTERPRETER_TYPE,
                nextStage.requestBuilder.tools()
                                        .get(0)
                                        .type()
        );
    }

    @Test
    void testFrom_withRetrieval_expectAddedRetrievalToolType() {
        var nextStage = this.assistantToolStage.from(new Retrieval());
        this.previousValuesRemainsUnchanged(nextStage);

        assertEquals(
                RETRIEVAL_TOOL_TYPE,
                nextStage.requestBuilder.tools()
                                        .get(0)
                                        .type()
        );
    }

    private void previousValuesRemainsUnchanged(AssistantConfigurationStage stage) {
        assertAll(
                () -> modelRemainsUnchanged(
                        this.assistantToolStage,
                        stage
                ),
                () -> nameRemainsUnchanged(
                        this.assistantToolStage,
                        stage
                ),
                () -> descriptionRemainsUnchanged(
                        this.assistantToolStage,
                        stage
                ),
                () -> instructionsRemainsUnchanged(
                        this.assistantToolStage,
                        stage
                ),
                () -> fileIdsRemainsUnchanged(
                        this.assistantToolStage,
                        stage
                ),
                () -> metadataRemainsUnchanged(
                        this.assistantToolStage,
                        stage
                )
        );
    }
}