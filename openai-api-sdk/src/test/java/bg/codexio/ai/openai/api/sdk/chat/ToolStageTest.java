package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.payload.chat.functions.GetNearbyPlaces;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static bg.codexio.ai.openai.api.sdk.chat.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ToolStageTest {
    private ToolStage toolStage;

    @BeforeEach
    public void setUp() {
        this.toolStage = new ToolStage(
                null,
                ChatMessageRequest.builder()
                                  .withModel(MODEL_TYPE.name())
                                  .withTemperature(CREATIVITY.val())
                                  .withTopP(CREATIVITY.val())
                                  .withFrequencyPenalty(REPETITION_PENALTY.val())
                                  .withPresencePenalty(REPETITION_PENALTY.val())
                                  .withMaxTokens(MAX_TOKENS)
                                  .withN(CHOICES)
                                  .withStop(STOP)
        );
    }

    @Test
    public void testWithTool_toolShouldChoose_expectToolChoiceAsChoice() {
        ToolStage stage = this.toolStage.withTool(
                GetNearbyPlaces.FUNCTION,
                true
        );

        this.testValuesWithChoice(stage);
    }

    @Test
    public void testWithTool_toolShouldNotChoose_expectToolChoiceAsChoice() {
        ToolStage stage = this.toolStage.withTool(
                GetNearbyPlaces.FUNCTION,
                true
        );

        this.testValuesWithoutChoice(stage);
    }

    @Test
    public void testChooseTool_toolShouldChoose_expectToolChoiceAsChoice() {
        ToolStage stage = this.toolStage.chooseTool(GetNearbyPlaces.FUNCTION);

        this.testValuesWithChoice(stage);
    }

    @Test
    public void testJustAdd_toolShouldNotChoose_expectToolChoiceAsChoice() {
        ToolStage stage = this.toolStage.justAdd(GetNearbyPlaces.FUNCTION);

        this.testValuesWithoutChoice(stage);
    }

    @Test
    public void testAnd_expectCorrectBuilder() {
        var stage = this.toolStage.and();

        this.testPreviousStepValues(stage);
    }

    private void testValuesWithChoice(ToolStage stage) {
        assertAll(
                () -> this.testPreviousStepValues(stage),
                () -> assertEquals(
                        Collections.singletonList(GetNearbyPlaces.FUNCTION),
                        stage.requestBuilder.tools()
                ),
                () -> assertEquals(
                        GetNearbyPlaces.FUNCTION.asChoice(),
                        stage.requestBuilder.toolChoice()
                )
        );
    }

    private void testValuesWithoutChoice(ToolStage stage) {
        assertAll(
                () -> this.testPreviousStepValues(stage),
                () -> assertEquals(
                        Collections.singletonList(GetNearbyPlaces.FUNCTION),
                        stage.requestBuilder.tools()
                ),
                () -> assertNull(stage.requestBuilder.toolChoice())
        );
    }

    private void testPreviousStepValues(ChatConfigurationStage stage) {
        assertAll(
                () -> modelRemainsUnchanged(
                        this.toolStage,
                        stage
                ),
                () -> temperatureRemainsUnchanged(
                        this.toolStage,
                        stage
                ),
                () -> topPRemainsUnchanged(
                        this.toolStage,
                        stage
                ),
                () -> frequencyPenaltyRemainsUnchanged(
                        this.toolStage,
                        stage
                ),
                () -> frequencyPenaltyRemainsUnchanged(
                        this.toolStage,
                        stage
                ),
                () -> maxTokensRemainUnchanged(
                        this.toolStage,
                        stage
                ),
                () -> choicesRemainUnchanged(
                        this.toolStage,
                        stage
                ),
                () -> stopRemainsUnchanged(
                        this.toolStage,
                        stage
                )
        );
    }
}
