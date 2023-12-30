package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;
import bg.codexio.ai.openai.api.payload.creativity.Creativity;
import bg.codexio.ai.openai.api.payload.creativity.RepetitionPenalty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.chat.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccuracyStageTest {
    private AccuracyStage accuracyStage;

    @BeforeEach
    public void setUp() {
        this.accuracyStage = new AccuracyStage(
                null,
                ChatMessageRequest.builder()
                                  .withModel(MODEL_TYPE.name())
                                  .withMaxTokens(MAX_TOKENS)
                                  .withN(CHOICES)
                                  .withStop(STOP)
                                  .addTool(TOOL)
        );
    }

    @Test
    public void testWithTemperature_manualChoice_expectTemperatureAsChoice() {
        var stage =
                this.accuracyStage.withTemperature(Creativity.I_AM_WRITING_SONGS);

        assertAll(
                () -> this.previousValuesRemainUnchanged(stage),
                () -> assertEquals(
                        Creativity.I_AM_WRITING_SONGS.val(),
                        stage.requestBuilder.temperature()
                )
        );
    }

    @Test
    public void testWithSamplingProbability_manualChoice_expectTopPAsChoice() {
        var stage =
                this.accuracyStage.withSamplingProbability(Creativity.I_AM_WRITING_SONGS);

        assertAll(
                () -> this.previousValuesRemainUnchanged(stage),
                () -> assertEquals(
                        Creativity.I_AM_WRITING_SONGS.val(),
                        stage.requestBuilder.topP()
                )
        );
    }

    @Test
    public void testWithFrequencyPenalty_manualChoice_expectFrequencyPenaltyAsChoice() {
        var stage =
                this.accuracyStage.withFrequencyPenalty(RepetitionPenalty.ALMOST_GOOD);

        assertAll(
                () -> this.previousValuesRemainUnchanged(stage),
                () -> assertEquals(
                        RepetitionPenalty.ALMOST_GOOD.val(),
                        stage.requestBuilder.frequencyPenalty()
                )
        );
    }

    @Test
    public void testWithPresencePenalty_manualChoice_expectPresencePenaltyAsChoice() {
        var stage =
                this.accuracyStage.withPresencePenalty(RepetitionPenalty.ALMOST_GOOD);

        assertAll(
                () -> this.previousValuesRemainUnchanged(stage),
                () -> assertEquals(
                        RepetitionPenalty.ALMOST_GOOD.val(),
                        stage.requestBuilder.presencePenalty()
                )
        );
    }

    @Test
    public void testSampleRepetitionTo_manualChoice_expectSampleRepetitionToAsChoice() {
        var stage = this.accuracyStage.sampleRepetitionsTo(1);

        assertAll(
                () -> this.previousValuesRemainUnchanged(stage),
                () -> assertEquals(
                        1,
                        stage.requestBuilder.seed()
                )
        );
    }

    @Test
    public void testAnd_expectCorrectBuilder() {
        var stage = this.accuracyStage.and();

        this.previousValuesRemainUnchanged(stage);
    }

    private void previousValuesRemainUnchanged(ChatConfigurationStage stage) {
        assertAll(
                () -> modelRemainsUnchanged(
                        this.accuracyStage,
                        stage
                ),
                () -> maxTokensRemainUnchanged(
                        this.accuracyStage,
                        stage
                ),
                () -> choicesRemainUnchanged(
                        this.accuracyStage,
                        stage
                ),
                () -> stopRemainsUnchanged(
                        this.accuracyStage,
                        stage
                ),
                () -> toolsRemainUnchanged(
                        this.accuracyStage,
                        stage
                )
        );
    }
}
