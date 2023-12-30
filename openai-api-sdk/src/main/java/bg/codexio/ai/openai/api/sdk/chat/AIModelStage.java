package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.http.chat.ChatHttpExecutor;
import bg.codexio.ai.openai.api.models.ModelType;
import bg.codexio.ai.openai.api.models.dalle.DallE30;
import bg.codexio.ai.openai.api.models.v35.GPT35Turbo1106Model;
import bg.codexio.ai.openai.api.models.v35.GPT35Turbo16kModel;
import bg.codexio.ai.openai.api.models.v35.GPT35TurboModel;
import bg.codexio.ai.openai.api.models.v40.GPT401106Model;
import bg.codexio.ai.openai.api.models.v40.GPT4032kModel;
import bg.codexio.ai.openai.api.models.v40.GPT40Model;
import bg.codexio.ai.openai.api.models.v40.GPT40VisionPreviewModel;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;

/**
 * A stage to choose a model supported by
 * <a href="https://platform.openai.com/docs/models/model-endpoint-compatibility">
 * /v1/chat/completions model endpoint compatibility
 * </a>.
 */
public class AIModelStage
        extends ChatConfigurationStage {

    AIModelStage(
            ChatHttpExecutor executor,
            ChatMessageRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * @param modelType {@link ModelType} usually implemented by
     *                  <ul>
     *                      <li>GPT 3.5:
     *                           <ul>
     *                                   <li>{@link GPT35TurboModel}</li>
     *                                   <li>{@link GPT35Turbo1106Model}</li>
     *                                   <li>{@link GPT35Turbo16kModel}</li>
     *                           </ul>
     *                      </li>
     *                      <li>GPT 4
     *                            <ul>
     *                                   <li>{@link GPT40Model}</li>
     *                                   <li>{@link GPT40VisionPreviewModel}
     *                                   </li>
     *                                   <li>{@link GPT4032kModel}</li>
     *                                   <li>{@link GPT401106Model}</li>
     *                            </ul>
     *                      </li>
     *                  </ul>
     *                                   Supplying other models such as
     *                                   {@link DallE30} may result into
     *                                   an error, when the API responds.
     * @return {@link TemperatureStage} to configure the temperature.
     */
    public TemperatureStage poweredBy(ModelType modelType) {
        return new TemperatureStage(
                this.executor,
                this.requestBuilder.withModel(modelType.name())
        );
    }

    /**
     * Configures the AI Model to be {@link GPT35TurboModel}
     *
     * @return {@link TemperatureStage} to configure the temperature.
     */
    public TemperatureStage poweredByGPT35() {
        return this.poweredBy(new GPT35TurboModel());
    }

    /**
     * Configures the AI Model to be {@link GPT40Model}
     *
     * @return {@link TemperatureStage} to configure the temperature.
     */
    public TemperatureStage poweredByGPT40() {
        return this.poweredBy(new GPT40Model());
    }

    /**
     * Configures the AI Model to be {@link GPT401106Model}
     *
     * @return {@link TemperatureStage} to configure the temperature.
     */
    public TemperatureStage turboPowered() {
        return this.poweredBy(new GPT401106Model());
    }

}
