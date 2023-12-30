package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.payload.vision.request.ImageUrlMessageRequest;
import bg.codexio.ai.openai.api.payload.vision.request.ImageUrlRequest;
import bg.codexio.ai.openai.api.payload.vision.request.MessageContentHolder;
import bg.codexio.ai.openai.api.payload.vision.request.VisionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static bg.codexio.ai.openai.api.sdk.vision.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class VisionRuntimeSelectionStageTest {

    private VisionRuntimeSelectionStage runtimeSelectionStage;

    private static void otherValuesRemainUnchanged(
            VisionConfigurationStage currentStage,
            VisionConfigurationStage nextStage
    ) {
        assertAll(
                () -> modelRemainsUnchanged(nextStage),
                () -> tokensRemainUnchanged(nextStage),
                () -> imageRemainsUnchanged(
                        nextStage,
                        0
                ),
                () -> detailsRemainsUnchanged(
                        nextStage,
                        0
                ),
                () -> executorRemainsUnchanged(
                        currentStage,
                        nextStage
                )
        );
    }

    @BeforeEach
    public void setUp() {
        this.runtimeSelectionStage = new VisionRuntimeSelectionStage(
                TEST_EXECUTOR,
                VisionRequest.empty()
                             .withModel(TEST_MODEL.name())
                             .withTokens(TEST_TOKENS)
                             .withMessages(new MessageContentHolder(List.of(new ImageUrlMessageRequest(new ImageUrlRequest(
                                     TEST_IMAGE,
                                     TEST_ANALYZE
                             )))))
        );
    }

    @Test
    public void testImmediate_expectUnchangedValues() {
        var nextStage = this.runtimeSelectionStage.immediate();

        otherValuesRemainUnchanged(
                this.runtimeSelectionStage,
                nextStage
        );
    }

    @Test
    public void testAsync_expectUnchangedValues() {
        var nextStage = this.runtimeSelectionStage.async();

        otherValuesRemainUnchanged(
                this.runtimeSelectionStage,
                nextStage
        );
    }

    @Test
    public void testReactive_expectUnchangedValues() {
        var nextStage = this.runtimeSelectionStage.reactive();

        otherValuesRemainUnchanged(
                this.runtimeSelectionStage,
                nextStage
        );
    }
}
