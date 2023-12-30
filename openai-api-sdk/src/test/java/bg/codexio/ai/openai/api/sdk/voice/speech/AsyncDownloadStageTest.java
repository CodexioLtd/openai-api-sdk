package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static bg.codexio.ai.openai.api.sdk.voice.speech.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AsyncDownloadStageTest {

    private AsyncDownloadStage asyncDownloadStage;

    private static void otherValuesRemainUnchanged(
            SpeechConfigurationStage currentStage,
            SpeechConfigurationStage nextStage
    ) {
        assertAll(
                () -> modelRemainsUnchanged(nextStage),
                () -> voiceRemainsUnchanged(nextStage),
                () -> formatRemainsUnchanged(nextStage),
                () -> speedRemainsUnchanged(nextStage),
                () -> inputRemainsUnchanged(nextStage),
                () -> executorRemainsUnchanged(
                        currentStage,
                        nextStage
                )
        );
    }

    @BeforeEach
    public void setUp() {
        this.asyncDownloadStage = new AsyncDownloadStage(
                TEST_EXECUTOR,
                SpeechRequest.builder()
                             .withModel(TEST_MODEL.name())
                             .withVoice(TEST_SPEAKER.val())
                             .withFormat(TEST_AUDIO.val())
                             .withSpeed(TEST_SPEED.val())
                             .withInput(TEST_INPUT)
        );
    }

    @Test
    public void testDownloadTo_withImaginaryFolder_shouldPassAlong() {
        var targetFolder = new File("imaginaryFolder");

        var nextStage = this.asyncDownloadStage.whenDownloadedTo(targetFolder);

        assertAll(
                () -> assertEquals(
                        targetFolder,
                        nextStage.getTargetFolder()
                ),
                () -> otherValuesRemainUnchanged(
                        this.asyncDownloadStage,
                        nextStage
                )
        );
    }
}
