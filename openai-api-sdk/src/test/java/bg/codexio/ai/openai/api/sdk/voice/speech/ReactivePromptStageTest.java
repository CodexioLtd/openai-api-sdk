package bg.codexio.ai.openai.api.sdk.voice.speech;

import bg.codexio.ai.openai.api.payload.voice.request.SpeechRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.voice.speech.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReactivePromptStageTest {

    private ReactivePromptStage reactivePromptStage;

    private static void otherValuesRemainUnchanged(
            SpeechConfigurationStage currentStage,
            SpeechConfigurationStage nextStage
    ) {
        assertAll(
                () -> modelRemainsUnchanged(nextStage),
                () -> voiceRemainsUnchanged(nextStage),
                () -> formatRemainsUnchanged(nextStage),
                () -> speedRemainsUnchanged(nextStage),
                () -> executorRemainsUnchanged(
                        currentStage,
                        nextStage
                )
        );
    }

    @BeforeEach
    public void setUp() {
        this.reactivePromptStage = new ReactivePromptStage(
                null,
                SpeechRequest.builder()
                             .withModel(TEST_MODEL.name())
                             .withVoice(TEST_SPEAKER.val())
                             .withFormat(TEST_AUDIO.val())
                             .withSpeed(TEST_SPEED.val())
        );
    }

    @Test
    public void testDictate_withUserValue_expectUserValue() {
        var nextStage = this.reactivePromptStage.dictate(TEST_INPUT);

        assertAll(
                () -> assertEquals(
                        TEST_INPUT,
                        nextStage.requestBuilder.input()
                ),
                () -> otherValuesRemainUnchanged(
                        this.reactivePromptStage,
                        nextStage
                )
        );
    }
}
