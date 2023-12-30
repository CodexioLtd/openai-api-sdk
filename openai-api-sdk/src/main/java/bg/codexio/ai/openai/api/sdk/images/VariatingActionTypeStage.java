package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.images.ImageVariationHttpExecutor;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import bg.codexio.ai.openai.api.payload.images.request.ImageVariationRequest;
import bg.codexio.ai.openai.api.sdk.StartStage;

import java.io.File;

/**
 * Explicit marker action type stage for flows where only creating variations
 * is possible <br>
 * E.g. when only a {@link ImageVariationHttpExecutor} has been provided
 */
public class VariatingActionTypeStage
        implements StartStage {

    final ImageVariationHttpExecutor variationExecutor;

    VariatingActionTypeStage(ImageVariationHttpExecutor variationExecutor) {
        this.variationExecutor = variationExecutor;
    }

    /**
     * @param image the original image passed to the model, baseline for the
     *              generated responses
     *              Creates variations of given image. <br>
     *              Corresponds with
     *              <a href="https://platform.openai.com/docs/api-reference/images/createEdit">
     *              /v1/images/variations
     *              </a>
     *              endpoint
     */
    public ChoicesStage<ImageVariationRequest,
            PromptlessImagesRuntimeSelectionStage<ImageVariationRequest>> another(File image) {
        return new AIModelStage<ImageVariationRequest,
                PromptlessImagesRuntimeSelectionStage<ImageVariationRequest>>(
                this.variationExecutor,
                ImageRequestBuilder.<ImageVariationRequest>builder()
                                   .withSpecificRequestCreator(imageRequestBuilder -> new ImageVariationRequest(
                                           imageRequestBuilder.image(),
                                           imageRequestBuilder.prompt(),
                                           imageRequestBuilder.model(),
                                           imageRequestBuilder.n(),
                                           imageRequestBuilder.size(),
                                           imageRequestBuilder.responseFormat(),
                                           imageRequestBuilder.user()
                                   ))
                                   .withImage(image),
                builder -> new PromptlessImagesRuntimeSelectionStage<>(
                        this.variationExecutor,
                        builder
                )
        ).poweredByDallE2();
    }
}
