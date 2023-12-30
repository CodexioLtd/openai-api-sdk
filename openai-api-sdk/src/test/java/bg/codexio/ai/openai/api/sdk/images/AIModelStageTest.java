package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.payload.images.request.CreateImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.images.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AIModelStageTest {

    private AIModelStage<CreateImageRequest,
            PromptfulImagesRuntimeSelectionStage<CreateImageRequest>> aIModelStage;

    @BeforeEach
    public void setUp() {
        this.aIModelStage = new AIModelStage<>(
                CREATE_IMAGE_HTTP_EXECUTOR,
                ImageRequestBuilder.<CreateImageRequest>builder()
                                   .withSpecificRequestCreator(CREATE_IMAGE_REQUEST_FUNCTION),
                PROMPTFUL_CREATE_RUNTIME_SELECTION
        );
    }

    @Test
    public void testPoweredByDalle2_expectDalle2Model() {
        var nextStage = this.aIModelStage.poweredByDallE2();

        assertAll(
                () -> modelIsDalle2(nextStage),
                () -> createImageRequestIsPopulated(nextStage)
        );
    }

    @Test
    public void testPoweredByDallE3_expectDalle3Model() {
        var nextStage = this.aIModelStage.poweredByDallE3();

        assertAll(
                () -> modelIsDalle3(nextStage),
                () -> choicesSetCorrectly(
                        nextStage,
                        1
                ),
                () -> createImageRequestIsPopulated(nextStage)
        );
    }

    @Test
    public void testDefaultModel_expectDalle2Model() {
        var nextStage = this.aIModelStage.defaultModel();

        assertAll(
                () -> modelIsDalle2(nextStage),
                () -> createImageRequestIsPopulated(nextStage)
        );
    }

}
