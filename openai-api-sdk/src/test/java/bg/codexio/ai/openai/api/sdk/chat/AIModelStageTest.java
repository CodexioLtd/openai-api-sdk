package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.models.v35.GPT35TurboModel;
import bg.codexio.ai.openai.api.models.v40.GPT401106Model;
import bg.codexio.ai.openai.api.models.v40.GPT40Model;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.chat.InternalAssertions.MODEL_TYPE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AIModelStageTest {

    private AIModelStage aiModelStage;

    @BeforeEach
    public void setUp() {
        this.aiModelStage = new AIModelStage(
                null,
                ChatMessageRequest.builder()
        );
    }

    @Test
    public void testPoweredByGPT350Turbo_expectGP35Turbo() {
        TemperatureStage nextStage = this.aiModelStage.poweredByGPT35();

        assertEquals(
                new GPT35TurboModel().name(),
                nextStage.requestBuilder.model()
        );
    }

    @Test
    public void testPoweredByGPT40_expectGP4T() {
        TemperatureStage nextStage = this.aiModelStage.poweredByGPT40();

        assertEquals(
                new GPT40Model().name(),
                nextStage.requestBuilder.model()
        );
    }

    @Test
    public void testTurboPowered_expectTurboPowered() {
        TemperatureStage nextStage = this.aiModelStage.turboPowered();

        assertEquals(
                new GPT401106Model().name(),
                nextStage.requestBuilder.model()
        );
    }

    @Test
    public void testPoweredByManualChoice_expectManualSelection() {
        TemperatureStage nextStage = this.aiModelStage.poweredBy(MODEL_TYPE);

        assertEquals(
                MODEL_TYPE.name(),
                nextStage.requestBuilder.model()
        );
    }
}
