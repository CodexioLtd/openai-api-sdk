package bg.codexio.ai.openai.api.sdk.message;

import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.message.response.ListMessagesResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.message.InternalAssertions.RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.message.InternalAssertions.executeWithPathVariables;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_RESPONSE;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MessageAnswerStageTest {

    private MessageAnswerStage<ListMessagesResponse> messageAnswerStage;

    @BeforeEach
    void setUp() {
        this.messageAnswerStage = new MessageAnswerStage<>(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                MessageRequest.builder(),
                THREAD_ID

        );
    }

    @Test
    void testAnswersRaw_expectCorrectResponse() {
        executeWithPathVariables(this.messageAnswerStage);
        var response = this.messageAnswerStage.answersRaw();

        assertNotNull(response);
    }

    @Test
    void testAnswersRaw_withThreadId_expectCorrectResponse() {
        executeWithPathVariables(this.messageAnswerStage);
        var response = this.messageAnswerStage.answersRaw(THREAD_ID);

        assertNotNull(response);
    }

    @Test
    void testAnswersRaw_withThreadResponse_expectCorrectResponse() {
        executeWithPathVariables(this.messageAnswerStage);
        var response = this.messageAnswerStage.answersRaw(THREAD_RESPONSE);

        assertNotNull(response);
    }
}