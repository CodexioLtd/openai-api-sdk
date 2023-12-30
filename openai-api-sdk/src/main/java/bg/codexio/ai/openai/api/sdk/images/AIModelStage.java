package bg.codexio.ai.openai.api.sdk.images;

import bg.codexio.ai.openai.api.http.DefaultOpenAIHttpExecutor;
import bg.codexio.ai.openai.api.models.dalle.DallE20;
import bg.codexio.ai.openai.api.models.dalle.DallE30;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequest;
import bg.codexio.ai.openai.api.payload.images.request.ImageRequestBuilder;
import bg.codexio.ai.openai.api.payload.images.response.ImageDataResponse;
import bg.codexio.ai.openai.api.sdk.IntermediateStage;
import bg.codexio.ai.openai.api.sdk.RuntimeSelectionStage;

import java.util.function.Function;

/**
 * A stage to choose a model supported by
 * <a href="https://platform.openai.com/docs/models/model-endpoint-compatibility">
 * /v1/chat/completions model endpoint compatibility
 * </a>.
 * <p>
 * As for the current version of the API, this step is supported only for
 * <b>creating images</b>. All other images related actions support
 * <b>dall-e-2</b> only.
 * </p>
 */
public class AIModelStage<R extends ImageRequest,
        E extends RuntimeSelectionStage>
        extends ImageConfigurationStage<R>
        implements IntermediateStage {

    /**
     * Delegate for {@link RuntimeSelectionStage} as it is
     * incepted in early stage, but only created at the end
     * of the flow. Hence, the delegate has to be maintained
     * through all the intermediate stages.
     */
    final Function<ImageRequestBuilder<R>, E> runtimeSelector;

    AIModelStage(
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
     * Configures the AI Model to be {@link DallE20}
     *
     * <p>
     * <em>This model is the default model for images generation and
     * the ancestor of the most recent <b>dall-e-3</b> model. It can create
     * original, realistic images based on other art or text description.
     * Keep in mind that images may be more cartoon-ish looking and more
     * similar to each other.</em>
     * </p>
     *
     * @return {@link ChoicesStage} to configure the choices count.
     */
    public ChoicesStage<R, E> poweredByDallE2() {
        return new ChoicesStage<>(
                this.executor,
                this.builder.withModel(new DallE20().name()),
                this.runtimeSelector
        );
    }

    /**
     * Configures the AI Model to be {@link DallE30}
     *
     * <p>
     * <em>This model is the most modern model for images generation and
     * the successor of the default <b>dall-e-2</b> model.
     * It understands significantly more nuance and detail compared to the
     * earlier systems.
     * Creates accurate images by directly translating the provided
     * instructions.
     * </em>
     * </p>
     *
     * <p><b>Note:</b> Currently supports only images generation and is
     * slightly more expensive in tokens</p>
     *
     * @return {@link ChoicesStage} to configure the choices count.
     */
    public StyleStage<R, E> poweredByDallE3() {
        return new StyleStage<>(
                this.executor,
                this.builder.withModel(new DallE30().name())
                            .withN(1),
                this.runtimeSelector
        );
    }

    /**
     * Configures the AI Model to be {@link DallE20}
     *
     * <p>
     * <em>This model is the default model for images generation and
     * the ancestor of the most recent <b>dall-e-3</b> model. It can create
     * original, realistic images based on other art or text description.
     * Keep in mind that images may be more cartoon-ish looking and more
     * similar to each other.</em>
     * </p>
     *
     * @return {@link ChoicesStage} to configure the choices count.
     */
    public ChoicesStage<R, E> defaultModel() {
        return this.poweredByDallE2();
    }

}
