package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;
import bg.codexio.ai.openai.api.payload.chat.response.ChatMessageResponse;
import bg.codexio.ai.openai.api.sdk.AsyncCallbackUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.chat.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AsyncContextStageTest {
    private AsyncContextStage asyncContextStage;

    @BeforeEach
    public void setUp() {
        this.asyncContextStage = new AsyncContextStage(
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
    public void testAsk_manualMessage_expectMessagesAsChoice() {
        AsyncCallbackUtils.mockAsyncExecution(
                CHAT_EXECUTOR,
                CHAT_MESSAGE_RESPONSE,
                "test-response"
        );

        var responses = new String[1];

        this.asyncContextStage.ask(
                    FIRST_QUESTION,
                    SECOND_QUESTION
            )
                              .then(r -> {
                                  responses[0] = r;
                              });

        assertEquals(
                responses[0],
                CHAT_MESSAGE_RESPONSE.choices()
                                     .get(0)
                        .message()
                                     .content()
        );
    }

    @Test
    public void testAskRaw_manualMessage_expectMessagesAsChoice() {
        AsyncCallbackUtils.mockAsyncExecution(
                CHAT_EXECUTOR,
                CHAT_MESSAGE_RESPONSE,
                "test-response"
        );

        var responses = new ChatMessageResponse[1];

        this.asyncContextStage.askRaw(
                    FIRST_QUESTION,
                    SECOND_QUESTION
            )
                              .then(r -> {
                                  responses[0] = r;
                              });

        assertEquals(
                responses[0],
                CHAT_MESSAGE_RESPONSE
        );
    }

    @Test
    public void testOnEachLine_manualMessage_expectMessagesAsChoice() {
        AsyncCallbackUtils.mockAsyncExecution(
                CHAT_EXECUTOR,
                CHAT_MESSAGE_RESPONSE,
                "test-response"
        );

        var responses = new StringBuilder();

        this.asyncContextStage.askRaw(
                    FIRST_QUESTION,
                    SECOND_QUESTION
            )
                              .onEachLine(responses::append);

        assertEquals(
                "test-response",
                responses.toString()
        );
    }
}
