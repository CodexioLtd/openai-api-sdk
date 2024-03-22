package bg.codexio.ai.openai.api.sdk.voice.transcription;

import bg.codexio.ai.openai.api.payload.voice.request.TranscriptionRequest;
import bg.codexio.ai.openai.api.payload.voice.response.SpeechTextResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.voice.transcription.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class SynchronousPromptStageTest {

    private SynchronousPromptStage synchronousPromptStage;

    @BeforeEach
    public void setUp() {
        this.synchronousPromptStage = new SynchronousPromptStage(
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
    public void testGuide_withUserValue_expectUserValue() {
        when(TEST_EXECUTOR.immediate()
                          .execute(any(TranscriptionRequest.class))).thenAnswer(answer -> new SpeechTextResponse(answer.getArgument(0)
                                                                                                                       .toString()));

        var result = this.synchronousPromptStage.guide(TEST_INPUT);

        assertEquals(
                this.synchronousPromptStage.requestBuilder.withPrompt(TEST_INPUT)
                                                          .build()
                                                          .toString(),
                result.text()
        );
    }

    @Test
    public void testUnguided_expectNoValue() {
        when(TEST_EXECUTOR.immediate()
                          .execute(any(TranscriptionRequest.class))).thenAnswer(answer -> new SpeechTextResponse(answer.getArgument(0)
                                                                                                                       .toString()));

        var result = this.synchronousPromptStage.unguided();

        assertEquals(
                this.synchronousPromptStage.requestBuilder.build()
                                                          .toString(),
                result.text()
        );
    }

}
