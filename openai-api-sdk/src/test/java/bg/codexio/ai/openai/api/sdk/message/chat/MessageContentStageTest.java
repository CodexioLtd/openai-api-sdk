package bg.codexio.ai.openai.api.sdk.message.chat;

import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.message.chat.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageContentStageTest {

    private MessageContentStage messageContentStage;

    @BeforeEach
    void setUp() {
        this.messageContentStage = new MessageContentStage(
                MESSAGE_HTTP_EXECUTOR,
                MessageRequest.builder(),
                THREAD_ID
        );
    }

    @Test
    void testWithContent_expectCorrectBuilder() {
        var nextStage = this.messageContentStage.withContent(MESSAGE_CONTENT);
        this.previousValuesRemainsUnchanged(nextStage);

        assertEquals(
                MESSAGE_CONTENT,
                nextStage.requestBuilder.content()
        );
    }

    private void previousValuesRemainsUnchanged(MessageConfigurationStage nextStage) {
        assertAll(
                () -> roleRemainsUnchanged(
                        this.messageContentStage,
                        nextStage
                ),
                () -> metadataRemainsUnchanged(
                        this.messageContentStage,
                        nextStage
                ),
                () -> fileIdsRemainsUnchanged(
                        this.messageContentStage,
                        nextStage
                )
        );
    }
}