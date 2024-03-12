package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_ID;
import static bg.codexio.ai.openai.api.sdk.run.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RunnableInstructionStageTest {

    private RunnableInstructionStage runnableInstructionStage;

    @BeforeEach
    void setUp() {
        this.runnableInstructionStage = new RunnableInstructionStage(
                null,
                RunnableRequest.builder()
                               .withAssistantId(ASSISTANT_ID),
                THREAD_ID
        );
    }

    @Test
    void testInstruct_expectCorrectBuilder() {
        var nextStage =
                this.runnableInstructionStage.instruct(RUNNABLE_ADDITIONAL_INSTRUCTIONS);
        this.previousValuesRemainsUnchanged(nextStage);

        assertEquals(
                RUNNABLE_ADDITIONAL_INSTRUCTIONS,
                nextStage.requestBuilder.additionalInstructions()
        );
    }

    private void previousValuesRemainsUnchanged(RunnableConfigurationStage stage) {
        assertAll(
                () -> assistantIdRemainsUnchanged(
                        this.runnableInstructionStage,
                        stage
                ),
                () -> metadataRemainsUnchanged(
                        this.runnableInstructionStage,
                        stage
                ),
                () -> modelRemainsUnchanged(
                        this.runnableInstructionStage,
                        stage
                )
        );
    }
}
