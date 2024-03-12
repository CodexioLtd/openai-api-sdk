package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_ID;
import static bg.codexio.ai.openai.api.sdk.run.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

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
        this.previousValuesRemainsUnchanged(this.runnableAdvancedConfigurationStage.meta());
    }

    @Test
    void testAiModel_expectCorrectBuilder() {
        this.previousValuesRemainsUnchanged(this.runnableAdvancedConfigurationStage.aiModel());
    }

    @Test
    void testInstruction_expectCorrectBuilder() {
        this.previousValuesRemainsUnchanged(this.runnableAdvancedConfigurationStage.instruction());
    }

    @Test
    void testMessaging_expectCorrectBuilder() {
        this.previousValuesRemainsUnchanged(this.runnableAdvancedConfigurationStage.messaging());
    }

    @Test
    void testAndRespond_expectCorrectResponse() {
        this.previousValuesRemainsUnchanged(this.runnableAdvancedConfigurationStage.andRespond());
    }

    @Test
    void testResult_expectCorrectBuilder() {
        this.previousValuesRemainsUnchanged(this.runnableAdvancedConfigurationStage.result());
    }

    @Test
    void testFinish_expectCorrectBuilder() {
        this.previousValuesRemainsUnchanged(this.runnableAdvancedConfigurationStage.finish());
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