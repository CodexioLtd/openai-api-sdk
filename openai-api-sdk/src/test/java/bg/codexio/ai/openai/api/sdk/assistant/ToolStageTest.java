package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.tool.CodeInterpreter;
import bg.codexio.ai.openai.api.payload.assistant.tool.Retrieval;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToolStageTest {

    private ToolStage toolStage;

    @BeforeEach
    void setUp() {
        this.toolStage = new ToolStage(
                ASSISTANT_HTTP_EXECUTOR,
                AssistantRequest.builder()
                                .withModel(ASSISTANT_MODEL_TYPE.name())
        );
    }

    @Test
    void testFrom_withCodeInterpreter_expectAddedCodeInterpreterToolType() {
        var nextStage = this.toolStage.from(new CodeInterpreter());
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
        var nextStage = this.toolStage.from(new Retrieval());
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
                        this.toolStage,
                        stage
                ),
                () -> nameRemainsUnchanged(
                        this.toolStage,
                        stage
                ),
                () -> descriptionRemainsUnchanged(
                        this.toolStage,
                        stage
                ),
                () -> instructionsRemainsUnchanged(
                        this.toolStage,
                        stage
                ),
                () -> fileIdsRemainsUnchanged(
                        this.toolStage,
                        stage
                ),
                () -> metadataRemainsUnchanged(
                        this.toolStage,
                        stage
                )
        );
    }
}