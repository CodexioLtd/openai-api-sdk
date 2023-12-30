package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.chat.InternalAssertions.modelRemainsUnchanged;

public class ManualConfigurationStageTest {

    private ManualConfigurationStage manualConfigurationStage;

    @BeforeEach
    public void setUp() {
        this.manualConfigurationStage = new ManualConfigurationStage(
                null,
                ChatMessageRequest.builder()
                                  .withModel(InternalAssertions.MODEL_TYPE.name())
        );
    }

    @Test
    public void testAccuracy_expectCorrectBuilder() {
        AccuracyStage stage = this.manualConfigurationStage.accuracy();

        this.previousValuesRemainUnchanged(stage);
    }

    @Test
    public void testToken_expectCorrectBuilder() {
        TokenStage stage = this.manualConfigurationStage.tokens();

        this.previousValuesRemainUnchanged(stage);
    }


    @Test
    public void testTools_expectCorrectBuilder() {
        ToolStage stage = this.manualConfigurationStage.tools();

        this.previousValuesRemainUnchanged(stage);
    }

    @Test
    public void testDone_expectCorrectBuilder() {
        var stage = this.manualConfigurationStage.done();

        this.previousValuesRemainUnchanged(stage);
    }

    private void previousValuesRemainUnchanged(ChatConfigurationStage stage) {
        modelRemainsUnchanged(
                this.manualConfigurationStage,
                stage
        );
    }
}
