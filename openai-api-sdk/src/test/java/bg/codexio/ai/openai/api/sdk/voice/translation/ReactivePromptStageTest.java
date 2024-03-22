package bg.codexio.ai.openai.api.sdk.voice.translation;

import bg.codexio.ai.openai.api.http.ReactiveHttpExecutor;
import bg.codexio.ai.openai.api.payload.voice.request.TranslationRequest;
import bg.codexio.ai.openai.api.payload.voice.response.SpeechTextResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static bg.codexio.ai.openai.api.sdk.voice.translation.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ReactivePromptStageTest {

    private ReactivePromptStage reactivePromptStage;

    @BeforeEach
    public void setUp() {
        this.reactivePromptStage = new ReactivePromptStage(
                TEST_EXECUTOR,
                TranslationRequest.builder()
                                  .withModel(TEST_MODEL.name())
                                  .withTemperature(TEST_TEMPERATURE.val())
                                  .withFormat(TEST_AUDIO.val())
                                  .withFile(TEST_FILE)
        );
    }

    @Test
    public void testGuide_withUserValue_expectUserValue() {
        when(TEST_EXECUTOR.reactive()
                          .execute(any(TranslationRequest.class))).thenAnswer(answer -> new ReactiveHttpExecutor.ReactiveExecution<>(
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
        when(TEST_EXECUTOR.reactive()
                          .execute(any(TranslationRequest.class))).thenAnswer(answer -> new ReactiveHttpExecutor.ReactiveExecution<>(
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