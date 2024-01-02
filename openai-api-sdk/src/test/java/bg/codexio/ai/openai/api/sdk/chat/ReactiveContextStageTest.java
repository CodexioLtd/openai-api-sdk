package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.http.OpenAIHttpExecutor;
import bg.codexio.ai.openai.api.models.ModelTypeAbstract;
import bg.codexio.ai.openai.api.models.v40.GPT4032kModel;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static bg.codexio.ai.openai.api.sdk.chat.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ReactiveContextStageTest {

    private static final ModelTypeAbstract MODEL_TYPE = new GPT4032kModel();
    private ReactiveContextStage reactiveContextStage;


    @BeforeEach
    public void setUp() {
        this.reactiveContextStage = new ReactiveContextStage(
                CHAT_EXECUTOR,
                ChatMessageRequest.builder()
                                  .withModel(MODEL_TYPE.name())
                                  .withTemperature(CREATIVITY.val())
                                  .withTopP(CREATIVITY.val())
                                  .withFrequencyPenalty(REPETITION_PENALTY.val())
                                  .withPresencePenalty(REPETITION_PENALTY.val())
                                  .withMaxTokens(MAX_TOKENS)
                                  .withN(CHOICES)
                                  .withStop(STOP)
                                  .addTool(TOOL)
                                  .addMessage(CHAT_MESSAGE)
        );
    }

    @Test
    public void testAskRaw_manualMessage_expectRawAnswer() {
        when(this.reactiveContextStage.executor.executeReactive(any())).thenAnswer(answer -> new OpenAIHttpExecutor.ReactiveExecution<>(
                Flux.empty(),
                Mono.just(CHAT_MESSAGE_RESPONSE)
        ));

        var response = this.reactiveContextStage.askRaw("Test Question")
                                                .response()
                                                .block();

        assertEquals(
                CHAT_MESSAGE_RESPONSE,
                response
        );
    }

    @Test
    public void testAsk_manualMessage_expectAnswer() {
        when(this.reactiveContextStage.executor.executeReactive(any())).thenAnswer(answer -> new OpenAIHttpExecutor.ReactiveExecution<>(
                Flux.empty(),
                Mono.just(CHAT_MESSAGE_RESPONSE)
        ));

        var response = this.reactiveContextStage.ask("Test Question")
                                                .block();

        assertEquals(
                CHAT_MESSAGE_RESPONSE.choices()
                                     .get(0)
                                     .message()
                                     .content(),
                response
        );
    }


}
