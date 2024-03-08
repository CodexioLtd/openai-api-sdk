package bg.codexio.ai.openai.api.sdk.message.chat;

import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.sdk.message.DefaultMessageConfigurationStage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.METADATA_MAP;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.METADATA_VAR_ARGS;
import static bg.codexio.ai.openai.api.sdk.message.InternalAssertions.*;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_ID;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageMetaStageTest {

    private MessageMetaStage messageMetaStage;

    @BeforeEach
    void setUp() {
        this.messageMetaStage = new MessageMetaStage(
                MESSAGE_HTTP_EXECUTOR,
                MessageRequest.builder()
                              .withContent(MESSAGE_CONTENT),
                THREAD_ID
        );
    }

    @Test
    void testAwareOf_expectCorrectBuilder() {
        var nextStage = this.messageMetaStage.awareOf(METADATA_VAR_ARGS);
        this.previousValuesRemainsUnchanged(nextStage);

        assertEquals(
                METADATA_MAP,
                nextStage.requestBuilder.metadata()
        );
    }

    private void previousValuesRemainsUnchanged(DefaultMessageConfigurationStage nextStage) {
        assertAll(
                () -> roleRemainsUnchanged(
                        this.messageMetaStage,
                        nextStage
                ),
                () -> fileIdsRemainsUnchanged(
                        this.messageMetaStage,
                        nextStage
                ),
                () -> contentRemainsUnchanged(
                        this.messageMetaStage,
                        nextStage
                )
        );
    }
}
