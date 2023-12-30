package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;

/**
 * Base for all Image stages
 */
public class ImageConfigurationStage<R extends ImageRequest> {

    protected DefaultOpenAIHttpExecutor<R, ImageDataResponse> executor;
    protected ImageRequestBuilder<R> builder;

    public ImageConfigurationStage(
            DefaultOpenAIHttpExecutor<R, ImageDataResponse> executor,
            ImageRequestBuilder<R> builder
    ) {
        this.executor = executor;
        this.builder = builder;
    }

}
