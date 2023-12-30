package bg.codexio.ai.openai.api.sdk.voice.translation;

import bg.codexio.ai.openai.api.payload.creativity.Creativity;
import bg.codexio.ai.openai.api.payload.voice.request.TranslationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.voice.translation.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemperatureStageTest {

    private TemperatureStage temperatureStage;

    @BeforeEach
    public void setUp() {
        this.temperatureStage = new TemperatureStage(
                TEST_EXECUTOR,
                TranslationRequest.builder()
                                  .withModel(TEST_MODEL.name())
                                  .withFile(TEST_FILE)
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
    public void testDeterministic_expectTemperature00() {
        this.testTemperature(
                this.temperatureStage.deterministic(),
                Creativity.TRULY_DETERMINISTIC
        );
    }

    @Test
    public void testCreativeAsDeveloper_expectTemperature10() {
        this.testTemperature(
                this.temperatureStage.randomized(),
                Creativity.I_HAVE_NO_IDEA_WHAT_I_AM_TALKING
        );
    }

    private void testTemperature(
            FormatStage stage,
            Creativity creativity
    ) {
        assertAll(
                () -> assertEquals(
                        stage.requestBuilder.temperature(),
                        creativity.val()
                ),
                () -> this.otherValuesRemainUnchanged(stage)
        );
    }

    private void otherValuesRemainUnchanged(TranslationConfigurationStage stage) {
        assertAll(
                () -> modelRemainsUnchanged(stage),
                () -> fileRemainsUnchanged(stage),
                () -> executorRemainsUnchanged(
                        this.temperatureStage,
                        stage
                )
        );
    }
}
