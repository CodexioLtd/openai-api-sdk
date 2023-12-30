package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.http.voice.SpeechHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.Speed;
import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;
import bg.codexio.ai.openai.api.sdk.IntermediateStage;

/**
 * Configures the speed of the output audio file
 */
public class SpeedStage
        extends SpeechConfigurationStage
        implements IntermediateStage {

    SpeedStage(
            SpeechHttpExecutor executor,
            SpeechRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * Configures a speed between 0.25 and 4
     *
     * @return {@link SpeechRuntimeSelectionStage} to select blocking or
     * async runtime
     */
    public SpeechRuntimeSelectionStage playback(double speed) {
        return new SpeechRuntimeSelectionStage(
                this.executor,
                this.requestBuilder.withSpeed(speed)
        );
    }

    /**
     * Configures a {@link Speed}
     *
     * @return {@link SpeechRuntimeSelectionStage} to select blocking or
     * async runtime
     */
    public SpeechRuntimeSelectionStage playback(Speed speed) {
        return this.playback(speed.val());
    }

    /**
     * Configures the {@link Speed} as {@link Speed#NORMAL}
     *
     * @return {@link SpeechRuntimeSelectionStage} to select blocking or
     * async runtime
     */
    public SpeechRuntimeSelectionStage sameSpeed() {
        return this.playback(Speed.NORMAL);
    }

    /**
     * Configures the {@link Speed} as {@link Speed#HALF_SLOW}
     *
     * @return {@link SpeechRuntimeSelectionStage} to select blocking or
     * async runtime
     */
    public SpeechRuntimeSelectionStage slow() {
        return this.playback(Speed.HALF_SLOW);
    }

    /**
     * Configures the {@link Speed} as {@link Speed#TWICE_FASTER}
     *
     * @return {@link SpeechRuntimeSelectionStage} to select blocking or
     * async runtime
     */
    public SpeechRuntimeSelectionStage fast() {
        return this.playback(Speed.TWICE_FASTER);
    }
}
