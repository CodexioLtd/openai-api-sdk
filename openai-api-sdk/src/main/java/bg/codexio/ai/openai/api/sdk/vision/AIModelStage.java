package bg.codexio.ai.openai.api.sdk.vision;

import bg.codexio.ai.openai.api.http.vision.VisionHttpExecutor;
import bg.codexio.ai.openai.api.models.ModelType;
import bg.codexio.ai.openai.api.models.v40.GPT4032kModel;
import bg.codexio.ai.openai.api.models.v40.GPT40VisionPreviewModel;
import bg.codexio.ai.openai.api.payload.vision.request.VisionRequest;

/**
 * A stage to choose a model supported by
 * <a href="https://platform.openai.com/docs/models/model-endpoint-compatibility">
 * /v1/chat/completions model endpoint compatibility
 * </a>.
 */
public class AIModelStage
        extends VisionConfigurationStage {


    AIModelStage(
            VisionHttpExecutor executor,
            VisionRequest requestContext
    ) {
        super(
                executor,
                requestContext
        );
    }

    /**
     * @param modelType {@link ModelType} usually implemented by
     *                  <ul>
     *                      <li>GPT 4
     *                            <ul>
     *                                   <li>{@link GPT40VisionPreviewModel}
     *                                   </li>
     *                            </ul>
     *                      </li>
     *                  </ul>
     *                                   Supplying other models such as
     *                                   {@link GPT4032kModel} may result into
     *                                   an error, when the API responds.
     * @return {@link TokenStage} to configure tokens.
     */
    public TokenStage poweredBy(ModelType modelType) {
        return new TokenStage(
                this.executor,
                this.requestContext.withModel(modelType.name())
        );
    }

    /**
     * Configures the AI Model to be {@link GPT40VisionPreviewModel}
     *
     * @return {@link TokenStage} to configure tokens.
     */
    public TokenStage poweredByGpt40Vision() {
        return this.poweredBy(new GPT40VisionPreviewModel());
    }

    /**
     * Configures the AI Model to be {@link GPT40VisionPreviewModel}
     *
     * @return {@link TokenStage} to configure tokens.
     */
    public TokenStage defaultModel() {
        return this.poweredByGpt40Vision();
    }

}
