package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.models.ModelTypeAbstract;
import bg.codexio.ai.openai.api.models.v40.GPT4032kModel;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;
import bg.codexio.ai.openai.api.payload.creativity.Creativity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static bg.codexio.ai.openai.api.sdk.chat.InternalAssertions.modelRemainsUnchanged;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemperatureStageTest {

    private static final ModelTypeAbstract MODEL_TYPE = new GPT4032kModel();
    private TemperatureStage temperatureStage;

    @BeforeEach
    public void setUp() {
        this.temperatureStage = new TemperatureStage(
                null,
                ChatMessageRequest.builder()
                                  .withModel(MODEL_TYPE.name())
        );
    }

    @Test
    public void testCreativeAs_manualChoiceTemperature_expectCreativeAsManualChoice() {
        this.testTemperature(
                this.temperatureStage.creativeAs(Creativity.DONT_TRUST_ME),
                Creativity.DONT_TRUST_ME
        );
    }

    @Test
    public void testScaleRepetitionToCreativity_manualChoiceEqualToFive_expectResultPerFormula() {
        MessageStage nextStage =
                this.temperatureStage.scaleRepetitionToCreativity(Creativity.BALANCE_BETWEEN_NOVELTY_AND_PREDICTABILITY);

        assertAll(
                () -> this.testPreviousStepValues(nextStage),
                () -> assertEquals(
                        Creativity.BALANCE_BETWEEN_NOVELTY_AND_PREDICTABILITY.val(),
                        nextStage.requestBuilder.temperature()
                ),
                () -> assertEquals(
                        Creativity.BALANCE_BETWEEN_NOVELTY_AND_PREDICTABILITY.val(),
                        nextStage.requestBuilder.topP()
                ),
                () -> assertEquals(
                        0.4,
                        nextStage.requestBuilder.frequencyPenalty()
                ),
                () -> assertEquals(
                        0.4,
                        nextStage.requestBuilder.presencePenalty()
                )
        );
    }

    @Test
    public void testScaleRepetitionToCreativity_manualChoiceMoreThanFive_expectResultPerFormula() {
        MessageStage nextStage =
                this.temperatureStage.scaleRepetitionToCreativity(Creativity.I_AM_WRITING_SONGS);

        assertAll(
                () -> this.testPreviousStepValues(nextStage),
                () -> assertEquals(
                        Creativity.I_AM_WRITING_SONGS.val(),
                        nextStage.requestBuilder.temperature()
                ),
                () -> assertEquals(
                        0.9,
                        nextStage.requestBuilder.topP()
                ),
                () -> assertEquals(
                        this.getRoundedBigDecimalValue(0.1),
                        this.getRoundedBigDecimalValue(nextStage.requestBuilder.frequencyPenalty())
                ),
                () -> assertEquals(
                        this.getRoundedBigDecimalValue(0.1),
                        this.getRoundedBigDecimalValue(nextStage.requestBuilder.presencePenalty())
                )
        );
    }

    @Test
    public void testScaleRepetitionToCreativity_manualChoiceLessThanFive_expectResultPerFormula() {
        MessageStage nextStage =
                this.temperatureStage.scaleRepetitionToCreativity(Creativity.FIRST_JUMP_TO_CREATIVITY);

        assertAll(
                () -> this.testPreviousStepValues(nextStage),
                () -> assertEquals(
                        Creativity.FIRST_JUMP_TO_CREATIVITY.val(),
                        nextStage.requestBuilder.temperature()
                ),
                () -> assertEquals(
                        this.getRoundedBigDecimalValue(0.2),
                        this.getRoundedBigDecimalValue(nextStage.requestBuilder.topP())
                ),
                () -> assertEquals(
                        this.getRoundedBigDecimalValue(0.6),
                        this.getRoundedBigDecimalValue(nextStage.requestBuilder.frequencyPenalty())
                ),
                () -> assertEquals(
                        this.getRoundedBigDecimalValue(0.6),
                        this.getRoundedBigDecimalValue(nextStage.requestBuilder.presencePenalty())
                )
        );

    }

    @Test
    public void testCreativeAsScientist_expectTemperature00() {
        this.testTemperature(
                this.temperatureStage.deterministic(),
                Creativity.TRULY_DETERMINISTIC
        );
    }

    @Test
    public void testCreativeAsDeveloper_expectTemperature02() {
        this.testTemperature(
                this.temperatureStage.predictable(),
                Creativity.INTRODUCE_SOME_RANDOMNESS
        );
    }

    @Test
    public void testCreativeButBalanced_expectTemperature03() {
        this.testTemperature(
                this.temperatureStage.inventive(),
                Creativity.FIRST_JUMP_TO_CREATIVITY
        );
    }

    @Test
    public void testCreativeAsSongwriter_expectTemperature08() {
        this.testTemperature(
                this.temperatureStage.imaginative(),
                Creativity.I_AM_WRITING_SONGS
        );
    }

    @Test
    public void testCreativeAsSongwriter_expectTemperature10() {
        this.testTemperature(
                this.temperatureStage.randomized(),
                Creativity.I_HAVE_NO_IDEA_WHAT_I_AM_TALKING
        );
    }

    @Test
    public void testDeepConfigure_expectCorrectBuilder() {
        var nextStage = this.temperatureStage.deepConfigure();

        this.testPreviousStepValues(nextStage);
    }

    @Test
    public void testAndRespond_expectCorrectBuilder() {
        var nextStage = this.temperatureStage.andRespond();

        this.testPreviousStepValues(nextStage);
    }

    private void testTemperature(
            MessageStage stage,
            Creativity creativity
    ) {
        this.testPreviousStepValues(stage);
        assertEquals(
                stage.requestBuilder.temperature(),
                creativity.val()
        );
    }

    private void testPreviousStepValues(ChatConfigurationStage stage) {
        modelRemainsUnchanged(
                this.temperatureStage,
                stage
        );
    }

    private BigDecimal getRoundedBigDecimalValue(Double number) {
        return BigDecimal.valueOf(number)
                         .setScale(
                                 1,
                                 RoundingMode.HALF_UP
                         );
    }
}
