package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import bg.codexio.ai.openai.api.sdk.IntermediateStage;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;

import java.util.function.Function;

/**
 * A stage to choose the number of the images generated.
 * <p>Currently, this is supported only by <b>dall-e-2</b> model
 */
public class ChoicesStage<R extends ImageRequest,
        E extends RuntimeSelectionStage>
        extends ImageConfigurationStage<R>
        implements IntermediateStage {

    final Function<ImageRequestBuilder<R>, E> runtimeSelector;

    ChoicesStage(
            DefaultOpenAIHttpExecutor<R, ImageDataResponse> executor,
            ImageRequestBuilder<R> builder,
            Function<ImageRequestBuilder<R>, E> runtimeSelector
    ) {
        super(
                executor,
                builder
        );
        this.runtimeSelector = runtimeSelector;
    }

    /**
     * @param choices the desired number of the images output
     *                <p>
     *                Sets the number of generated images to the desired one.
     *                <br>
     *                <b color="orange">Important note:</b> The min count is
     *                <b color="orange">1</b> and the max count is <b
     *                color="orange">10</b>. If a different count is passed,
     *                it will result in error is the API response.
     *                </p>
     * @return {@link Dalle2SizeStage} to configure the dimensions of the
     * images.
     */
    public Dalle2SizeStage<R, E> withChoices(Integer choices) {
        return new Dalle2SizeStage<>(
                this.executor,
                this.builder.withN(choices),
                this.runtimeSelector
        );
    }

    /**
     * Sets the number of generated images to one only.
     *
     * @return {@link Dalle2SizeStage} to configure the dimensions of the
     * images.
     */
    public Dalle2SizeStage<R, E> singleChoice() {
        return this.withChoices(1);
    }
}
