package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import bg.codexio.ai.openai.api.sdk.HttpBuilder;
import bg.codexio.ai.openai.api.sdk.message.MessageActionTypeStage;
import bg.codexio.ai.openai.api.sdk.message.MessageResult;
import bg.codexio.ai.openai.api.sdk.message.Messages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.message.InternalAssertions.LIST_MESSAGE_RESPONSE_WITH_TEXT_CONTENT;
import static bg.codexio.ai.openai.api.sdk.message.InternalAssertions.RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.run.InternalAssertions.RUNNABLE_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RunnableMessageResultTest {

    private RunnableMessageResult runnableMessageResult;

    @BeforeEach
    void setUp() {
        this.runnableMessageResult = new RunnableMessageResult(
                RUNNABLE_HTTP_EXECUTOR,
                RunnableRequest.builder(),
                THREAD_ID
        );
    }

    @Test
    void testAnswers_expectCorrectResponse() {
        var messageResult = new MessageResult(
                "test_quote_test_message_value",
                null,
                null
        );
        try (var mockedMessage = mockStatic(Messages.class)) {
            var httpBuilderMock = mock(HttpBuilder.class);
            mockedMessage.when(() -> Messages.defaults(THREAD_ID))
                         .thenReturn(httpBuilderMock);
            mockedMessage.when(httpBuilderMock::and)
                         .thenReturn(new MessageActionTypeStage(
                                 null,
                                 RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                                 THREAD_ID
                         ));
            when(RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR.executeWithPathVariables(any())).thenAnswer(res -> LIST_MESSAGE_RESPONSE_WITH_TEXT_CONTENT);

            var result = this.runnableMessageResult.answers();
            assertEquals(
                    messageResult,
                    result
            );
        }
    }
}
