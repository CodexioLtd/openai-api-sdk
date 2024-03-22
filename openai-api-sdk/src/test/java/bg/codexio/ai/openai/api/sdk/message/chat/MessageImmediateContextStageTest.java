package bg.codexio.ai.openai.api.sdk.message.chat;

import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.message.chat.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MessageImmediateContextStageTest {

    private MessageImmediateContextStage messageImmediateContextStage;

    @BeforeEach
    void setUp() {
        this.messageImmediateContextStage = new MessageImmediateContextStage(
                MESSAGE_HTTP_EXECUTOR,
                MessageRequest.builder()
                              .withContent(MESSAGE_CONTENT),
                THREAD_ID
        );
    }

    @Test
    public void testFinish_expectCorrectResponse() {
        when(this.messageImmediateContextStage.httpExecutor.immediate()
                                                           .executeWithPathVariable(
                                                                   any(),
                                                                   any()
                                                           )).thenAnswer(res -> MESSAGE_RESPONSE);

        var response = this.messageImmediateContextStage.finish();

        assertEquals(
                MESSAGE_RESPONSE,
                response
        );
    }
}