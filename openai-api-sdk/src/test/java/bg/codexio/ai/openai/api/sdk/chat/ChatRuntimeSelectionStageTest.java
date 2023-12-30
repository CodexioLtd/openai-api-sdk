package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.chat.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ChatRuntimeSelectionStageTest {
    private ChatRuntimeSelectionStage runtimeSelectionStage;

    @BeforeEach
    public void setUp() {
        this.runtimeSelectionStage = new ChatRuntimeSelectionStage(
                null,
                ChatMessageRequest.builder()
                                  .withModel(MODEL_TYPE.name())
                                  .withTermperature(CREATIVITY.val())
                                  .withTopP(CREATIVITY.val())
                                  .withFrequencyPenalty(REPETITION_PENALTY.val())
                                  .withPresencePenalty(REPETITION_PENALTY.val())
                                  .addTool(TOOL)
        ) {};
    }

    @Test
    public void testImmediate_expectCorrectBuilder() {
        var nextStage = this.runtimeSelectionStage.immediate();

        this.previousStepValuesRemainUnchanged(nextStage);
    }

    @Test
    public void testAsync_expectCorrectBuilder() {
        var nextStage = this.runtimeSelectionStage.async();

        this.previousStepValuesRemainUnchanged(nextStage);
    }

    @Test
    public void testReactive_expectCorrectBuilder() {
        var nextStage = this.runtimeSelectionStage.reactive();

        this.previousStepValuesRemainUnchanged(nextStage);
    }

    private void previousStepValuesRemainUnchanged(ChatConfigurationStage stage) {
        assertAll(
                () -> modelRemainsUnchanged(
                        this.runtimeSelectionStage,
                        stage
                ),
                () -> temperatureRemainsUnchanged(
                        this.runtimeSelectionStage,
                        stage
                ),
                () -> topPRemainsUnchanged(
                        this.runtimeSelectionStage,
                        stage
                ),
                () -> frequencyPenaltyRemainsUnchanged(
                        this.runtimeSelectionStage,
                        stage
                ),
                () -> presencePenaltyRemainsUnchanged(
                        this.runtimeSelectionStage,
                        stage
                ),
                () -> toolsRemainUnchanged(
                        this.runtimeSelectionStage,
                        stage
                )
        );
    }
}
