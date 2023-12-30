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
 * <b>dall-e-2</b>
 * <p>
 * Currently, <b>dall-e-2</b> supports only square sizes with 3 dimensions
 * options
 *     <ul>
 *         <li><b>256x256</b></li>
 *         <li><b>512x512</b></li>
 *         <li><b>1024x1024</b></li>
 *     </ul>
 * </p>
 */
public class Dalle2SizeStage<R extends ImageRequest,
        E extends RuntimeSelectionStage>
        extends ImageConfigurationStage<R>
        implements IntermediateStage {

    private final Function<ImageRequestBuilder<R>, E> runtimeSelector;

    Dalle2SizeStage(
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
     * The smallest square with dimensions <b>256x256 px.</b>
     *
     * @return {@link FormatStage} to configure the response format of the
     * images.
     */
    public FormatStage<R, E> smallSquare() {
        return this.withSize(Dimensions.SQUARE_256.val());
    }

    /**
     * The medium square with dimensions <b>256x256 px.</b>
     *
     * @return {@link FormatStage} to configure the response format of the
     * images.
     */
    public FormatStage<R, E> mediumSquare() {
        return this.withSize(Dimensions.SQUARE_512.val());
    }

    /**
     * The biggest square with dimensions <b>256x256 px.</b>
     *
     * @return {@link FormatStage} to configure the response format of the
     * images.
     */
    public FormatStage<R, E> bigSquare() {
        return this.withSize(Dimensions.SQUARE_1024.val());
    }
}
