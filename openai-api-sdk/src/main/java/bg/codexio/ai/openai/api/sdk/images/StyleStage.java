package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.images.Style;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import bg.codexio.ai.openai.api.sdk.IntermediateStage;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;

import java.util.function.Function;

/**
 * A stage to choose the style of the images generated.
 * <p>Currently, this is supported only by <b>dall-e-3</b> model, therefore
 * applicable only for <b>creating images</b>.
 */
public class StyleStage<R extends ImageRequest, E extends RuntimeSelectionStage>
        extends ImageConfigurationStage<R>
        implements IntermediateStage {

    private final Function<ImageRequestBuilder<R>, E> runtimeSelector;

    StyleStage(
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
     * Sets the style to <b>vivid</b>.
     * Causes the model to lean towards generating hyper-real and dramatic
     * images.
     * </p>
     *
     * @return {@link QualityStage} to configure the quality of the images.
     */
    public QualityStage<R, E> vivid() {
        return this.withStyle(Style.HYPER_REAL);
    }

    /**
     * <p>
     * Sets the style to <b>natural</b>.
     * Causes the model to produce more natural, less hyper-real looking
     * images, closer to the <b>dall-e-2</b> style.
     * </p>
     *
     * @return {@link QualityStage} to configure the quality of the images.
     */
    public QualityStage<R, E> natural() {
        return this.withStyle(Style.NATURAL);
    }

    /**
     * <p>
     * Sets the style to <b>natural</b>.
     * Causes the model to produce more natural, less hyper-real looking
     * images, closer to the <b>dall-e-2</b> style.
     * </p>
     *
     * @return {@link QualityStage} to configure the quality of the images.
     */
    public QualityStage<R, E> unstyled() {
        return this.natural();
    }

    private QualityStage<R, E> withStyle(Style style) {
        return new QualityStage<>(
                this.executor,
                this.builder.withStyle(style.val()),
                this.runtimeSelector
        );
    }
}
