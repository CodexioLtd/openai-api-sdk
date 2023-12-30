package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.http.chat.ChatHttpExecutor;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;
import bg.codexio.ai.openai.api.payload.creativity.Creativity;
import bg.codexio.ai.openai.api.payload.creativity.RepetitionPenalty;
import bg.codexio.ai.openai.api.sdk.TerminalStage;

/**
 * Configures the temperature or in other words,
 * the creativity of the model. This is usually related
 * to <b>temperature</b> or <b>topP</b> parameters.
 */
public class TemperatureStage
        extends ChatConfigurationStage
        implements TerminalStage {

    protected TemperatureStage(
            ChatHttpExecutor executor,
            ChatMessageRequest.Builder requestBuilder
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
     * @return {@link MessageStage} to add prompts
     */
    public MessageStage creativeAs(Creativity creativity) {
        return new MessageStage(
                this.executor,
                this.requestBuilder.withTermperature(creativity.val())
        );
    }

    /**
     * Configures <b>temperature</b> and additionally
     * configures <b>topP</b>, <b>frequencyPenalty</b> and
     * <b>presencePenalty</b> by applying approximation for
     * <b>topP</b> and inverse function for the penalties.
     *
     * @param creativity {@link Creativity}
     * @return {@link MessageStage} to add prompts
     */
    public MessageStage scaleRepetitionToCreativity(Creativity creativity) {
        return new MessageStage(
                this.executor,
                this.requestBuilder.withTermperature(creativity.val())
                                   .withTopP(creativity.val()
                                                     == Creativity.BALANCE_BETWEEN_NOVELTY_AND_PREDICTABILITY.val()
                                             ?
                                             Creativity.BALANCE_BETWEEN_NOVELTY_AND_PREDICTABILITY.val()
                                             : (
                                                     creativity.val()
                                                             > Creativity.BALANCE_BETWEEN_NOVELTY_AND_PREDICTABILITY.val()
                                                     ? Math.min(
                                                             Creativity.I_HAVE_NO_IDEA_WHAT_I_AM_TALKING.val(),
                                                             creativity.val()
                                                                     + 0.1
                                                     )
                                                     : Math.max(
                                                             Creativity.TRULY_DETERMINISTIC.val(),
                                                             creativity.val()
                                                                     - 0.1
                                                     )
                                             ))
                                   .withFrequencyPenalty(Math.max(
                                           RepetitionPenalty.REPEAT_A_LOT.val(),
                                           RepetitionPenalty.HARDLY_REPEATS.val()
                                                   - creativity.val()
                                   ))
                                   .withPresencePenalty(Math.max(
                                           RepetitionPenalty.REPEAT_A_LOT.val(),
                                           RepetitionPenalty.HARDLY_REPEATS.val()
                                                   - creativity.val()
                                   ))
                                   .withN(1)
        );
    }

    /**
     * Configures all parameters to be mostly deterministic.
     * This sets zero randomness in the temperature and top_p
     * sampling.
     *
     * @return {@link MessageStage} to add prompts
     */
    public MessageStage deterministic() {
        return this.scaleRepetitionToCreativity(Creativity.TRULY_DETERMINISTIC);
    }

    /**
     * Configures all parameters to be mostly deterministic,
     * but also a little bit creative.
     *
     * @return {@link MessageStage} to add prompts
     */
    public MessageStage predictable() {
        return this.scaleRepetitionToCreativity(Creativity.INTRODUCE_SOME_RANDOMNESS);
    }

    /**
     * Configures all parameters to be balanced between creativity and
     * determinism.
     *
     * @return {@link MessageStage} to add prompts
     */
    public MessageStage inventive() {
        return this.scaleRepetitionToCreativity(Creativity.FIRST_JUMP_TO_CREATIVITY);
    }

    /**
     * <font color=orange>You may receive a lot of irrelevancy.</font><br/>
     * Highly creative parameters with less deterministic properties.
     *
     * @return {@link MessageStage} to add prompts
     */
    public MessageStage imaginative() {
        return this.scaleRepetitionToCreativity(Creativity.I_AM_WRITING_SONGS);
    }

    /**
     * <font color=red>You may receive total non-sense in the answers
     * .</font><br/>
     * Configures all parameters to the highest creativity with
     * little to none determinism and a lot of repetitions.
     *
     * @return {@link MessageStage} to add prompts
     */
    public MessageStage randomized() {
        return this.scaleRepetitionToCreativity(Creativity.I_HAVE_NO_IDEA_WHAT_I_AM_TALKING);
    }

    /**
     * Goes to manually configuring some non-trivial options
     * such as tools, tokens and accuracy. After successful configuration,
     * use {@link ManualConfigurationStage#done()} to go to the next
     * {@link MessageStage}.
     *
     * @return {@link ManualConfigurationStage}
     */
    public ManualConfigurationStage deepConfigure() {
        return new ManualConfigurationStage(
                this.executor,
                this.requestBuilder
        );
    }

    /**
     * Bypass all configuration steps and go directly to
     * {@link ChatRuntimeSelectionStage}.
     *
     * @return {@link ChatRuntimeSelectionStage} to select runtime and then
     * prompt the API.
     */
    public ChatRuntimeSelectionStage andRespond() {
        return new ChatRuntimeSelectionStage(
                this.executor,
                this.requestBuilder
        );
    }
}
