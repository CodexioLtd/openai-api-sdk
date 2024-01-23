package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.models.v35.GPT35TurboModel;
import bg.codexio.ai.openai.api.models.v40.GPT401106Model;
import bg.codexio.ai.openai.api.models.v40.GPT40Model;
import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_ID;
import static bg.codexio.ai.openai.api.sdk.run.InternalAssertions.*;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_ID;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AiModelStageTest {

    private AiModelStage aiModelStage;

    @BeforeEach
    void setUp() {
        this.aiModelStage = new AiModelStage(
                null,
                RunnableRequest.builder()
                               .withAssistantId(ASSISTANT_ID),
                THREAD_ID
        );
    }

    @Test
    public void testPoweredByGPT350Turbo_expectGP35Turbo() {
        var nextStage = this.aiModelStage.poweredByGPT35();
        this.previousValuesRemainsUnchanged(nextStage);

        assertEquals(
                new GPT35TurboModel().name(),
                nextStage.requestBuilder.model()
        );
    }

    @Test
    public void testPoweredByGPT40_expectGP4T() {
        var nextStage = this.aiModelStage.poweredByGPT40();
        this.previousValuesRemainsUnchanged(nextStage);

        assertEquals(
                new GPT40Model().name(),
                nextStage.requestBuilder.model()
        );
    }

    @Test
    public void testTurboPowered_expectTurboPowered() {
        var nextStage = this.aiModelStage.turboPowered();
        this.previousValuesRemainsUnchanged(nextStage);

        assertEquals(
                new GPT401106Model().name(),
                nextStage.requestBuilder.model()
        );
    }


    private void previousValuesRemainsUnchanged(RunnableConfigurationStage stage) {
        assertAll(
                () -> assistantIdRemainsUnchanged(
                        this.aiModelStage,
                        stage
                ),
                () -> metadataRemainsUnchanged(
                        this.aiModelStage,
                        stage
                ),
                () -> additionalInstructionsRemainsUnchanged(
                        this.aiModelStage,
                        stage
                )
        );
    }
}