package bg.codexio.ai.openai.api.sdk.voice.transcription;

import bg.codexio.ai.openai.api.payload.voice.request.TranscriptionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.voice.transcription.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AudioFileStageTest {

    private AudioFileStage audioFileStage;

    private static void otherValuesRemainUnchanged(
            TranscriptionConfigurationStage currentStage,
            TranscriptionConfigurationStage nextStage
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
        this.audioFileStage = new AudioFileStage(
                null,
                TranscriptionRequest.builder()
                                    .withModel(TEST_MODEL.name())
        );
    }

    @Test
    public void testTranscribe_expectSuppliedFile() {
        var nextStage = this.audioFileStage.transcribe(TEST_FILE);

        assertAll(
                () -> assertEquals(
                        TEST_FILE,
                        nextStage.requestBuilder.file()
                ),
                () -> otherValuesRemainUnchanged(
                        this.audioFileStage,
                        nextStage
                )
        );
    }

}
