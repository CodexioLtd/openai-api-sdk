package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.http.voice.SpeechHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.AudioFormat;
import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;
import bg.codexio.ai.openai.api.sdk.IntermediateStage;

/**
 * Configures the output format
 */
public class OutputStage
        extends SpeechConfigurationStage
        implements IntermediateStage {

    OutputStage(
            SpeechHttpExecutor executor,
            SpeechRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * Sets {@link AudioFormat}
     *
     * @return {@link SpeedStage} to configure the produced speed
     */
    public SpeedStage audio(AudioFormat format) {
        return new SpeedStage(
                this.executor,
                this.requestBuilder.withFormat(format.val())
        );
    }

    /**
     * Sets {@link AudioFormat} as {@link AudioFormat#MP3}
     *
     * @return {@link SpeedStage} to configure the produced speed
     */
    public SpeedStage mp3() {
        return this.audio(AudioFormat.MP3);
    }

    /**
     * Sets {@link AudioFormat} optimized for streaming, as
     * {@link AudioFormat#OPUS}
     *
     * @return {@link SpeedStage} to configure the produced speed
     */
    public SpeedStage forStreaming() {
        return this.audio(AudioFormat.OPUS);
    }

    /**
     * Sets {@link AudioFormat} optimized for lower rates, as
     * {@link AudioFormat#AAC}
     *
     * @return {@link SpeedStage} to configure the produced speed
     */
    public SpeedStage forLowerRates() {
        return this.audio(AudioFormat.AAC);
    }

    /**
     * Sets {@link AudioFormat} with no compression, as {@link AudioFormat#FLAC}
     *
     * @return {@link SpeedStage} to configure the produced speed
     */
    public SpeedStage noCompression() {
        return this.audio(AudioFormat.FLAC);
    }
}
