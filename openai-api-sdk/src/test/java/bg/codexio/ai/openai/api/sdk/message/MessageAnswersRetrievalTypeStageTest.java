package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.sdk.message.answer.MessageAnswersRetrievalTypeStage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.message.InternalAssertions.*;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_RESPONSE;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        executeWithPathVariables(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                LIST_MESSAGE_RESPONSE
        );
        var response = this.messageAnswersRetrievalTypeStage.answersRaw();

        assertNotNull(response);
    }

    @Test
    void testAnswersRaw_withTextContentResponse_expectCorrectResponse() {
        executeWithPathVariables(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                LIST_MESSAGE_RESPONSE_WITH_TEXT_CONTENT
        );
        var response = this.messageAnswersRetrievalTypeStage.answersRaw();

        assertNotNull(response);
    }

    @Test
    void testAnswersRaw_withImageContentResponse_expectCorrectResponse() {
        executeWithPathVariables(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                LIST_MESSAGE_RESPONSE_WITH_IMAGE_CONTENT
        );
        var response = this.messageAnswersRetrievalTypeStage.answersRaw();

        assertNotNull(response);
    }

    @Test
    void testAnswersRaw_withThreadId_expectCorrectResponse() {
        executeWithPathVariables(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                LIST_MESSAGE_RESPONSE
        );
        var response =
                this.messageAnswersRetrievalTypeStage.answersRaw(THREAD_ID);

        assertNotNull(response);
    }

    @Test
    void testAnswersRaw_withThreadIdAndWithTextContentResponse_expectCorrectResponse() {
        executeWithPathVariables(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                LIST_MESSAGE_RESPONSE_WITH_TEXT_CONTENT
        );
        var response = this.messageAnswersRetrievalTypeStage.answersRaw(THREAD_ID);

        assertNotNull(response);
    }

    @Test
    void testAnswersRaw_withThreadIdAndWithImageContentResponse_expectCorrectResponse() {
        executeWithPathVariables(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                LIST_MESSAGE_RESPONSE_WITH_IMAGE_CONTENT
        );
        var response =
                this.messageAnswersRetrievalTypeStage.answersRaw(THREAD_ID);

        assertNotNull(response);
    }

    @Test
    void testAnswersRaw_withThreadResponse_expectCorrectResponse() {
        executeWithPathVariables(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                LIST_MESSAGE_RESPONSE
        );
        var response =
                this.messageAnswersRetrievalTypeStage.answersRaw(THREAD_RESPONSE);

        assertNotNull(response);
    }

    @Test
    void testAnswersRaw_withThreadResponseAndWithTextContentResponse_expectCorrectResponse() {
        executeWithPathVariables(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                LIST_MESSAGE_RESPONSE_WITH_TEXT_CONTENT
        );
        var response = this.messageAnswersRetrievalTypeStage.answersRaw(THREAD_RESPONSE);

        assertNotNull(response);
    }

    @Test
    void testAnswersRaw_withThreadResponseAndWithImageContentResponse_expectCorrectResponse() {
        executeWithPathVariables(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                LIST_MESSAGE_RESPONSE_WITH_IMAGE_CONTENT
        );
        var response =
                this.messageAnswersRetrievalTypeStage.answersRaw(THREAD_RESPONSE);

        assertNotNull(response);
    }

    @Test
    void testAnswers_expectCorrectResponse() {
        executeWithPathVariables(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                LIST_MESSAGE_RESPONSE
        );
        var result = this.messageAnswersRetrievalTypeStage.retrieve();

        assertEquals(
                MESSAGE_TEST_RESULT,
                result
        );
    }

    @Test
    void testAnswers_withTextContentResponse_expectCorrectResponse() {
        executeWithPathVariables(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                LIST_MESSAGE_RESPONSE_WITH_TEXT_CONTENT
        );
        var result = this.messageAnswersRetrievalTypeStage.retrieve();

        assertEquals(
                MESSAGE_TEST_RESULT_WITHOUT_IMAGE,
                result
        );
    }

    @Test
    void testAnswers_withImageContentResponse_expectCorrectResponse() {
        executeWithPathVariables(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                LIST_MESSAGE_RESPONSE_WITH_IMAGE_CONTENT
        );
        var result = this.messageAnswersRetrievalTypeStage.retrieve();

        assertEquals(
                MESSAGE_TEST_RESULT_WITHOUT_TEXT,
                result
        );
    }

    @Test
    void testAnswers_withThreadId_expectCorrectResponse() {
        executeWithPathVariables(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                LIST_MESSAGE_RESPONSE
        );
        var result = this.messageAnswersRetrievalTypeStage.retrieve(THREAD_ID);

        assertEquals(
                MESSAGE_TEST_RESULT,
                result
        );
    }

    @Test
    void testAnswers_withThreadIdAndWithTextContentResponse_expectCorrectResponse() {
        executeWithPathVariables(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                LIST_MESSAGE_RESPONSE_WITH_TEXT_CONTENT
        );
        var result = this.messageAnswersRetrievalTypeStage.retrieve(THREAD_ID);

        assertEquals(
                MESSAGE_TEST_RESULT_WITHOUT_IMAGE,
                result
        );
    }

    @Test
    void testAnswers_withThreadIdAndWithImageContentResponse_expectCorrectResponse() {
        executeWithPathVariables(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                LIST_MESSAGE_RESPONSE_WITH_IMAGE_CONTENT
        );
        var result = this.messageAnswersRetrievalTypeStage.retrieve(THREAD_ID);
        assertEquals(
                MESSAGE_TEST_RESULT_WITHOUT_TEXT,
                result
        );
    }

    @Test
    void testAnswers_withThreadResponse_expectCorrectResponse() {
        executeWithPathVariables(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                LIST_MESSAGE_RESPONSE
        );
        var result = this.messageAnswersRetrievalTypeStage.retrieve(THREAD_RESPONSE);

        assertEquals(
                MESSAGE_TEST_RESULT,
                result
        );
    }

    @Test
    void testAnswers_withThreadResponseAndWithTextContentResponse_expectCorrectResponse() {
        executeWithPathVariables(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                LIST_MESSAGE_RESPONSE_WITH_TEXT_CONTENT
        );
        var result = this.messageAnswersRetrievalTypeStage.retrieve(THREAD_RESPONSE);

        assertEquals(
                MESSAGE_TEST_RESULT_WITHOUT_IMAGE,
                result
        );
    }

    @Test
    void testAnswers_withThreadResponseAndWithImageContentResponse_expectCorrectResponse() {
        executeWithPathVariables(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                LIST_MESSAGE_RESPONSE_WITH_IMAGE_CONTENT
        );
        var result = this.messageAnswersRetrievalTypeStage.retrieve(THREAD_RESPONSE);

        assertEquals(
                MESSAGE_TEST_RESULT_WITHOUT_TEXT,
                result
        );
    }
}