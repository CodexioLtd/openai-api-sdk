package bg.codexio.ai.openai.api.sdk.voice.translation;

import bg.codexio.ai.openai.api.payload.voice.request.TranslationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.voice.translation.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AudioFileStageTest {

    private AudioFileStage audioFileStage;

    private static void otherValuesRemainUnchanged(
            TranslationConfigurationStage currentStage,
            TranslationConfigurationStage nextStage
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
                TEST_EXECUTOR,
                TranslationRequest.builder()
                                  .withModel(TEST_MODEL.name())
        );
    }

    @Test
    public void testTranscribe_expectSuppliedFile() {
        var nextStage = this.audioFileStage.translate(TEST_FILE);

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
