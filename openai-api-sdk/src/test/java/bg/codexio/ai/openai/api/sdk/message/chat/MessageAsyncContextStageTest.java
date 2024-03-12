package bg.codexio.ai.openai.api.sdk.message.chat;

import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.message.chat.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class MessageAsyncContextStageTest {

    private MessageAsyncContextStage messageAsyncContextStage;

    @BeforeEach
    void setUp() {
        this.messageAsyncContextStage = new MessageAsyncContextStage(
                MESSAGE_HTTP_EXECUTOR,
                MessageRequest.builder()
                              .withContent(MESSAGE_CONTENT),
                THREAD_ID
        );
    }

    @Test
    void testFinish_expectCorrectBuilder() {
        var nextStage = this.messageAsyncContextStage.finish();

        previousValuesRemainsUnchanged(nextStage);
    }

    private void previousValuesRemainsUnchanged(MessageConfigurationStage nextStage) {
        assertAll(
                () -> roleRemainsUnchanged(
                        this.messageAsyncContextStage,
                        nextStage
                ),
                () -> metadataRemainsUnchanged(
                        this.messageAsyncContextStage,
                        nextStage
                ),
                () -> fileIdsRemainsUnchanged(
                        this.messageAsyncContextStage,
                        nextStage
                ),
                () -> contentRemainsUnchanged(
                        this.messageAsyncContextStage,
                        nextStage
                )
        );
    }
}
