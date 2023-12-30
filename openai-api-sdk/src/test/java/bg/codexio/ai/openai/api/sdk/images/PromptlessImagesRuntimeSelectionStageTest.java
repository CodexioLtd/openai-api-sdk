package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.models.dalle.DallE30;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import bg.codexio.ai.openai.api.payload.images.request.ImageVariationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.images.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PromptlessImagesRuntimeSelectionStageTest {

    private PromptlessImagesRuntimeSelectionStage<ImageVariationRequest> promptlessImagesRuntimeSelectionStage;

    @BeforeEach
    public void setUp() {
        this.promptlessImagesRuntimeSelectionStage =
                new PromptlessImagesRuntimeSelectionStage<>(
                IMAGE_VARIATION_HTTP_EXECUTOR,
                ImageRequestBuilder.<ImageVariationRequest>builder()
                                   .withSpecificRequestCreator(IMAGE_VARIATION_REQUEST_FUNCTION)
                                   .withModel(new DallE30().name())
                                   .withN(1)
                                   .withSize(TEST_CUSTOM_SIZE)
        );
    }

    @Test
    public void testAsync_expectCorrectBuilder() {
        var nextStage = this.promptlessImagesRuntimeSelectionStage.async();

        this.previousValuesRemainUnchanged(nextStage);
    }

    @Test
    public void testImmediate_expectCorrectBuilder() {
        var nextStage = this.promptlessImagesRuntimeSelectionStage.immediate();

        this.previousValuesRemainUnchanged(nextStage);
    }

    @Test
    public void testReactive_expectCorrectBuilder() {
        var nextStage = this.promptlessImagesRuntimeSelectionStage.reactive();

        this.previousValuesRemainUnchanged(nextStage);
    }

    private void previousValuesRemainUnchanged(ImageConfigurationStage<ImageVariationRequest> nextStage) {
        assertAll(
                () -> executorRemainsUnchanged(
                        this.promptlessImagesRuntimeSelectionStage,
                        nextStage
                ),
                () -> modelRemainsUnchanged(
                        this.promptlessImagesRuntimeSelectionStage,
                        nextStage
                ),
                () -> choicesRemainsUnchanged(
                        this.promptlessImagesRuntimeSelectionStage,
                        nextStage
                ),
                () -> sizeRemainsUnchanged(
                        this.promptlessImagesRuntimeSelectionStage,
                        nextStage
                ),
                () -> qualityRemainsUnchanged(
                        this.promptlessImagesRuntimeSelectionStage,
                        nextStage
                ),
                () -> styleRemainsUnchanged(
                        this.promptlessImagesRuntimeSelectionStage,
                        nextStage
                )
        );
    }
}
