package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.models.v35.GPT35TurboModel;
import bg.codexio.ai.openai.api.models.v40.GPT401106Model;
import bg.codexio.ai.openai.api.models.v40.GPT40Model;
import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AIModelStageTest {

    private AIModelStage aiModelStage;

    @BeforeEach
    void setUp() {
        this.aiModelStage = new AIModelStage(
                null,
                AssistantRequest.builder()
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

    public void previousValuesRemainsUnchanged(AssistantConfigurationStage stage) {
        assertAll(
                () -> metadataRemainsUnchanged(
                        this.aiModelStage,
                        stage
                ),
                () -> nameRemainsUnchanged(
                        this.aiModelStage,
                        stage
                ),
                () -> instructionsRemainsUnchanged(
                        this.aiModelStage,
                        stage
                ),
                () -> descriptionRemainsUnchanged(
                        this.aiModelStage,
                        stage
                ),
                () -> fileIdsRemainsUnchanged(
                        this.aiModelStage,
                        stage
                ),
                () -> toolsRemainsUnchanged(
                        this.aiModelStage,
                        stage
                )
        );
    }
}
