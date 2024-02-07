package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.payload.chat.ChatFunction;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;
import bg.codexio.ai.openai.api.payload.chat.request.FunctionTool;
import bg.codexio.ai.openai.api.sdk.chat.exception.TopLogprobsOutOfRangeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static bg.codexio.ai.openai.api.sdk.chat.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class LogprobsStageTest {

    private static final Integer TOP_LOGPROBS_VALUE = 2;

    private LogprobsStage logprobsStage;

    @BeforeEach
    void setUp() {
        this.logprobsStage = new LogprobsStage(
                CHAT_EXECUTOR,
                ChatMessageRequest.builder()
                                  .withModel(MODEL_TYPE.name())
                                  .withTermperature(CREATIVITY.val())
                                  .withTopP(CREATIVITY.val())
                                  .withFrequencyPenalty(REPETITION_PENALTY.val())
                                  .withPresencePenalty(REPETITION_PENALTY.val())
                                  .withMaxTokens(MAX_TOKENS)
                                  .withN(CHOICES)
                                  .withStop(STOP)
                                  .withTools(List.of(new FunctionTool(new ChatFunction(
                                          "test",
                                          null,
                                          null,
                                          null
                                  ))))
        );
    }

    @Test
    void testEnable_expectCorrectBuilder() {
        var result = this.logprobsStage.enable();

        this.previousValuesRemainsUnchanged(result);
        assertEquals(
                Boolean.TRUE,
                result.requestBuilder.logprobs()
        );
    }

    @Test
    void testWithTop_expectCorrectBuilder() {
        var result = this.logprobsStage.withTop(TOP_LOGPROBS_VALUE);

        this.previousValuesRemainsUnchanged(result);
        assertAll(
                () -> isLogprobsEnabled(result),
                () -> assertEquals(
                        TOP_LOGPROBS_VALUE,
                        result.requestBuilder.topLogprobs()

                )
        );
    }

    @Test
    void testWithTop_withValueSmallerThanZero_expectTopLogprobsOutOfRangeException() {
        assertThrows(
                TopLogprobsOutOfRangeException.class,
                () -> this.logprobsStage.withTop(-1)
        );
    }

    @Test
    void testWithTop_withValueBiggerThanFive_expectTopLogprobsOutOfRangeException() {
        assertThrows(
                TopLogprobsOutOfRangeException.class,
                () -> this.logprobsStage.withTop(6)
        );
    }

    @Test
    void testAnd_expectCorrectBuilder() {
        var result = this.logprobsStage.and();

        this.previousValuesRemainsUnchanged(result);
    }

    private void previousValuesRemainsUnchanged(ChatConfigurationStage stage) {
        assertAll(
                () -> modelRemainsUnchanged(
                        this.logprobsStage,
                        stage
                ),
                () -> temperatureRemainsUnchanged(
                        this.logprobsStage,
                        stage
                ),
                () -> topPRemainsUnchanged(
                        this.logprobsStage,
                        stage
                ),
                () -> frequencyPenaltyRemainsUnchanged(
                        this.logprobsStage,
                        stage
                ),
                () -> frequencyPenaltyRemainsUnchanged(
                        this.logprobsStage,
                        stage
                ),
                () -> maxTokensRemainUnchanged(
                        this.logprobsStage,
                        stage
                ),
                () -> choicesRemainUnchanged(
                        this.logprobsStage,
                        stage
                ),
                () -> stopRemainsUnchanged(
                        this.logprobsStage,
                        stage
                ),
                () -> toolsRemainUnchanged(
                        this.logprobsStage,
                        stage
                )
        );
    }

    private void isLogprobsEnabled(ChatConfigurationStage configurationStage) {
        assertEquals(
                Boolean.TRUE,
                configurationStage.requestBuilder.logprobs()
        );
    }
}