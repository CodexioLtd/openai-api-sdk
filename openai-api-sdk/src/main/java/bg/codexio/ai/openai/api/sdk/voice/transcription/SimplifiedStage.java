package bg.codexio.ai.openai.api.sdk.voice.transcription;

import bg.codexio.ai.openai.api.http.HttpExecutorContext;
import bg.codexio.ai.openai.api.models.whisper.Whisper10;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.sdk.TerminalStage;

import java.io.File;

/**
 * Simplifies configuration with already configured
 * AI Model, temperature, language and output format;
 */
public class SimplifiedStage
        implements TerminalStage {

    final ApiCredentials credentials;
    final File audioFile;

    public SimplifiedStage(
            ApiCredentials credentials,
            File audioFile
    ) {
        this.credentials = credentials;
        this.audioFile = audioFile;
    }


    /**
     * Autoconfigures AI Model {@link Whisper10},
     * temperature {@link TemperatureStage#deterministic()},
     * language {@link LanguageStage#talkingInEnglish()} and
     * format {@link FormatStage#justText()}
     *
     * @return {@link TranscriptionRuntimeSelectionStage}
     */
    @Override
    public TranscriptionRuntimeSelectionStage andRespond() {
        return Transcription.authenticate(new HttpExecutorContext(this.credentials))
                            .and()
                            .poweredByWhisper10()
                            .transcribe(this.audioFile)
                            .deterministic()
                            .talkingInEnglish()
                            .justText();
    }
}
