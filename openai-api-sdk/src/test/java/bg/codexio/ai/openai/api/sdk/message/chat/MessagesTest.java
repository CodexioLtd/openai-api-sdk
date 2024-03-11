package bg.codexio.ai.openai.api.sdk.message.chat;

import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.message.Messages.throughHttp;
import static bg.codexio.ai.openai.api.sdk.message.chat.InternalAssertions.MESSAGE_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_RESPONSE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessagesTest {
    @Test
    public void testThroughHttp_withMessageExecutorAndThreadId_expectCreateExecutor() {
        var nextStage = throughHttp(
                MESSAGE_HTTP_EXECUTOR,
                THREAD_ID
        );

        assertEquals(
                MESSAGE_HTTP_EXECUTOR,
                nextStage.httpExecutor
        );
    }

    @Test
    public void testThroughHttp_withMessageExecutorAndThreadResponse_expectCreateExecutor() {
        var nextStage = throughHttp(
                MESSAGE_HTTP_EXECUTOR,
                THREAD_RESPONSE
        );

        assertEquals(
                MESSAGE_HTTP_EXECUTOR,
                nextStage.httpExecutor
        );
    }
}
