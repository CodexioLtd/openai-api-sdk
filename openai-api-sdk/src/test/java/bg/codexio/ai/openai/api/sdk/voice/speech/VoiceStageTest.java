package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.payload.voice.Speaker;
import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.voice.speech.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VoiceStageTest {

    private static final Speaker MANUAL_SELECTION = Speaker.ALLOY;

    private VoiceStage voiceStage;

    private static void otherValuesRemainUnchanged(
            SpeechConfigurationStage currentStage,
            SpeechConfigurationStage nextStage
    ) {
        assertAll(
                () -> modelRemainsUnchanged(nextStage),
                () -> executorRemainsUnchanged(
                        currentStage,
                        nextStage
                )
        );
    }

    @BeforeEach
    public void setUp() {
        this.voiceStage = new VoiceStage(
                null,
                SpeechRequest.builder()
                             .withModel(TEST_MODEL.name())
        );
    }

    @Test
    public void testDefaultSpeaker_expectSpeakerEcho() {
        var nextStage = this.voiceStage.defaultSpeaker();

        assertAll(
                () -> assertEquals(
                        Speaker.ECHO.val(),
                        nextStage.requestBuilder.voice()
                ),
                () -> otherValuesRemainUnchanged(
                        this.voiceStage,
                        nextStage
                )
        );
    }

    @Test
    public void testVoiceOf_expectManualSelection() {
        var nextStage = this.voiceStage.voiceOf(MANUAL_SELECTION);

        assertAll(
                () -> assertEquals(
                        MANUAL_SELECTION.val(),
                        nextStage.requestBuilder.voice()
                ),
                () -> otherValuesRemainUnchanged(
                        this.voiceStage,
                        nextStage
                )
        );
    }
}
