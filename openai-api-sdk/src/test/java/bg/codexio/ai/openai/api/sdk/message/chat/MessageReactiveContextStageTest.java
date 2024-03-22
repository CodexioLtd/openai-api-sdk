package bg.codexio.ai.openai.api.sdk.message.chat;

import bg.codexio.ai.openai.api.http.ReactiveHttpExecutor;
import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.OBJECT_MAPPER;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.message.chat.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MessageReactiveContextStageTest {

    public MessageReactiveContextStage messageReactiveContextStage;

    @BeforeEach
    void setUp() {
        this.messageReactiveContextStage = new MessageReactiveContextStage(
                MESSAGE_HTTP_EXECUTOR,
                MessageRequest.builder()
                              .withContent(MESSAGE_CONTENT),
                THREAD_ID
        );
    }

    @Test
    public void testFinishRaw_expectCorrectResponse()
            throws JsonProcessingException {
        when(this.messageReactiveContextStage.httpExecutor.reactive()
                                                          .executeWithPathVariable(
                                                                  any(),
                                                                  any()
                                                          )).thenAnswer(answer -> new ReactiveHttpExecutor.ReactiveExecution<>(
                Flux.just(OBJECT_MAPPER.writeValueAsString(MESSAGE_RESPONSE)),
                Mono.empty()
        ));

        var response = this.messageReactiveContextStage.finishRaw();

        assertEquals(
                OBJECT_MAPPER.writeValueAsString(MESSAGE_RESPONSE),
                response.lines()
                        .blockFirst()
        );
    }

    @Test
    public void testFinish_expectCorrectResponse() {
        when(this.messageReactiveContextStage.httpExecutor.reactive()
                                                          .executeWithPathVariable(
                                                                  any(),
                                                                  any()
                                                          )).thenAnswer(answer -> new ReactiveHttpExecutor.ReactiveExecution<>(
                Flux.empty(),
                Mono.just(MESSAGE_RESPONSE)
        ));

        var response = this.messageReactiveContextStage.finish()
                                                       .block();

        assertEquals(
                MESSAGE_RESPONSE,
                response
        );
    }
}