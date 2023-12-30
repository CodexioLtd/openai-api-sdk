package bg.codexio.ai.openai.api.sdk.voice.translation;

import bg.codexio.ai.openai.api.http.voice.TranslationHttpExecutor;
import bg.codexio.ai.openai.api.models.ModelTypeAbstract;
import bg.codexio.ai.openai.api.models.whisper.Whisper10;
import bg.codexio.ai.openai.api.payload.creativity.Creativity;
import bg.codexio.ai.openai.api.payload.voice.AudioFormat;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public final class InternalAssertions {

    static final File TEST_FILE = new File("/var/files/test-file.mp3");
    static final ModelTypeAbstract TEST_MODEL = new Whisper10();
    static final AudioFormat TEST_AUDIO = AudioFormat.MP3;
    static final Creativity TEST_TEMPERATURE =
            Creativity.FIRST_JUMP_TO_CREATIVITY;
    static final String TEST_INPUT = "test input";
    static final TranslationHttpExecutor TEST_EXECUTOR =
            mock(TranslationHttpExecutor.class);

    private InternalAssertions() {

    }

    static void fileRemainsUnchanged(TranslationConfigurationStage nextStage) {
        assertEquals(
                TEST_FILE,
                nextStage.requestBuilder.file()
        );
    }

    static void modelRemainsUnchanged(TranslationConfigurationStage nextStage) {
        assertEquals(
                TEST_MODEL.name(),
                nextStage.requestBuilder.model()
        );
    }

    static void formatRemainsUnchanged(TranslationConfigurationStage nextStage) {
        assertEquals(
                TEST_AUDIO.val(),
                nextStage.requestBuilder.responseFormat()
        );
    }

    static void inputRemainsUnchanged(TranslationConfigurationStage nextStage) {
        assertEquals(
                TEST_INPUT,
                nextStage.requestBuilder.prompt()
        );
    }

    static void temperatureRemainsUnchanged(TranslationConfigurationStage nextStage) {
        assertEquals(
                TEST_TEMPERATURE.val(),
                nextStage.requestBuilder.temperature()
        );
    }

    static void executorRemainsUnchanged(
            TranslationConfigurationStage source,
            TranslationConfigurationStage target
    ) {
        assertEquals(
                source.executor,
                target.executor
        );
    }
}
