package bg.codexio.ai.openai.api.sdk.voice.translation;

import bg.codexio.ai.openai.api.models.whisper.Whisper10;
import bg.codexio.ai.openai.api.payload.voice.request.TranslationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.voice.translation.InternalAssertions.TEST_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.voice.translation.InternalAssertions.executorRemainsUnchanged;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AIModelStageTest {

    private AIModelStage aiModelStage;

    @BeforeEach
    public void setUp() {
        this.aiModelStage = new AIModelStage(
                TEST_EXECUTOR,
                TranslationRequest.builder()
        );
    }

    @Test
    public void testPoweredByWhisper10_expectWhisper10() {
        var nextStage = this.aiModelStage.poweredByWhisper10();

        assertAll(
                () -> assertEquals(
                        new Whisper10().name(),
                        nextStage.requestBuilder.model()
                ),
                () -> executorRemainsUnchanged(
                        this.aiModelStage,
                        nextStage
                )
        );
    }

    @Test
    public void testPoweredByManualChoice_expectManualSelection() {
        var nextStage = this.aiModelStage.poweredBy(new Whisper10());

        assertAll(
                () -> assertEquals(
                        new Whisper10().name(),
                        nextStage.requestBuilder.model()
                ),
                () -> executorRemainsUnchanged(
                        this.aiModelStage,
                        nextStage
                )
        );
    }

}
