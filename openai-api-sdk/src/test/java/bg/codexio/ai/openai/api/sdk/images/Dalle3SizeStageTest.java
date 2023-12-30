package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.models.dalle.DallE30;
import bg.codexio.ai.openai.api.payload.images.Dimensions;
import bg.codexio.ai.openai.api.payload.images.request.CreateImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.images.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Dalle3SizeStageTest {

    private Dalle3SizeStage<CreateImageRequest,
            PromptfulImagesRuntimeSelectionStage<CreateImageRequest>> dalle3SizeStage;

    @BeforeEach
    public void setUp() {
        this.dalle3SizeStage = new Dalle3SizeStage<>(
                CREATE_IMAGE_HTTP_EXECUTOR,
                ImageRequestBuilder.<CreateImageRequest>builder()
                                   .withSpecificRequestCreator(CREATE_IMAGE_REQUEST_FUNCTION)
                                   .withModel(new DallE30().name())
                                   .withN(TEST_CHOICES),
                PROMPTFUL_CREATE_RUNTIME_SELECTION

        );
    }

    @Test
    public void testSquare_expectSquare1024() {
        var nextStage = this.dalle3SizeStage.square();

        this.testSizeChoice(
                nextStage,
                Dimensions.SQUARE_1024.val()
        );
    }

    @Test
    public void testPortrait_expectPortrait1792() {
        var nextStage = this.dalle3SizeStage.portrait();

        this.testSizeChoice(
                nextStage,
                Dimensions.PORTRAIT_1792.val()
        );
    }

    @Test
    public void testLandscape_expectLandscape1792() {
        var nextStage = this.dalle3SizeStage.landscape();

        this.testSizeChoice(
                nextStage,
                Dimensions.LANDSCAPE_1792.val()
        );
    }

    public void testSizeChoice(
            ImageConfigurationStage<CreateImageRequest> nextStage,
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

    public void oldValuesRemainUnchanged(ImageConfigurationStage<CreateImageRequest> nextStage) {
        assertAll(
                () -> executorRemainsUnchanged(
                        this.dalle3SizeStage,
                        nextStage
                ),
                () -> modelRemainsUnchanged(
                        this.dalle3SizeStage,
                        nextStage
                ),
                () -> choicesRemainsUnchanged(
                        this.dalle3SizeStage,
                        nextStage
                )
        );
    }
}
