package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.models.v35.GPT35TurboModel;
import bg.codexio.ai.openai.api.models.v40.GPT401106Model;
import bg.codexio.ai.openai.api.models.v40.GPT40Model;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AiModelStageTest {

    private AiModelStage aiModelStage;

    @BeforeEach
    void setUp() {
        this.aiModelStage = new AiModelStage(
                null,
                AssistantRequest.builder()
        );
    }

    @Test
    public void testPoweredByGPT350Turbo_expectGP35Turbo() {
        var nextStage = this.aiModelStage.poweredByGPT35();

        assertEquals(
                new GPT35TurboModel().name(),
                nextStage.requestBuilder.model()
        );
    }

    @Test
    public void testPoweredByGPT40_expectGP4T() {
        var nextStage = this.aiModelStage.poweredByGPT40();

        assertEquals(
                new GPT40Model().name(),
                nextStage.requestBuilder.model()
        );
    }

    @Test
    public void testTurboPowered_expectTurboPowered() {
        var nextStage = this.aiModelStage.turboPowered();

        assertEquals(
                new GPT401106Model().name(),
                nextStage.requestBuilder.model()
        );
    }
}
