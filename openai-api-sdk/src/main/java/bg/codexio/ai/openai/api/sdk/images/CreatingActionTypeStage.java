package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.images.CreateImageHttpExecutor;
import bg.codexio.ai.openai.api.payload.images.request.CreateImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import bg.codexio.ai.openai.api.sdk.StartStage;

/**
 * Explicit marker action type stage for flows where only creating is
 * possible <br>
 * E.g. when only a {@link CreateImageHttpExecutor} has been provided
 */
public class CreatingActionTypeStage
        implements StartStage {

    final CreateImageHttpExecutor createExecutor;

    CreatingActionTypeStage(CreateImageHttpExecutor createExecutor) {
        this.createExecutor = createExecutor;
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
        return new AIModelStage<>(
                this.createExecutor,
                ImageRequestBuilder.<CreateImageRequest>builder()
                                   .withSpecificRequestCreator(imageRequestBuilder -> new CreateImageRequest(
                                           imageRequestBuilder.prompt(),
                                           imageRequestBuilder.model(),
                                           imageRequestBuilder.n(),
                                           imageRequestBuilder.quality(),
                                           imageRequestBuilder.responseFormat(),
                                           imageRequestBuilder.size(),
                                           imageRequestBuilder.style(),
                                           imageRequestBuilder.user()
                                   )),
                builder -> new PromptfulImagesRuntimeSelectionStage<>(
                        this.createExecutor,
                        builder
                )
        );
    }
}
