package bg.codexio.ai.openai.api.sdk.message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.message.InternalAssertions.MESSAGE_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.message.InternalAssertions.RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MessageActionTypeStageTest {

    private MessageActionTypeStage messageActionTypeStage;

    @BeforeEach
    void setUp() {
        this.messageActionTypeStage = new MessageActionTypeStage(
                MESSAGE_HTTP_EXECUTOR,
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                THREAD_ID
        );
    }

    @Test
    void testChat_expectCorrectBuilder() {
        var nextStage = this.messageActionTypeStage.chat();

        assertNotNull(nextStage);

    }

    @Test
    void testRespond_expectCorrectBuilder() {
        var nextStage = this.messageActionTypeStage.respond();

        assertNotNull(nextStage);
    }

}
