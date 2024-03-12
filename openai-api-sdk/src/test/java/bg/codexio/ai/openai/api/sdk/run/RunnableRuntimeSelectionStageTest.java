package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_ID;
import static bg.codexio.ai.openai.api.sdk.run.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class RunnableRuntimeSelectionStageTest {

    private RunnableRuntimeSelectionStage runnableRuntimeSelectionStage;

    @BeforeEach
    void setUp() {
        this.runnableRuntimeSelectionStage = new RunnableRuntimeSelectionStage(
                RUNNABLE_HTTP_EXECUTOR,
                RunnableRequest.builder()
                               .withAssistantId(ASSISTANT_ID),
                THREAD_ID
        );
    }

    @Test
    public void testImmediate_expectCorrectBuilder() {
        this.previousValuesRemainsUnchanged(this.runnableRuntimeSelectionStage.immediate());
    }

    @Test
    public void testAsync_expectCorrectBuilder() {
        this.previousValuesRemainsUnchanged(this.runnableRuntimeSelectionStage.async());
    }

    @Test
    public void testReactive_expectCorrectBuilder() {
        this.previousValuesRemainsUnchanged(this.runnableRuntimeSelectionStage.reactive());
    }

    private void previousValuesRemainsUnchanged(RunnableConfigurationStage stage) {
        assertAll(
                () -> assistantIdRemainsUnchanged(
                        this.runnableRuntimeSelectionStage,
                        stage
                ),
                () -> modelRemainsUnchanged(
                        this.runnableRuntimeSelectionStage,
                        stage
                ),
                () -> additionalInstructionsRemainsUnchanged(
                        this.runnableRuntimeSelectionStage,
                        stage
                ),
                () -> metadataRemainsUnchanged(
                        this.runnableRuntimeSelectionStage,
                        stage
                )
        );
    }
}