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

public class ImagesTerminalStageTest {

    private ImagesTerminalStage<CreateImageRequest,
            PromptfulImagesRuntimeSelectionStage<CreateImageRequest>> imagesTerminalStage;

    @BeforeEach
    public void setUp() {
        this.imagesTerminalStage = new ImagesTerminalStage<>(
                CREATE_IMAGE_HTTP_EXECUTOR,
                ImageRequestBuilder.<CreateImageRequest>builder()
                                   .withSpecificRequestCreator(CREATE_IMAGE_REQUEST_FUNCTION)
                                   .withModel(new DallE30().name())
                                   .withN(1)
                                   .withSize(TEST_CUSTOM_SIZE)
                                   .withQuality(Quality.HIGH_QUALITY.val())
                                   .withStyle(Style.HYPER_REAL.val()),
                PROMPTFUL_CREATE_RUNTIME_SELECTION
        );
    }

    @Test
    public void testAndRespond_expectCorrectBuilder() {
        var nextStage = this.imagesTerminalStage.andRespond();

        assertAll(
                () -> executorRemainsUnchanged(
                        this.imagesTerminalStage,
                        nextStage
                ),
                () -> modelRemainsUnchanged(
                        this.imagesTerminalStage,
                        nextStage
                ),
                () -> choicesRemainsUnchanged(
                        this.imagesTerminalStage,
                        nextStage
                ),
                () -> sizeRemainsUnchanged(
                        this.imagesTerminalStage,
                        nextStage
                ),
                () -> qualityRemainsUnchanged(
                        this.imagesTerminalStage,
                        nextStage
                ),
                () -> styleRemainsUnchanged(
                        this.imagesTerminalStage,
                        nextStage
                )
        );
    }
}
