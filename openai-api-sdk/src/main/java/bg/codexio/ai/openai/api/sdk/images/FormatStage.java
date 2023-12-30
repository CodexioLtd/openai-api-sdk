package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.images.Format;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import bg.codexio.ai.openai.api.sdk.IntermediateStage;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;

import java.util.function.Function;

/**
 * A stage to choose the format of the response images
 */
public class FormatStage<R extends ImageRequest,
        E extends RuntimeSelectionStage>
        extends ImageConfigurationStage<R>
        implements IntermediateStage {

    private final Function<ImageRequestBuilder<R>, E> runtimeSelector;

    FormatStage(
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
     * Sets the image format to <b>url</b>.
     * <p>
     * <b color="orange">Important note:</b> The images are not permanently
     * hosted on the OpenAI servers.
     * If you want to ensure the image is saved, you have to additionally
     * download it.
     * </p>
     *
     * @return {@link ImagesTerminalStage} to choose an API for the image
     * request.
     */
    public ImagesTerminalStage<R, E> expectUrl() {
        return this.withFormat(Format.URL);
    }

    /**
     * Sets the image format to <b>Base 64</b>.
     *
     * @return {@link ImagesTerminalStage} to choose an API for the image
     * request.
     */
    public ImagesTerminalStage<R, E> expectBase64() {
        return this.withFormat(Format.BASE_64);
    }

    private ImagesTerminalStage<R, E> withFormat(Format format) {
        return new ImagesTerminalStage<>(
                this.executor,
                this.builder.withResponseFormat(format.val()),
                this.runtimeSelector
        );
    }
}
