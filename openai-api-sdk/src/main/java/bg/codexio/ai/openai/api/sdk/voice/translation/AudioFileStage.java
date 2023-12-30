package bg.codexio.ai.openai.api.sdk.voice.translation;

import bg.codexio.ai.openai.api.http.voice.TranslationHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.TranslationRequest;

import java.io.File;

/**
 * Configures which audio file to translate
 */
public class AudioFileStage
        extends TranslationConfigurationStage {

    AudioFileStage(
            TranslationHttpExecutor executor,
            TranslationRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * Sets local audio file to translate.
     * Must be one these formats:
     * <ul>
     *     <li>flac</li>
     *     <li>mp3</li>
     *     <li>mp4</li>
     *     <li>mpeg</li>
     *     <li>mpga</li>
     *     <li>m4a</li>
     *     <li>ogg</li>
     *     <li>wav</li>
     * </ul>
     *
     * @param audioFile local file
     * @return {@link TemperatureStage} to configure the temperature level
     */
    public TemperatureStage translate(File audioFile) {
        return new TemperatureStage(
                this.executor,
                this.requestBuilder.withFile(audioFile)
        );
    }
}
