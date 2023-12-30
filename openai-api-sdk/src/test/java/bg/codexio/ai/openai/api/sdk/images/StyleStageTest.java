package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.models.dalle.DallE30;
import bg.codexio.ai.openai.api.payload.images.Style;
import bg.codexio.ai.openai.api.payload.images.request.CreateImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.images.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StyleStageTest {

    private StyleStage<CreateImageRequest,
            PromptfulImagesRuntimeSelectionStage<CreateImageRequest>> styleStage;

    @BeforeEach
    public void setUp() {
        this.styleStage = new StyleStage<>(
                CREATE_IMAGE_HTTP_EXECUTOR,
                ImageRequestBuilder.<CreateImageRequest>builder()
                                   .withSpecificRequestCreator(CREATE_IMAGE_REQUEST_FUNCTION)
                                   .withModel(new DallE30().name())
                                   .withN(1)
                                   .withSize(TEST_CUSTOM_SIZE),
                PROMPTFUL_CREATE_RUNTIME_SELECTION
        );
    }

    @Test
    public void testUnstyled_expectStyleNatural() {
        var nextStage = this.styleStage.unstyled();

        this.testStyleChoice(
                nextStage,
                Style.NATURAL
        );
    }

    @Test
    public void testNatural_expectStyleNatural() {
        var nextStage = this.styleStage.natural();

        this.testStyleChoice(
                nextStage,
                Style.NATURAL
        );
    }

    @Test
    public void testVivid_expectStyleHyperReal() {
        var nextStage = this.styleStage.vivid();

        this.testStyleChoice(
                nextStage,
                Style.HYPER_REAL
        );
    }

    public void testStyleChoice(
            ImageConfigurationStage<CreateImageRequest> nextStage,
            Style style
    ) {
        assertAll(
                () -> assertEquals(
                        style.val(),
                        nextStage.builder.style()
                ),
                () -> this.oldValuesRemainUnchanged(nextStage)
        );
    }

    public void oldValuesRemainUnchanged(ImageConfigurationStage<CreateImageRequest> nextStage) {
        assertAll(
                () -> executorRemainsUnchanged(
                        this.styleStage,
                        nextStage
                ),
                () -> modelRemainsUnchanged(
                        this.styleStage,
                        nextStage
                ),
                () -> choicesRemainsUnchanged(
                        this.styleStage,
                        nextStage
                ),
                () -> sizeRemainsUnchanged(
                        this.styleStage,
                        nextStage
                )
        );
    }
}
