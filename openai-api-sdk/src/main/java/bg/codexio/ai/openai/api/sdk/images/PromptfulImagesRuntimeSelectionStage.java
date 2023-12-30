package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;

/**
 * <p>
 * Configures how the HTTP client to act.
 * Whether the requests will be sent in an immediate blocking manner
 * (synchronous)
 * or they will be sent asynchronously via promises or reactive API.
 * </p>
 * This class delegates directly to the next stage, where prompt is required.
 */
public class PromptfulImagesRuntimeSelectionStage<R extends ImageRequest>
        extends ImageConfigurationStage<R>
        implements RuntimeSelectionStage {

    PromptfulImagesRuntimeSelectionStage(
            DefaultOpenAIHttpExecutor<R, ImageDataResponse> executor,
            ImageRequestBuilder<R> builder
    ) {
        super(
                executor,
                builder
        );
    }

    /**
     * Requests will be synchronously blocking without streaming enabled.
     *
     * @return {@link SynchronousApi} to specify the prompts
     */
    @Override
    public SynchronousApi<R> immediate() {
        return new SynchronousApi<>(
                this.executor,
                this.builder
        );
    }

    /**
     * Requests will be asynchronous with promises.
     * No real-time streaming will be enabled.
     *
     * @return {@link AsyncApi} to specify the prompts
     */
    @Override
    public AsyncApi<R> async() {
        return new AsyncApi<>(
                this.executor,
                this.builder
        );
    }

    /**
     * Requests will be asynchronous under reactive API.
     * Encouraged to be used only if a truly reactive
     * environment is present, such as
     * <a href="https://github.com/reactor/reactor-netty">Reactor Netty</a>.
     * No real-time streaming will be enabled.
     *
     * @return {@link ReactiveApi} to specify the prompts
     */
    @Override
    public ReactiveApi<R> reactive() {
        return new ReactiveApi<>(
                this.executor,
                this.builder
        );
    }
}
