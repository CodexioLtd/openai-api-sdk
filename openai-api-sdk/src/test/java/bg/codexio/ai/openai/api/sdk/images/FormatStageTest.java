package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.models.dalle.DallE20;
import bg.codexio.ai.openai.api.payload.images.Format;
import bg.codexio.ai.openai.api.payload.images.request.EditImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.images.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormatStageTest {

    private FormatStage<EditImageRequest,
            PromptfulImagesRuntimeSelectionStage<EditImageRequest>> formatStage;

    @BeforeEach
    public void setUp() {
        this.formatStage = new FormatStage<>(
                EDIT_IMAGE_HTTP_EXECUTOR,
                ImageRequestBuilder.<EditImageRequest>builder()
                                   .withSpecificRequestCreator(EDIT_IMAGE_REQUEST_FUNCTION)
                                   .withModel(new DallE20().name())
                                   .withN(TEST_CHOICES)
                                   .withSize(TEST_CUSTOM_SIZE),
                PROMPTFUL_EDIT_RUNTIME_SELECTION
        );
    }

    @Test
    public void testExpectBase64_expectBuilderWithFormatBase64() {
        var nextStage = this.formatStage.expectBase64();

        this.testFormatChoice(
                nextStage,
                Format.BASE_64
        );
    }

    @Test
    public void testExpectUrl_expectBuilderWithFormatUrl() {
        var nextStage = this.formatStage.expectUrl();

        this.testFormatChoice(
                nextStage,
                Format.URL
        );
    }

    public void testFormatChoice(
            ImageConfigurationStage<EditImageRequest> nextStage,
            Format format
    ) {
        assertAll(
                () -> assertEquals(
                        format.val(),
                        nextStage.builder.responseFormat()
                ),
                () -> this.oldValuesRemainUnchanged(nextStage)
        );
    }

    public void oldValuesRemainUnchanged(ImageConfigurationStage<EditImageRequest> nextStage) {
        assertAll(
                () -> executorRemainsUnchanged(
                        this.formatStage,
                        nextStage
                ),
                () -> modelRemainsUnchanged(
                        this.formatStage,
                        nextStage
                ),
                () -> choicesRemainsUnchanged(
                        this.formatStage,
                        nextStage
                ),
                () -> sizeRemainsUnchanged(
                        this.formatStage,
                        nextStage
                )
        );
    }
}
