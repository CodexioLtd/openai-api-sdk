package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import bg.codexio.ai.openai.api.sdk.RuntimeExecutor;

/**
 * Synchronous API to add a final instruction and proceed with the HTTP call.
 */
public class SynchronousApi<R extends ImageRequest>
        extends ImageConfigurationStage<R>
        implements RuntimeExecutor,
                   ImagesPromptStage<SynchronousExecutor<R>> {

    SynchronousApi(
            DefaultOpenAIHttpExecutor<R, ImageDataResponse> executor,
            ImageRequestBuilder<R> builder
    ) {
        super(
                executor,
                builder
        );
    }

    /**
     * @param prompt the instruction for the model, most important and
     *               mandatory part of the configuration
     * @return {@link SynchronousExecutor} to handle the HTTP response
     */
    public SynchronousExecutor<R> generate(String prompt) {
        return new SynchronousExecutor<R>(
                this.executor,
                this.builder.withPrompt(prompt)
        );
    }
}
