package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.payload.voice.AudioFormat;
import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static bg.codexio.ai.openai.api.sdk.voice.speech.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutputStageTest {

    private OutputStage outputStage;

    private static void otherValuesRemainUnchanged(
            SpeechConfigurationStage currentStage,
            SpeechConfigurationStage nextStage
    ) {
        assertAll(
                () -> modelRemainsUnchanged(nextStage),
                () -> voiceRemainsUnchanged(nextStage),
                () -> executorRemainsUnchanged(
                        currentStage,
                        nextStage
                )
        );
    }

    @BeforeEach
    public void setUp() {
        this.outputStage = new OutputStage(
                null,
                SpeechRequest.builder()
                             .withModel(TEST_MODEL.name())
                             .withVoice(TEST_SPEAKER.val())
        );
    }

    @Test
    public void testMp3_expectOutputMp3() {
        var nextStage = this.outputStage.mp3();

        assertAll(
                () -> assertEquals(
                        AudioFormat.MP3.val(),
                        nextStage.requestBuilder.responseFormat()
                ),
                () -> otherValuesRemainUnchanged(
                        this.outputStage,
                        nextStage
                )
        );
    }

    @Test
    public void testForStreaming_expectOutputOpus() {
        var nextStage = this.outputStage.forStreaming();

        assertAll(
                () -> assertEquals(
                        AudioFormat.OPUS.val(),
                        nextStage.requestBuilder.responseFormat()
                ),
                () -> otherValuesRemainUnchanged(
                        this.outputStage,
                        nextStage
                )
        );
    }

    @Test
    public void testForLowerRates_expectOutputAac() {
        var nextStage = this.outputStage.forLowerRates();

        assertAll(
                () -> assertEquals(
                        AudioFormat.AAC.val(),
                        nextStage.requestBuilder.responseFormat()
                ),
                () -> otherValuesRemainUnchanged(
                        this.outputStage,
                        nextStage
                )
        );
    }

    @Test
    public void testNoCompression_expectOutputFlac() {
        var nextStage = this.outputStage.noCompression();

        assertAll(
                () -> assertEquals(
                        AudioFormat.FLAC.val(),
                        nextStage.requestBuilder.responseFormat()
                ),
                () -> otherValuesRemainUnchanged(
                        this.outputStage,
                        nextStage
                )
        );
    }

    @ParameterizedTest
    @EnumSource(AudioFormat.class)
    public void testAudio_withAllEnumValues_expectOutputEnumsValue(AudioFormat format) {
        var nextStage = this.outputStage.audio(format);

        assertAll(
                () -> assertEquals(
                        format.val(),
                        nextStage.requestBuilder.responseFormat()
                ),
                () -> otherValuesRemainUnchanged(
                        this.outputStage,
                        nextStage
                )
        );
    }
}
