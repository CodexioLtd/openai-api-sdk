package bg.codexio.ai.openai.api.sdk.voice.translation;

import bg.codexio.ai.openai.api.http.voice.TranslationHttpExecutor;
import bg.codexio.ai.openai.api.models.ModelType;
import bg.codexio.ai.openai.api.models.dalle.DallE30;
import bg.codexio.ai.openai.api.models.whisper.Whisper10;
import bg.codexio.ai.openai.api.payload.voice.request.TranslationRequest;

/**
 * A stage to choose a model supported by
 * <a href="https://platform.openai.com/docs/models/model-endpoint-compatibility">
 * /v1/audio/translations model endpoint compatibility
 * </a>.
 */
public class AIModelStage
        extends TranslationConfigurationStage {

    AIModelStage(
            TranslationHttpExecutor executor,
            TranslationRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * @param modelType {@link ModelType} usually implemented by
     *                  <ul>
     *                      <li>Whisper:
     *                           <ul>
     *                                   <li>{@link Whisper10}</li>
     *                           </ul>
     *                      </li>
     *                  </ul>
     *                                   Supplying other models such as
     *                                   {@link DallE30} may result into
     *                                   an error, when the API responds.
     * @return {@link AudioFileStage} to configure the temperature.
     */
    public AudioFileStage poweredBy(ModelType modelType) {
        return new AudioFileStage(
                this.executor,
                this.requestBuilder.withModel(modelType.name())
        );
    }

    /**
     * Configures the AI Model to be {@link Whisper10}
     *
     * @return {@link AudioFileStage} to configure the temperature.
     */
    public AudioFileStage poweredByWhisper10() {
        return this.poweredBy(new Whisper10());
    }

}
