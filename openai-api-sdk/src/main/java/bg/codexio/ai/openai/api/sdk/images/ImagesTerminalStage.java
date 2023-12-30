package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;
import bg.codexio.ai.openai.api.sdk.TerminalStage;

import java.util.function.Function;

/**
 * Marker stage for finishing the configurations of the prompt. <br>
 * Leads to the second stage of configurations for the HTTP client behaviour.
 */
public class ImagesTerminalStage<R extends ImageRequest,
        E extends RuntimeSelectionStage>
        extends ImageConfigurationStage<R>
        implements TerminalStage {

    private final Function<ImageRequestBuilder<R>, E> runtimeSelector;


    ImagesTerminalStage(
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
     * @return {@link PromptfulImagesRuntimeSelectionStage} to choose one of
     * the three possible HTTP client behaviours
     * <ul>
     *     <li>Synchronous</li>
     *     <li>Async</li>
     *     <li>Reactive</li>
     * </ul>
     */
    @Override
    public E andRespond() {
        return this.runtimeSelector.apply(this.builder);
    }
}
