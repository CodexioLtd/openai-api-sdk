package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.images.Quality;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import bg.codexio.ai.openai.api.sdk.IntermediateStage;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;

import java.util.function.Function;

/**
 * A stage to choose the quality of the images generated.
 * <p>Currently, this is supported only by <b>dall-e-3</b> model, therefore
 * applicable only for <b>creating images</b>.
 */
public class QualityStage<R extends ImageRequest,
        E extends RuntimeSelectionStage>
        extends ImageConfigurationStage<R>
        implements IntermediateStage {
    private final Function<ImageRequestBuilder<R>, E> runtimeSelector;

    QualityStage(
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
     * <p>
     * Sets the quality to <b>HD</b>. The images will be with finer details
     * and greater consistency across the image.
     * This might increase the tokens usage.
     * </p>
     *
     * @return {@link Dalle3SizeStage} to configure the dimensions of the
     * images.
     */
    public Dalle3SizeStage<R, E> highDefinitioned() {
        return this.withQuality(Quality.HIGH_QUALITY);
    }

    /**
     * <p>
     * Sets the quality to <b>Standard</b>.
     * This might increase the tokens usage.
     * </p>
     *
     * @return {@link Dalle3SizeStage} to configure the dimensions of the
     * images.
     */
    public Dalle3SizeStage<R, E> standardQuality() {
        return this.withQuality(Quality.STANDARD);
    }

    private Dalle3SizeStage<R, E> withQuality(Quality quality) {
        return new Dalle3SizeStage<>(
                this.executor,
                this.builder.withQuality(quality.val()),
                this.runtimeSelector
        );
    }
}
