package bg.codexio.ai.openai.api.sdk.voice.transcription;

import bg.codexio.ai.openai.api.http.voice.TranscriptionHttpExecutor;
import bg.codexio.ai.openai.api.payload.creativity.Creativity;
import bg.codexio.ai.openai.api.payload.voice.request.TranscriptionRequest;

/**
 * Configures the temperature or in other words,
 * the creativity of the model. This is usually related
 * to <b>temperature</b> parameter.
 */
public class TemperatureStage
        extends TranscriptionConfigurationStage {

    TemperatureStage(
            TranscriptionHttpExecutor executor,
            TranscriptionRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * Configures <b>temperature</b>
     *
     * @param creativity {@link Creativity}
     * @return {@link LanguageStage} to add prompts
     */
    public LanguageStage creativeAs(Creativity creativity) {
        return new LanguageStage(
                this.executor,
                this.requestBuilder.withTemperature(creativity.val())
        );
    }

    /**
     * Configures <b>temperature</b> to {@link Creativity#TRULY_DETERMINISTIC}
     *
     * @return {@link LanguageStage} to add prompts
     */
    public LanguageStage deterministic() {
        return this.creativeAs(Creativity.TRULY_DETERMINISTIC);
    }

    /**
     * Configures <b>temperature</b> to
     * {@link Creativity#I_HAVE_NO_IDEA_WHAT_I_AM_TALKING}
     *
     * @return {@link LanguageStage} to add prompts
     */
    public LanguageStage randomized() {
        return this.creativeAs(Creativity.I_HAVE_NO_IDEA_WHAT_I_AM_TALKING);
    }
}
