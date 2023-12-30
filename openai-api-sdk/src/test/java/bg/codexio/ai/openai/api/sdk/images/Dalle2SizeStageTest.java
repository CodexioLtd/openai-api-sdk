package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.models.dalle.DallE20;
import bg.codexio.ai.openai.api.payload.images.Dimensions;
import bg.codexio.ai.openai.api.payload.images.request.EditImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.images.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Dalle2SizeStageTest {

    private Dalle2SizeStage<EditImageRequest,
            PromptfulImagesRuntimeSelectionStage<EditImageRequest>> dalle2SizeStage;

    @BeforeEach
    public void setUp() {
        this.dalle2SizeStage = new Dalle2SizeStage<>(
                EDIT_IMAGE_HTTP_EXECUTOR,
                ImageRequestBuilder.<EditImageRequest>builder()
                                   .withSpecificRequestCreator(EDIT_IMAGE_REQUEST_FUNCTION)
                                   .withModel(new DallE20().name())
                                   .withN(TEST_CHOICES),
                PROMPTFUL_EDIT_RUNTIME_SELECTION

        );
    }

    @Test
    public void testSmallSquare_expectSquare256() {
        var nextStage = this.dalle2SizeStage.smallSquare();

        this.testSizeChoice(
                nextStage,
                Dimensions.SQUARE_256.val()
        );
    }

    @Test
    public void testMediumSquare_expectSquare512() {
        var nextStage = this.dalle2SizeStage.mediumSquare();

        this.testSizeChoice(
                nextStage,
                Dimensions.SQUARE_512.val()
        );
    }

    @Test
    public void testBigSquare_expectSquare1024() {
        var nextStage = this.dalle2SizeStage.bigSquare();

        this.testSizeChoice(
                nextStage,
                Dimensions.SQUARE_1024.val()
        );
    }

    public void testSizeChoice(
            ImageConfigurationStage<EditImageRequest> nextStage,
            String size
    ) {
        assertAll(
                () -> assertEquals(
                        size,
                        nextStage.builder.size()
                ),
                () -> this.oldValuesRemainUnchanged(nextStage)
        );
    }

    public void oldValuesRemainUnchanged(ImageConfigurationStage<EditImageRequest> nextStage) {
        assertAll(
                () -> executorRemainsUnchanged(
                        this.dalle2SizeStage,
                        nextStage
                ),
                () -> modelRemainsUnchanged(
                        this.dalle2SizeStage,
                        nextStage
                ),
                () -> choicesRemainsUnchanged(
                        this.dalle2SizeStage,
                        nextStage
                )
        );
    }
}
