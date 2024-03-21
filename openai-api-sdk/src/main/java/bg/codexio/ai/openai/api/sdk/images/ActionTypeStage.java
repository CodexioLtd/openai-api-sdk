package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.images.CreateImageHttpExecutor;
import bg.codexio.ai.openai.api.http.images.EditImageHttpExecutor;
import bg.codexio.ai.openai.api.http.images.ImageVariationHttpExecutor;
import bg.codexio.ai.openai.api.payload.images.request.CreateImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.EditImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageVariationRequest;
import bg.codexio.ai.openai.api.sdk.StartStage;

import java.io.File;

/**
 * A stage to choose the <b>Images</b> related action desired
 * Current version includes following possibilities:
 * <ul>
 *     <li>Creating images</li>
 *     <li>Editing images</li>
 *     <li>Generating images variations</li>
 * </ul>
 */
public class ActionTypeStage<R extends ImageRequest>
        implements StartStage {

    final CreateImageHttpExecutor createExecutor;
    final EditImageHttpExecutor editExecutor;
    final ImageVariationHttpExecutor variationExecutor;

    ActionTypeStage(
            CreateImageHttpExecutor createExecutor,
            EditImageHttpExecutor editExecutor,
            ImageVariationHttpExecutor variationExecutor
    ) {
        this.createExecutor = createExecutor;
        this.editExecutor = editExecutor;
        this.variationExecutor = variationExecutor;
    }

    /**
     * Creates images given a prompt. <br>
     * Corresponds with
     * <a href="https://platform.openai.com/docs/api-reference/images/create">
     * /v1/images/generations
     * </a>
     * endpoint
     */
    public AIModelStage<CreateImageRequest,
            PromptfulImagesRuntimeSelectionStage<CreateImageRequest>> creating() {
        return new CreatingActionTypeStage(this.createExecutor).creating();
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
    public EditingMaskStage<EditImageRequest, PromptfulImagesRuntimeSelectionStage<EditImageRequest>> editingStandart(File image) {
        return new EditingActionTypeStage(this.editExecutor).editing(image)
                                                            .standart();
    }

    public EditingMaskConfigurationStage<EditImageRequest,
            PromptfulImagesRuntimeSelectionStage<EditImageRequest>> editing(File image) {
        return new EditingActionTypeStage(this.editExecutor).editing(image);
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
        return new VariatingActionTypeStage(this.variationExecutor).another(image);
    }

}
