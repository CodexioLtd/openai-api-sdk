package bg.codexio.ai.openai.api.sdk.message.answer;

import bg.codexio.ai.openai.api.http.OpenAIHttpExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.message.answer.InternalAssertions.LIST_MESSAGE_RESPONSE;
import static bg.codexio.ai.openai.api.sdk.message.answer.InternalAssertions.RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MessageAnswersReactiveContextStageTest {

    private MessageAnswersReactiveContextStage messageReactiveContextStage;

    @BeforeEach
    void setUp() {
        this.messageReactiveContextStage =
                new MessageAnswersReactiveContextStage(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                THREAD_ID
        );
    }

    @Test
    void testAnswers_expectCorrectBuilder() {
        when(this.messageReactiveContextStage.httpExecutor.retrieveReactive(any())).thenAnswer(answer -> new OpenAIHttpExecutor.ReactiveExecution<>(
                Flux.empty(),
                Mono.just(LIST_MESSAGE_RESPONSE)
        ));

        assertNotNull(this.messageReactiveContextStage.answers()
                                                      .block());
    }
}
