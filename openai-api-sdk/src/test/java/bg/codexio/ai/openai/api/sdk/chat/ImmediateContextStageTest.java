package bg.codexio.ai.openai.api.sdk.chat;

import bg.codexio.ai.openai.api.payload.chat.ChatMessage;
import bg.codexio.ai.openai.api.payload.chat.request.ChatMessageRequest;
import bg.codexio.ai.openai.api.payload.chat.response.ChatMessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.chat.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ImmediateContextStageTest {
    private ImmediateContextStage immediateContextStage;

    @BeforeEach
    public void setUp() {
        this.immediateContextStage = new ImmediateContextStage(
                CHAT_EXECUTOR,
                ChatMessageRequest.builder()
                                  .withModel(MODEL_TYPE.name())
                                  .withTermperature(CREATIVITY.val())
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
    public void testAskRaw_expectCorrectResponse() {
        when(this.immediateContextStage.executor.immediate()
                                                .execute(any())).thenAnswer(answer -> CHAT_MESSAGE_RESPONSE);

        var response = immediateContextStage.askRaw("Test Question");

        assertEquals(
                CHAT_MESSAGE_RESPONSE,
                response
        );
    }

    @Test
    public void testAsk_expectCorrectResponse() {
        when(this.immediateContextStage.executor.immediate()
                                                .execute(any())).thenAnswer(answer -> CHAT_MESSAGE_RESPONSE);

        var response = immediateContextStage.ask("Test Question");

        assertEquals(
                this.getChatMessage(CHAT_MESSAGE_RESPONSE)
                    .content(),
                response
        );
    }

    @Test
    public void testAsk_withToolCalls_expectCorrectResponse() {
        when(immediateContextStage.executor.immediate()
                                           .execute(any())).thenAnswer(answer -> CHAT_MESSAGE_RESPONSE_2);

        var response = immediateContextStage.ask("Test Question");

        assertEquals(
                this.getChatMessage(CHAT_MESSAGE_RESPONSE_2)
                    .toolCalls()
                    .get(0)
                    .function()
                    .arguments(),
                response
        );
    }

    private ChatMessage getChatMessage(ChatMessageResponse chatMessageResponse) {
        return chatMessageResponse.choices()
                                  .get(0)
                                  .message();
    }
}
