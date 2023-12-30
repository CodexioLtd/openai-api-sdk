package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.models.dalle.DallE30;
import bg.codexio.ai.openai.api.payload.images.Quality;
import bg.codexio.ai.openai.api.payload.images.Style;
import bg.codexio.ai.openai.api.payload.images.request.CreateImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.images.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PromptfulImagesRuntimeSelectionStageTest {

    private PromptfulImagesRuntimeSelectionStage<CreateImageRequest> promptfulImagesRuntimeSelectionStage;

    @BeforeEach
    public void setUp() {
        this.promptfulImagesRuntimeSelectionStage =
                new PromptfulImagesRuntimeSelectionStage<>(
                CREATE_IMAGE_HTTP_EXECUTOR,
                ImageRequestBuilder.<CreateImageRequest>builder()
                                   .withSpecificRequestCreator(CREATE_IMAGE_REQUEST_FUNCTION)
                                   .withModel(new DallE30().name())
                                   .withN(1)
                                   .withSize(TEST_CUSTOM_SIZE)
                                   .withQuality(Quality.HIGH_QUALITY.val())
                                   .withStyle(Style.HYPER_REAL.val())
        );
    }

    @Test
    public void testAsync_expectCorrectBuilder() {
        var nextStage = this.promptfulImagesRuntimeSelectionStage.async();

        this.previousValuesRemainUnchanged(nextStage);
    }

    @Test
    public void testImmediate_expectCorrectBuilder() {
        var nextStage = this.promptfulImagesRuntimeSelectionStage.immediate();

        this.previousValuesRemainUnchanged(nextStage);
    }

    @Test
    public void testReactive_expectCorrectBuilder() {
        var nextStage = this.promptfulImagesRuntimeSelectionStage.reactive();

        this.previousValuesRemainUnchanged(nextStage);
    }

    private void previousValuesRemainUnchanged(ImageConfigurationStage<CreateImageRequest> nextStage) {
        assertAll(
                () -> executorRemainsUnchanged(
                        this.promptfulImagesRuntimeSelectionStage,
                        nextStage
                ),
                () -> modelRemainsUnchanged(
                        this.promptfulImagesRuntimeSelectionStage,
                        nextStage
                ),
                () -> choicesRemainsUnchanged(
                        this.promptfulImagesRuntimeSelectionStage,
                        nextStage
                ),
                () -> sizeRemainsUnchanged(
                        this.promptfulImagesRuntimeSelectionStage,
                        nextStage
                ),
                () -> qualityRemainsUnchanged(
                        this.promptfulImagesRuntimeSelectionStage,
                        nextStage
                ),
                () -> styleRemainsUnchanged(
                        this.promptfulImagesRuntimeSelectionStage,
                        nextStage
                )
        );
    }
}
