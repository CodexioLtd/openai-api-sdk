package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.http.voice.SpeechHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.Speaker;
import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;
import bg.codexio.ai.openai.api.sdk.IntermediateStage;

/**
 * Configures which person's voice to use
 */
public class VoiceStage
        extends SpeechConfigurationStage
        implements IntermediateStage {

    VoiceStage(
            SpeechHttpExecutor executor,
            SpeechRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * Sets a {@link Speaker}
     *
     * @return {@link OutputStage} to configure the output format
     */
    public OutputStage voiceOf(Speaker speaker) {
        return new OutputStage(
                this.executor,
                this.requestBuilder.withVoice(speaker.val())
        );
    }

    /**
     * Sets a {@link Speaker} to {@link Speaker#ECHO}
     *
     * @return {@link OutputStage} to configure the output format
     */
    public OutputStage defaultSpeaker() {
        return this.voiceOf(Speaker.ECHO);
    }
}
