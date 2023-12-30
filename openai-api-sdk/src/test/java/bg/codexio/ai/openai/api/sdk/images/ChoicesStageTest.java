package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.models.dalle.DallE20;
import bg.codexio.ai.openai.api.payload.images.request.EditImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.images.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChoicesStageTest {
    private ChoicesStage<EditImageRequest,
            PromptfulImagesRuntimeSelectionStage<EditImageRequest>> choicesStageStage;

    @BeforeEach
    public void setUp() {
        this.choicesStageStage = new ChoicesStage<>(
                EDIT_IMAGE_HTTP_EXECUTOR,
                ImageRequestBuilder.<EditImageRequest>builder()
                                   .withSpecificRequestCreator(EDIT_IMAGE_REQUEST_FUNCTION)
                                   .withModel(new DallE20().name()),
                PROMPTFUL_EDIT_RUNTIME_SELECTION
        );
    }

    @Test
    public void testWithChoices_manualInput_expectChoicesAsSelected() {
        var nextStage = this.choicesStageStage.withChoices(5);

        this.testChoicesChoice(
                nextStage,
                5
        );
    }

    @Test
    public void testSingleChoice_expectOnlyOneChoice() {
        var nextStage = this.choicesStageStage.singleChoice();

        this.testChoicesChoice(
                nextStage,
                1
        );
    }

    public void testChoicesChoice(
            ImageConfigurationStage<EditImageRequest> nextStage,
            Integer choices
    ) {
        assertAll(
                () -> assertEquals(
                        choices,
                        nextStage.builder.n()
                ),
                () -> oldValuesRemainUnchanged(nextStage)
        );
    }

    public void oldValuesRemainUnchanged(ImageConfigurationStage<EditImageRequest> nextStage) {
        assertAll(
                () -> executorRemainsUnchanged(
                        this.choicesStageStage,
                        nextStage
                ),
                () -> modelRemainsUnchanged(
                        this.choicesStageStage,
                        nextStage
                )
        );
    }
}
