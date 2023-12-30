package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.http.voice.SpeechHttpExecutor;
import bg.codexio.ai.openai.api.models.ModelType;
import bg.codexio.ai.openai.api.models.dalle.DallE30;
import bg.codexio.ai.openai.api.models.tts.TTS1HDModel;
import bg.codexio.ai.openai.api.models.tts.TTS1Model;
import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;
import bg.codexio.ai.openai.api.sdk.IntermediateStage;

/**
 * A stage to choose a model supported by
 * <a href="https://platform.openai.com/docs/models/model-endpoint-compatibility">
 * /v1/audio/speech model endpoint compatibility
 * </a>.
 */
public class AIModelStage
        extends SpeechConfigurationStage
        implements IntermediateStage {

    AIModelStage(
            SpeechHttpExecutor executor,
            SpeechRequest.Builder requestContext
    ) {
        super(
                executor,
                requestContext
        );
    }

    /**
     * @param modelType {@link ModelType} usually implemented by
     *                  <ul>
     *                      <li>TTS:
     *                           <ul>
     *                                   <li>{@link TTS1Model}</li>
     *                                   <li>{@link TTS1HDModel}</li>
     *                           </ul>
     *                      </li>
     *                  </ul>
     *                                   Supplying other models such as
     *                                   {@link DallE30} may result into
     *                                   an error, when the API responds.
     * @return {@link VoiceStage} to configure the temperature.
     */
    public VoiceStage poweredBy(ModelType modelType) {
        return new VoiceStage(
                this.executor,
                this.requestBuilder.withModel(modelType.name())
        );
    }

    /**
     * Configures the AI Model to be {@link TTS1HDModel}
     *
     * @return {@link VoiceStage} to configure the temperature.
     */
    public VoiceStage hdPowered() {
        return this.poweredBy(new TTS1HDModel());
    }

    /**
     * Configures the AI Model to be {@link TTS1Model}
     *
     * @return {@link VoiceStage} to configure the temperature.
     */
    public VoiceStage defaultModel() {
        return this.poweredBy(new TTS1Model());
    }

}
