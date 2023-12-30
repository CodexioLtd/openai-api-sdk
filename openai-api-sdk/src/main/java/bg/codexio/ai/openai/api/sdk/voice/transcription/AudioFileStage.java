package bg.codexio.ai.openai.api.sdk.voice.transcription;

import bg.codexio.ai.openai.api.http.voice.TranscriptionHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.TranscriptionRequest;

import java.io.File;

/**
 * Configures which audio file to transcribe
 */
public class AudioFileStage
        extends TranscriptionConfigurationStage {

    AudioFileStage(
            TranscriptionHttpExecutor executor,
            TranscriptionRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * Sets local audio file to transcribe.
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
    public TemperatureStage transcribe(File audioFile) {
        return new TemperatureStage(
                this.executor,
                this.requestBuilder.withFile(audioFile)
        );
    }
}
