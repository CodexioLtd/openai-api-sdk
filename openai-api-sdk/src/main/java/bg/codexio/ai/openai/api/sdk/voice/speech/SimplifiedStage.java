package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.models.tts.TTS1HDModel;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.TerminalStage;

/**
 * Preconfigured stage with AI Model,
 * speaker, format and speed
 */
public class SimplifiedStage
        implements TerminalStage {

    private final ApiCredentials credentials;

    public SimplifiedStage(ApiCredentials credentials) {
        this.credentials = credentials;
    }

    /**
     * Preconfigures {@link TTS1HDModel} with HD quality
     * the default speaker with mp3 output format and x1 speed.
     *
     * @return {@link SpeechRuntimeSelectionStage} to select the runtime
     */
    @Override
    public SpeechRuntimeSelectionStage andRespond() {
        return Speech.authenticate(new HttpExecutorContext(this.credentials))
                     .and()
                     .hdPowered()
                     .defaultSpeaker()
                     .mp3()
                     .sameSpeed();
    }
}
