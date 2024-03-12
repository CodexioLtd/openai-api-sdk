package bg.codexio.ai.openai.api.sdk.message.chat;

import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.message.chat.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class MessageRuntimeSelectionStageTest {

    private MessageRuntimeSelectionStage messageRuntimeSelectionStage;

    @BeforeEach
    void setUp() {
        this.messageRuntimeSelectionStage = new MessageRuntimeSelectionStage(
                MESSAGE_HTTP_EXECUTOR,
                MessageRequest.builder()
                              .withContent(MESSAGE_CONTENT),
                THREAD_ID
        );
    }

    @Test
    public void testImmediate_expectCorrectBuilder() {
        var nextStage = this.messageRuntimeSelectionStage.immediate();

        previousValuesRemainsUnchanged(nextStage);
    }

    @Test
    public void testAsync_expectCorrectBuilder() {
        var nextStage = this.messageRuntimeSelectionStage.async();

        previousValuesRemainsUnchanged(nextStage);
    }

    @Test
    public void testReactive_expectCorrectBuilder() {
        var nextStage = this.messageRuntimeSelectionStage.reactive();

        previousValuesRemainsUnchanged(nextStage);
    }

    private void previousValuesRemainsUnchanged(MessageConfigurationStage nextStage) {
        assertAll(
                () -> roleRemainsUnchanged(
                        this.messageRuntimeSelectionStage,
                        nextStage
                ),
                () -> metadataRemainsUnchanged(
                        this.messageRuntimeSelectionStage,
                        nextStage
                ),
                () -> fileIdsRemainsUnchanged(
                        this.messageRuntimeSelectionStage,
                        nextStage
                ),
                () -> contentRemainsUnchanged(
                        this.messageRuntimeSelectionStage,
                        nextStage
                )
        );
    }
}
