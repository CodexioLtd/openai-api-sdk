package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import bg.codexio.ai.openai.api.sdk.IntermediateStage;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;
import bg.codexio.ai.openai.api.sdk.images.operation.ImageOperations;

import java.util.function.Function;

public class EditingMaskConfigurationStage<R extends ImageRequest,
        E extends RuntimeSelectionStage>
        extends ImageConfigurationStage<R>
        implements IntermediateStage {

    final Function<ImageRequestBuilder<R>, E> runtimeSelector;

    public EditingMaskConfigurationStage(
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


    public EditingMaskStage<R, E> standart() {
        return new EditingMaskStage<>(
                this.executor,
                this.builder,
                this.runtimeSelector
        );
    }

    public EditingMaskStage<R, E> operations(ImageOperations imageOperations) {
        return new EditingMaskStage<>(
                this.executor,
                this.builder,
                this.runtimeSelector,
                imageOperations
        );
    }
}