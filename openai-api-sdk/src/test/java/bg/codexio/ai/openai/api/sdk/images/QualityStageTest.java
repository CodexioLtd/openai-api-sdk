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
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QualityStageTest {

    private QualityStage<CreateImageRequest,
            PromptfulImagesRuntimeSelectionStage<CreateImageRequest>> qualityStage;

    @BeforeEach
    public void setUp() {
        this.qualityStage = new QualityStage<>(
                CREATE_IMAGE_HTTP_EXECUTOR,
                ImageRequestBuilder.<CreateImageRequest>builder()
                                   .withSpecificRequestCreator(CREATE_IMAGE_REQUEST_FUNCTION)
                                   .withModel(new DallE30().name())
                                   .withN(1)
                                   .withSize(TEST_CUSTOM_SIZE)
                                   .withStyle(Style.HYPER_REAL.val()),
                PROMPTFUL_CREATE_RUNTIME_SELECTION

        );
    }

    @Test
    public void testHighDefinitioned_expectHighQuality() {
        var nextStage = this.qualityStage.highDefinitioned();

        this.testQualityChoice(
                nextStage,
                Quality.HIGH_QUALITY
        );
    }

    @Test
    public void testStandardQuality_expectStandard() {
        var nextStage = this.qualityStage.standardQuality();

        this.testQualityChoice(
                nextStage,
                Quality.STANDARD
        );
    }

    public void testQualityChoice(
            ImageConfigurationStage<CreateImageRequest> nextStage,
            Quality quality
    ) {
        assertAll(
                () -> assertEquals(
                        quality.val(),
                        nextStage.builder.quality()
                ),
                () -> this.oldValuesRemainUnchanged(nextStage)
        );
    }

    public void oldValuesRemainUnchanged(ImageConfigurationStage<CreateImageRequest> nextStage) {
        assertAll(
                () -> executorRemainsUnchanged(
                        this.qualityStage,
                        nextStage
                ),
                () -> modelRemainsUnchanged(
                        this.qualityStage,
                        nextStage
                ),
                () -> choicesRemainsUnchanged(
                        this.qualityStage,
                        nextStage
                ),
                () -> sizeRemainsUnchanged(
                        this.qualityStage,
                        nextStage
                )
        );
    }
}
