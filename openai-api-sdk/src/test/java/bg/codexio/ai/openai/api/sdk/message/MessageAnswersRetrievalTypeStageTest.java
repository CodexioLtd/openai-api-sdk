package bg.codexio.ai.openai.api.sdk.message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.message.InternalAssertions.RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.message.InternalAssertions.executeWithPathVariables;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_RESPONSE;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MessageAnswersRetrievalTypeStageTest {

    private MessageAnswersRetrievalTypeStage messageAnswersRetrievalTypeStage;

    @BeforeEach
    void setUp() {
        this.messageAnswersRetrievalTypeStage =
                new MessageAnswersRetrievalTypeStage(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                THREAD_ID
        );
    }

    @Test
    void testAnswersRaw_expectCorrectResponse() {
        executeWithPathVariables(RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR);
        var response = this.messageAnswersRetrievalTypeStage.answersRaw();

        assertNotNull(response);
    }

    @Test
    void testAnswersRaw_withThreadId_expectCorrectResponse() {
        executeWithPathVariables(RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR);
        var response =
                this.messageAnswersRetrievalTypeStage.answersRaw(THREAD_ID);

        assertNotNull(response);
    }

    @Test
    void testAnswersRaw_withThreadResponse_expectCorrectResponse() {
        executeWithPathVariables(RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR);
        var response =
                this.messageAnswersRetrievalTypeStage.answersRaw(THREAD_RESPONSE);

        assertNotNull(response);
    }

    @Test
    void testAnswers_expectCorrectResponse() {
        executeWithPathVariables(RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR);
        var nextStage = this.messageAnswersRetrievalTypeStage.answers();
        assertNotNull(nextStage);
    }

    @Test
    void testAnswers_withThreadId_expectCorrectResponse() {
        executeWithPathVariables(RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR);
        var nextStage =
                this.messageAnswersRetrievalTypeStage.answers(THREAD_ID);
        assertNotNull(nextStage);
    }

    @Test
    void testAnswers_withThreadResponse_expectCorrectResponse() {
        executeWithPathVariables(RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR);
        var nextStage =
                this.messageAnswersRetrievalTypeStage.answers(THREAD_RESPONSE);
        assertNotNull(nextStage);
    }
}