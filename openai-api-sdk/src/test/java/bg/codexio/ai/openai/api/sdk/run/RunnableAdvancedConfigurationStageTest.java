package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_ID;
import static bg.codexio.ai.openai.api.sdk.run.InternalAssertions.*;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_ID;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RunnableAdvancedConfigurationStageTest {

    private RunnableAdvancedConfigurationStage runnableAdvancedConfigurationStage;

    @BeforeEach
    void setUp() {
        this.runnableAdvancedConfigurationStage =
                new RunnableAdvancedConfigurationStage(
                RUNNABLE_HTTP_EXECUTOR,
                RunnableRequest.builder()
                               .withAssistantId(ASSISTANT_ID),
                THREAD_ID

        );
    }

    @Test
    void testMeta_expectCorrectBuilder() {
        var nextStage = this.runnableAdvancedConfigurationStage.meta();

        this.previousValuesRemainsUnchanged(nextStage);
    }

    @Test
    void testAiModel_expectCorrectBuilder() {
        var nextStage = this.runnableAdvancedConfigurationStage.aiModel();

        this.previousValuesRemainsUnchanged(nextStage);
    }

    @Test
    void testInstruction_expectCorrectBuilder() {
        var nextStage = this.runnableAdvancedConfigurationStage.instruction();

        this.previousValuesRemainsUnchanged(nextStage);
    }

    @Test
    void testAndRespond_expectCorrectResponse() {
        execute(this.runnableAdvancedConfigurationStage);

        var response = this.runnableAdvancedConfigurationStage.andRespond();

        assertEquals(
                RUNNABLE_RESPONSE,
                response
        );
    }

    @Test
    void testResult_expectCorrectBuilder() {
        var nextStage = this.runnableAdvancedConfigurationStage.result();

        this.previousValuesRemainsUnchanged(nextStage);
    }

    @Test
    void testFinish_expectCorrectBuilder() {
        execute(this.runnableAdvancedConfigurationStage);

        var nextStage =
                this.runnableAdvancedConfigurationStage.finishImmediate();

        this.previousValuesRemainsUnchanged(nextStage);
    }


    private void previousValuesRemainsUnchanged(RunnableConfigurationStage stage) {
        assertAll(
                () -> assistantIdRemainsUnchanged(
                        this.runnableAdvancedConfigurationStage,
                        stage
                ),
                () -> modelRemainsUnchanged(
                        this.runnableAdvancedConfigurationStage,
                        stage
                ),
                () -> additionalInstructionsRemainsUnchanged(
                        this.runnableAdvancedConfigurationStage,
                        stage
                ),
                () -> metadataRemainsUnchanged(
                        this.runnableAdvancedConfigurationStage,
                        stage
                )
        );
    }
}
