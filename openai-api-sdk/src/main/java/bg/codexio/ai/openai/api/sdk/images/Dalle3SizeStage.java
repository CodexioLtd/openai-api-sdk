package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.images.Dimensions;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import bg.codexio.ai.openai.api.sdk.IntermediateStage;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;

import java.util.function.Function;

/**
 * A stage to choose the dimensions of the image generated supported by
 * <b>dall-e-3</b>
 * <p>
 * Currently, the possible options are
 *     <ul>
 *         <li><b>256x256</b></li>
 *         <li><b>512x512</b></li>
 *         <li><b>1024x1024</b></li>
 *     </ul>
 * </p>
 */
public class Dalle3SizeStage<R extends ImageRequest,
        E extends RuntimeSelectionStage>
        extends ImageConfigurationStage<R>
        implements IntermediateStage {

    private final Function<ImageRequestBuilder<R>, E> runtimeSelector;

    Dalle3SizeStage(
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

    private FormatStage<R, E> withSize(String size) {
        return new FormatStage<>(
                this.executor,
                this.builder.withSize(size),
                this.runtimeSelector
        );
    }

    /**
     * Square with dimensions <b>1024x1024 px.</b>
     *
     * @return {@link FormatStage} to configure the response format of the
     * images.
     */
    public FormatStage<R, E> square() {
        return this.withSize(Dimensions.SQUARE_1024.val());
    }

    /**
     * Landscape with dimensions <b>1792x1024 px.</b>
     *
     * @return {@link FormatStage} to configure the response format of the
     * images.
     */
    public FormatStage<R, E> landscape() {
        return this.withSize(Dimensions.LANDSCAPE_1792.val());
    }

    /**
     * Landscape with dimensions <b>1024x1792 px.</b>
     *
     * @return {@link FormatStage} to configure the response format of the
     * images.
     */
    public FormatStage<R, E> portrait() {
        return this.withSize(Dimensions.PORTRAIT_1792.val());
    }
}
