package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.models.tts.TTS1HDModel;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.payload.voice.AudioFormat;
import bg.codexio.ai.openai.api.payload.voice.Speaker;
import bg.codexio.ai.openai.api.payload.voice.Speed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimplifiedStageTest {

    private SimplifiedStage simplifiedStage;

    @BeforeEach
    public void setUp() {
        this.simplifiedStage = new SimplifiedStage(new ApiCredentials(""));
    }

    @Test
    public void testEnsureRuntimeSelectionStage_expectFilledRuntimeSelectionStage() {
        var nextStage = this.simplifiedStage.andRespond();

        assertAll(
                () -> assertEquals(
                        new TTS1HDModel().name(),
                        nextStage.requestBuilder.model()
                ),
                () -> assertEquals(
                        Speaker.ECHO.val(),
                        nextStage.requestBuilder.voice()
                ),
                () -> assertEquals(
                        AudioFormat.MP3.val(),
                        nextStage.requestBuilder.responseFormat()
                ),
                () -> assertEquals(
                        Speed.NORMAL.val(),
                        nextStage.requestBuilder.speed()
                )
        );
    }

}
