package bg.codexio.ai.openai.api.sdk.message.answer;

import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_RESPONSE;
import static bg.codexio.ai.openai.api.sdk.message.Messages.throughHttp;
import static bg.codexio.ai.openai.api.sdk.message.answer.InternalAssertions.RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessagesTest {
    @Test
    public void testThroughHttp_withRetrieveListMessagesExecutorAndThreadId_expectEditExecutor() {
        var nextStage = throughHttp(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                THREAD_ID
        );

        assertEquals(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                nextStage.httpExecutor
        );
    }

    @Test
    public void testThroughHttp_withRetrieveListMessagesExecutorAndThreadResponse_expectEditExecutor() {
        var nextStage = throughHttp(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                THREAD_RESPONSE
        );

        assertEquals(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                nextStage.httpExecutor
        );
    }
}
