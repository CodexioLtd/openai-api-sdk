package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.images.EditImageHttpExecutor;
import bg.codexio.ai.openai.api.payload.images.request.EditImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import bg.codexio.ai.openai.api.sdk.StartStage;

import java.io.File;

/**
 * Explicit marker action type stage for flows where only editing is possible
 * <br>
 * E.g. when only a {@link EditImageHttpExecutor} has been provided
 */
public class EditingActionTypeStage
        implements StartStage {

    final EditImageHttpExecutor editExecutor;

    EditingActionTypeStage(EditImageHttpExecutor editExecutor) {
        this.editExecutor = editExecutor;
    }

    /**
     * @param image the original image passed to the model, baseline for the
     *              generated responses
     *              Creates edited or extended image given an original image
     *              and a prompt. <br>
     *              <p>
     *              <b color="orange">Important note:</b>
     *              The image must be <b>a valid PNG file, less than 4MB, and
     *              square</b>.
     *              If mask is not provided (see {@link EditingMaskStage}),
     *              image must have transparency, which will be used as the
     *              mask.
     *              </p>
     *              Corresponds with
     *              <a href="https://platform.openai.com/docs/api-reference/images/createEdit">
     *              /v1/images/edits
     *              </a>
     *              endpoint
     */
    public EditingMaskConfigurationStage<EditImageRequest,
            PromptfulImagesRuntimeSelectionStage<EditImageRequest>> editing(File image) {
        return new EditingMaskConfigurationStage<>(
                this.editExecutor,
                ImageRequestBuilder.<EditImageRequest>builder()
                                   .withSpecificRequestCreator(imageRequestBuilder -> new EditImageRequest(
                                           imageRequestBuilder.image(),
                                           imageRequestBuilder.prompt(),
                                           imageRequestBuilder.mask(),
                                           imageRequestBuilder.model(),
                                           imageRequestBuilder.n(),
                                           imageRequestBuilder.size(),
                                           imageRequestBuilder.responseFormat(),
                                           imageRequestBuilder.user()
                                   ))
                                   .withImage(image),
                builder -> new PromptfulImagesRuntimeSelectionStage<>(
                        this.editExecutor,
                        builder
                )
        );
    }
}
