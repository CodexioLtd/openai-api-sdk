package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.models.ModelTypeAbstract;
import bg.codexio.ai.openai.api.models.v40.GPT40VisionPreviewModel;
import bg.codexio.ai.openai.api.payload.vision.request.VisionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.vision.InternalAssertions.TEST_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.vision.InternalAssertions.executorRemainsUnchanged;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AIModelStageTest {

    private static final ModelTypeAbstract MODEL_MANUAL_CHOICE =
            new GPT40VisionPreviewModel();
    private AIModelStage aiModelStage;

    @BeforeEach
    public void setUp() {
        this.aiModelStage = new AIModelStage(
                TEST_EXECUTOR,
                VisionRequest.empty()
        );
    }

    @Test
    public void testDefaultModel_expectGpt40Vision() {
        var nextStage = this.aiModelStage.defaultModel();

        assertAll(
                () -> assertEquals(
                        new GPT40VisionPreviewModel().name(),
                        nextStage.requestContext.model()
                ),
                () -> executorRemainsUnchanged(
                        this.aiModelStage,
                        nextStage
                )
        );
    }

    @Test
    public void testPoweredByGpt40Vision_expectGpt40Vision() {
        var nextStage = this.aiModelStage.poweredByGpt40Vision();

        assertAll(
                () -> assertEquals(
                        new GPT40VisionPreviewModel().name(),
                        nextStage.requestContext.model()
                ),
                () -> executorRemainsUnchanged(
                        this.aiModelStage,
                        nextStage
                )
        );
    }

    @Test
    public void testPoweredByManualChoice_expectManualSelection() {
        var nextStage = this.aiModelStage.poweredBy(MODEL_MANUAL_CHOICE);

        assertAll(
                () -> assertEquals(
                        new GPT40VisionPreviewModel().name(),
                        nextStage.requestContext.model()
                ),
                () -> executorRemainsUnchanged(
                        this.aiModelStage,
                        nextStage
                )
        );
    }
}
