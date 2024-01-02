package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.chat.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenStageTest {
    private TokenStage tokenStage;

    @BeforeEach
    public void setUp() {
        this.tokenStage = new TokenStage(
                null,
                ChatMessageRequest.builder()
                                  .withModel(MODEL_TYPE.name())
                                  .withTemperature(CREATIVITY.val())
                                  .withTopP(CREATIVITY.val())
                                  .withFrequencyPenalty(REPETITION_PENALTY.val())
                                  .withPresencePenalty(REPETITION_PENALTY.val())
                                  .addTool(TOOL)
        );
    }

    @Test
    public void testMax_manualChoice_expectMaxAsChoice() {
        var stage = this.tokenStage.max(100);

        assertAll(
                () -> this.previousStepValuesRemainUnchanged(stage),
                () -> assertEquals(
                        100,
                        stage.requestBuilder.maxTokens()
                )
        );
    }

    @Test
    public void testChoices_manualChoice_expectChoicesAsChoice() {
        var stage = this.tokenStage.choices(2);

        assertAll(
                () -> this.previousStepValuesRemainUnchanged(stage),
                () -> assertEquals(
                        2,
                        stage.requestBuilder.n()
                )
        );
    }

    @Test
    public void testAnd_manualChoice_expectChoicesAsChoice() {
        var stage = this.tokenStage.choices(2);

        assertAll(
                () -> this.previousStepValuesRemainUnchanged(stage),
                () -> assertEquals(
                        2,
                        stage.requestBuilder.n()
                )
        );
    }

    @Test
    public void testStop_manualChoice_expectStopAsChoice() {
        var stage = this.tokenStage.stopAt("Stop!");

        assertAll(
                () -> this.previousStepValuesRemainUnchanged(stage),
                () -> assertEquals(
                        "Stop!",
                        stage.requestBuilder.stop()[0]
                )
        );
    }

    @Test
    public void testAnd_expectCorrectBuilder() {
        var stage = this.tokenStage.and();

        this.previousStepValuesRemainUnchanged(stage);
    }


    private void previousStepValuesRemainUnchanged(ChatConfigurationStage stage) {
        assertAll(
                () -> modelRemainsUnchanged(
                        this.tokenStage,
                        stage
                ),
                () -> temperatureRemainsUnchanged(
                        this.tokenStage,
                        stage
                ),
                () -> topPRemainsUnchanged(
                        this.tokenStage,
                        stage
                ),
                () -> frequencyPenaltyRemainsUnchanged(
                        this.tokenStage,
                        stage
                ),
                () -> presencePenaltyRemainsUnchanged(
                        this.tokenStage,
                        stage
                ),
                () -> toolsRemainUnchanged(
                        this.tokenStage,
                        stage
                )
        );
    }

}
