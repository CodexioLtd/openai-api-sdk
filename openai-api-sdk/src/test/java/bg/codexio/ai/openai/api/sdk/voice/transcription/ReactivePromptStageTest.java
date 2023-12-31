package bg.codexio.ai.openai.api.sdk.voice.transcription;

import bg.codexio.ai.openai.api.http.OpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.TranscriptionRequest;
import bg.codexio.ai.openai.api.payload.voice.response.SpeechTextResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static bg.codexio.ai.openai.api.sdk.voice.transcription.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ReactivePromptStageTest {

    private ReactivePromptStage reactivePromptStage;

    @BeforeEach
    public void setUp() {
        this.reactivePromptStage = new ReactivePromptStage(
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
        when(TEST_EXECUTOR.executeReactive(any(TranscriptionRequest.class))).thenAnswer(answer -> new OpenAIHttpExecutor.ReactiveExecution<>(
                Flux.empty(),
                Mono.just(new SpeechTextResponse(answer.getArgument(0)
                                                       .toString()))
        ));

        var result = this.reactivePromptStage.guide(TEST_INPUT);

        assertEquals(
                this.reactivePromptStage.requestBuilder.withPrompt(TEST_INPUT)
                                                       .build()
                                                       .toString(),
                result.response()
                      .block()
                      .text()
        );
    }

    @Test
    public void testUnguided_expectNoValue() {
        when(TEST_EXECUTOR.executeReactive(any(TranscriptionRequest.class))).thenAnswer(answer -> new OpenAIHttpExecutor.ReactiveExecution<>(
                Flux.empty(),
                Mono.just(new SpeechTextResponse(answer.getArgument(0)
                                                       .toString()))
        ));

        var result = this.reactivePromptStage.unguided();

        assertEquals(
                this.reactivePromptStage.requestBuilder.build()
                                                       .toString(),
                result.response()
                      .block()
                      .text()
        );
    }

}
