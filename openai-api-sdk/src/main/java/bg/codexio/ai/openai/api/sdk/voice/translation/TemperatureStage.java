package bg.codexio.ai.openai.api.sdk.voice.translation;

import bg.codexio.ai.openai.api.http.voice.TranslationHttpExecutor;
import bg.codexio.ai.openai.api.payload.creativity.Creativity;
import bg.codexio.ai.openai.api.payload.voice.request.TranslationRequest;

/**
 * Configures the temperature or in other words,
 * the creativity of the model. This is usually related
 * to <b>temperature</b> parameter.
 */
public class TemperatureStage
        extends TranslationConfigurationStage {

    TemperatureStage(
            TranslationHttpExecutor executor,
            TranslationRequest.Builder requestBuilder
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
     * @return {@link FormatStage} to add prompts
     */
    public FormatStage creativeAs(Creativity creativity) {
        return new FormatStage(
                this.executor,
                this.requestBuilder.withTemperature(creativity.val())
        );
    }

    /**
     * Configures <b>temperature</b> to {@link Creativity#TRULY_DETERMINISTIC}
     *
     * @return {@link FormatStage} to add prompts
     */
    public FormatStage deterministic() {
        return this.creativeAs(Creativity.TRULY_DETERMINISTIC);
    }

    /**
     * Configures <b>temperature</b> to
     * {@link Creativity#I_HAVE_NO_IDEA_WHAT_I_AM_TALKING}
     *
     * @return {@link FormatStage} to add prompts
     */
    public FormatStage randomized() {
        return this.creativeAs(Creativity.I_HAVE_NO_IDEA_WHAT_I_AM_TALKING);
    }
}
