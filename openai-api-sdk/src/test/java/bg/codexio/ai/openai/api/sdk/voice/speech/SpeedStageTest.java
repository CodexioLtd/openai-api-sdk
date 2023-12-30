package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.payload.voice.Speed;
import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static bg.codexio.ai.openai.api.sdk.voice.speech.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpeedStageTest {

    private SpeedStage speedStage;

    private static void otherValuesRemainUnchanged(
            SpeechConfigurationStage currentStage,
            SpeechConfigurationStage nextStage
    ) {
        assertAll(
                () -> modelRemainsUnchanged(nextStage),
                () -> voiceRemainsUnchanged(nextStage),
                () -> formatRemainsUnchanged(nextStage),
                () -> executorRemainsUnchanged(
                        currentStage,
                        nextStage
                )
        );
    }

    @BeforeEach
    public void setUp() {
        this.speedStage = new SpeedStage(
                null,
                SpeechRequest.builder()
                             .withModel(TEST_MODEL.name())
                             .withVoice(TEST_SPEAKER.val())
                             .withFormat(TEST_AUDIO.val())
        );
    }

    @Test
    public void testSameSpeed_expectNormalSpeed() {
        var nextStage = this.speedStage.sameSpeed();

        assertAll(
                () -> assertEquals(
                        Speed.NORMAL.val(),
                        nextStage.requestBuilder.speed()
                ),
                () -> otherValuesRemainUnchanged(
                        this.speedStage,
                        nextStage
                )
        );
    }

    @Test
    public void testSlow_expectHalfSlowSpeed() {
        var nextStage = this.speedStage.slow();

        assertAll(
                () -> assertEquals(
                        Speed.HALF_SLOW.val(),
                        nextStage.requestBuilder.speed()
                ),
                () -> otherValuesRemainUnchanged(
                        this.speedStage,
                        nextStage
                )
        );
    }

    @Test
    public void testFast_expectTwiceFasterSpeed() {
        var nextStage = this.speedStage.fast();

        assertAll(
                () -> assertEquals(
                        Speed.TWICE_FASTER.val(),
                        nextStage.requestBuilder.speed()
                ),
                () -> otherValuesRemainUnchanged(
                        this.speedStage,
                        nextStage
                )
        );
    }

    @ParameterizedTest
    @EnumSource(Speed.class)
    public void testPlayback_withAllEnumValues_expectOutputEnumsValue(Speed speed) {
        var nextStage = this.speedStage.playback(speed);

        assertAll(
                () -> assertEquals(
                        speed.val(),
                        nextStage.requestBuilder.speed()
                ),
                () -> otherValuesRemainUnchanged(
                        this.speedStage,
                        nextStage
                )
        );
    }

}
