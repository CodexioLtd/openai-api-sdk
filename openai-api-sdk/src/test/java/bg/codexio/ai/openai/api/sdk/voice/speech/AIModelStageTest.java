package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.models.tts.TTS1HDModel;
import bg.codexio.ai.openai.api.models.tts.TTS1Model;
import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.voice.speech.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AIModelStageTest {

    private AIModelStage aiModelStage;

    @BeforeEach
    public void setUp() {
        this.aiModelStage = new AIModelStage(
                TEST_EXECUTOR,
                SpeechRequest.builder()
        );
    }

    @Test
    public void testHdPowered_expectTTS1Hd() {
        var nextStage = this.aiModelStage.hdPowered();

        assertAll(
                () -> assertEquals(
                        new TTS1HDModel().name(),
                        nextStage.requestBuilder.model()
                ),
                () -> executorRemainsUnchanged(
                        this.aiModelStage,
                        nextStage
                )
        );
    }

    @Test
    public void testDefaultModel_expectTTS1() {
        var nextStage = this.aiModelStage.defaultModel();

        assertAll(
                () -> assertEquals(
                        new TTS1Model().name(),
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
        var nextStage = this.aiModelStage.poweredBy(TEST_MODEL);

        assertAll(
                () -> assertEquals(
                        TEST_MODEL.name(),
                        nextStage.requestBuilder.model()
                ),
                () -> executorRemainsUnchanged(
                        this.aiModelStage,
                        nextStage
                )
        );
    }

}
