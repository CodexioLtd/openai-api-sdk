package bg.codexio.ai.openai.api.sdk.voice.transcription;

import bg.codexio.ai.openai.api.payload.voice.request.TranscriptionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.voice.transcription.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class AsyncPromptStageTest {

    private AsyncPromptStage asyncPromptStage;

    private static void otherValuesRemainUnchanged(
            TranscriptionConfigurationStage currentStage,
            TranscriptionConfigurationStage nextStage
    ) {
        assertAll(
                () -> modelRemainsUnchanged(nextStage),
                () -> fileRemainsUnchanged(nextStage),
                () -> temperatureRemainsUnchanged(nextStage),
                () -> languageRemainsUnchanged(nextStage),
                () -> executorRemainsUnchanged(
                        currentStage,
                        nextStage
                )
        );
    }

    @BeforeEach
    public void setUp() {
        this.asyncPromptStage = new AsyncPromptStage(
                TEST_EXECUTOR,
                TranscriptionRequest.builder()
                                    .withModel(TEST_MODEL.name())
                                    .withTemperature(TEST_TEMPERATURE.val())
                                    .withFormat(TEST_AUDIO.val())
                                    .withLanguage(TEST_LANGUAGE)
                                    .withFile(TEST_FILE)
        );
    }

    @Test
    public void testGuide_withUserInput_shouldHaveInput() {
        var nextStage = this.asyncPromptStage.guide(TEST_INPUT);

        assertAll(
                () -> assertEquals(
                        TEST_INPUT,
                        nextStage.requestBuilder.prompt()
                ),
                () -> otherValuesRemainUnchanged(
                        this.asyncPromptStage,
                        nextStage
                )
        );
    }

    @Test
    public void testUnguided_expectNoInput() {
        var nextStage = this.asyncPromptStage.unguided();

        assertAll(
                () -> assertNull(nextStage.requestBuilder.prompt()),
                () -> otherValuesRemainUnchanged(
                        this.asyncPromptStage,
                        nextStage
                )
        );
    }
}
