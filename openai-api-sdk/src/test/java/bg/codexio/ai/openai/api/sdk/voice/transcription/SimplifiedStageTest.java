package bg.codexio.ai.openai.api.sdk.voice.transcription;

import bg.codexio.ai.openai.api.models.whisper.Whisper10;
import bg.codexio.ai.openai.api.payload.creativity.Creativity;
import bg.codexio.ai.openai.api.payload.credentials.ApiCredentials;
import bg.codexio.ai.openai.api.payload.voice.request.TranscriptionFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.voice.transcription.InternalAssertions.TEST_FILE;
import static bg.codexio.ai.openai.api.sdk.voice.transcription.InternalAssertions.TEST_LANGUAGE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimplifiedStageTest {

    private SimplifiedStage simplifiedStage;

    @BeforeEach
    public void setUp() {
        this.simplifiedStage = new SimplifiedStage(
                new ApiCredentials(""),
                TEST_FILE
        );
    }

    @Test
    public void testEnsureRuntimeSelectionStage_expectFilledRuntimeSelectionStage() {
        var nextStage = this.simplifiedStage.andRespond();

        assertAll(
                () -> assertEquals(
                        new Whisper10().name(),
                        nextStage.requestBuilder.model()
                ),
                () -> assertEquals(
                        TEST_FILE,
                        nextStage.requestBuilder.file()
                ),
                () -> assertEquals(
                        Creativity.TRULY_DETERMINISTIC.val(),
                        nextStage.requestBuilder.temperature()
                ),
                () -> assertEquals(
                        TEST_LANGUAGE,
                        nextStage.requestBuilder.language()
                ),
                () -> assertEquals(
                        TranscriptionFormat.TEXT.val(),
                        nextStage.requestBuilder.responseFormat()
                )
        );
    }

}
