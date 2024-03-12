package bg.codexio.ai.openai.api.sdk.message.answer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.message.answer.InternalAssertions.RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MessageAnswersRuntimeSelectionStageTest {

    public MessageAnswersRuntimeSelectionStage messageAnswersRuntimeSelectionStage;

    @BeforeEach
    void setUp() {
        this.messageAnswersRuntimeSelectionStage =
                new MessageAnswersRuntimeSelectionStage(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                THREAD_ID
        );
    }

    @Test
    public void testImmediate_expectCorrectBuilder() {
        assertNotNull(this.messageAnswersRuntimeSelectionStage.immediate());
    }

    @Test
    public void testAsync_expectCorrectBuilder() {
        assertNotNull(this.messageAnswersRuntimeSelectionStage.async());
    }

    @Test
    public void testReactive_expectCorrectBuilder() {
        assertNotNull(this.messageAnswersRuntimeSelectionStage.reactive());
    }
}