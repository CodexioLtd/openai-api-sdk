package bg.codexio.ai.openai.api.sdk.message.answer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.message.answer.InternalAssertions.RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MessageAnswerAsyncContextStageTest {

    public MessageAnswersAsyncContextStage messageAnswersAsyncContextStage;

    @BeforeEach
    void setUp() {
        this.messageAnswersAsyncContextStage =
                new MessageAnswersAsyncContextStage(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                THREAD_ID
        );
    }

    @Test
    public void testAnswers_expectCorrectBuilder() {
        assertNotNull(this.messageAnswersAsyncContextStage.answers());
    }
}