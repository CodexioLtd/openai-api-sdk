package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestConstantsUtils.METADATA_MAP;
import static bg.codexio.ai.openai.api.sdk.CommonTestConstantsUtils.METADATA_VAR_ARGS;
import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_ID;
import static bg.codexio.ai.openai.api.sdk.run.InternalAssertions.*;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_ID;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RunnableMetaStageTest {

    private RunnableMetaStage runnableMetaStage;

    @BeforeEach
    void setUp() {
        this.runnableMetaStage = new RunnableMetaStage(
                null,
                RunnableRequest.builder()
                               .withAssistantId(ASSISTANT_ID),
                THREAD_ID
        );
    }

    @Test
    void testAwareOf_expectCorrectBuilder() {
        var nextStage = this.runnableMetaStage.awareOf(METADATA_VAR_ARGS);
        this.previousValuesRemainsUnchanged(nextStage);

        assertEquals(
                METADATA_MAP,
                nextStage.requestBuilder.metadata()
        );
    }


    private void previousValuesRemainsUnchanged(RunnableConfigurationStage stage) {
        assertAll(
                () -> assistantIdRemainsUnchanged(
                        this.runnableMetaStage,
                        stage
                ),
                () -> modelRemainsUnchanged(
                        this.runnableMetaStage,
                        stage
                ),
                () -> additionalInstructionsRemainsUnchanged(
                        this.runnableMetaStage,
                        stage
                )
        );
    }
}
