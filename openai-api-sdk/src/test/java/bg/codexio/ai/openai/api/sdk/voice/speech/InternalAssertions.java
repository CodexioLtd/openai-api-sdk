package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.http.voice.SpeechHttpExecutor;
import bg.codexio.ai.openai.api.models.ModelTypeAbstract;
import bg.codexio.ai.openai.api.models.tts.TTS1Model;
import bg.codexio.ai.openai.api.payload.voice.AudioFormat;
import bg.codexio.ai.openai.api.payload.voice.Speaker;
import bg.codexio.ai.openai.api.payload.voice.Speed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public final class InternalAssertions {

    static final ModelTypeAbstract TEST_MODEL = new TTS1Model();
    static final Speaker TEST_SPEAKER = Speaker.FABLE;
    static final AudioFormat TEST_AUDIO = AudioFormat.MP3;
    static final Speed TEST_SPEED = Speed.NORMAL;
    static final String TEST_INPUT = "test input";
    static final SpeechHttpExecutor TEST_EXECUTOR =
            mock(SpeechHttpExecutor.class);

    private InternalAssertions() {

    }


    static void modelRemainsUnchanged(SpeechConfigurationStage nextStage) {
        assertEquals(
                TEST_MODEL.name(),
                nextStage.requestBuilder.model()
        );
    }

    static void voiceRemainsUnchanged(SpeechConfigurationStage nextStage) {
        assertEquals(
                TEST_SPEAKER.val(),
                nextStage.requestBuilder.voice()
        );
    }

    static void formatRemainsUnchanged(SpeechConfigurationStage nextStage) {
        assertEquals(
                TEST_AUDIO.val(),
                nextStage.requestBuilder.responseFormat()
        );
    }

    static void speedRemainsUnchanged(SpeechConfigurationStage nextStage) {
        assertEquals(
                TEST_SPEED.val(),
                nextStage.requestBuilder.speed()
        );
    }

    static void inputRemainsUnchanged(SpeechConfigurationStage nextStage) {
        assertEquals(
                TEST_INPUT,
                nextStage.requestBuilder.input()
        );
    }

    static void executorRemainsUnchanged(
            SpeechConfigurationStage source,
            SpeechConfigurationStage target
    ) {
        assertEquals(
                source.executor,
                target.executor
        );
    }
}
