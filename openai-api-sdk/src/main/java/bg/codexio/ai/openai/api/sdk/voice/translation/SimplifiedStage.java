package bg.codexio.ai.openai.api.sdk.voice.translation;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.models.whisper.Whisper10;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.TerminalStage;

import java.io.File;

/**
 * Simplifies configuration with already configured
 * AI Model, temperature and output format;
 */
public class SimplifiedStage
        implements TerminalStage {

    final ApiCredentials credentials;
    final File audioFile;

    SimplifiedStage(
            ApiCredentials credentials,
            File audioFile
    ) {
        this.credentials = credentials;
        this.audioFile = audioFile;
    }

    /**
     * Autoconfigures AI Model {@link Whisper10},
     * temperature {@link TemperatureStage#deterministic()} and
     * format {@link FormatStage#justText()}
     *
     * @return {@link TranslationRuntimeSelectionStage}
     */
    public TranslationRuntimeSelectionStage andRespond() {
        return Translation.authenticate(new HttpExecutorContext(this.credentials))
                          .and()
                          .poweredByWhisper10()
                          .translate(this.audioFile)
                          .deterministic()
                          .justText();
    }

}
