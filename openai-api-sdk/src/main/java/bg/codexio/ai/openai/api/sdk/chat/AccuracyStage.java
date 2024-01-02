package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.http.chat.ChatHttpExecutor;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;
import bg.codexio.ai.openai.api.payload.creativity.Creativity;
import bg.codexio.ai.openai.api.payload.creativity.RepetitionPenalty;

/**
 * Configures temperature, topP, penalties and seed.
 */
public class AccuracyStage
        extends ChatConfigurationStage {

    AccuracyStage(
            ChatHttpExecutor executor,
            ChatMessageRequest.Builder requestBuilder
    ) {
        super(
                executor,
                requestBuilder
        );
    }

    /**
     * Configures temperature
     *
     * @param temperature {@link Creativity}
     * @return self
     */
    public AccuracyStage withTemperature(Creativity temperature) {
        return new AccuracyStage(
                this.executor,
                this.requestBuilder.withTemperature(temperature.val())
        );
    }

    /**
     * Configures topP
     *
     * @param topP {@link Creativity}
     * @return self
     */
    public AccuracyStage withSamplingProbability(Creativity topP) {
        return new AccuracyStage(
                this.executor,
                this.requestBuilder.withTopP(topP.val())
        );
    }

    /**
     * Configures frequencyPenalty
     *
     * @param penalty {@link RepetitionPenalty}
     * @return self
     */
    public AccuracyStage withFrequencyPenalty(RepetitionPenalty penalty) {
        return new AccuracyStage(
                this.executor,
                this.requestBuilder.withFrequencyPenalty(penalty.val())
        );
    }

    /**
     * Configures presencePenalty
     *
     * @param penalty {@link RepetitionPenalty}
     * @return self
     */
    public AccuracyStage withPresencePenalty(RepetitionPenalty penalty) {
        return new AccuracyStage(
                this.executor,
                this.requestBuilder.withPresencePenalty(penalty.val())
        );
    }

    /**
     * Configures seed
     *
     * @return self
     */
    public AccuracyStage sampleRepetitionsTo(int seed) {
        return new AccuracyStage(
                this.executor,
                this.requestBuilder.withSeed(seed)
        );
    }

    /**
     * Go back
     *
     * @return {@link ManualConfigurationStage}
     */
    public ManualConfigurationStage and() {
        return new ManualConfigurationStage(
                this.executor,
                this.requestBuilder
        );
    }
}
