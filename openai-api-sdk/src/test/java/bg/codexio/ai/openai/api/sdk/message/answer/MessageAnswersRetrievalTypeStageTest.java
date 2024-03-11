package bg.codexio.ai.openai.api.sdk.message.answer;

import bg.codexio.ai.openai.api.payload.message.response.ListMessagesResponse;
import bg.codexio.ai.openai.api.sdk.message.MessageResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static bg.codexio.ai.openai.api.sdk.message.answer.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageAnswersRetrievalTypeStageTest {

    private MessageAnswersRetrievalTypeStage messageAnswersRetrievalTypeStage;

    private static Stream<Arguments> provideTestVariablesForEmptyMessageResultTestCase() {
        return Stream.of(
                Arguments.of(LIST_MESSAGES_RESPONSE_WITH_EMPTY_DATA),
                Arguments.of(LIST_MESSAGE_RESPONSE_WITHOUT_DATA)
        );
    }

    @Test
    void testRetrieve_withFullResponse_expectCorrectResponse() {
        this.messageAnswersRetrievalTypeStage =
                new MessageAnswersRetrievalTypeStage(LIST_MESSAGE_RESPONSE);

        assertEquals(
                MESSAGE_TEST_RESULT,
                this.messageAnswersRetrievalTypeStage.retrieve()
        );
    }

    @Test
    void testRetrieve_withTextContentAndFileCitationResponse_expectCorrectResponse() {
        this.messageAnswersRetrievalTypeStage =
                new MessageAnswersRetrievalTypeStage(LIST_MESSAGE_RESPONSE_WITH_TEXT_CONTENT_WITH_FILE_CITATION);

        assertEquals(
                MESSAGE_TEST_RESULT_WITHOUT_IMAGE,
                this.messageAnswersRetrievalTypeStage.retrieve()
        );
    }

    @Test
    void testRetrieve_withTextContentAndFilePathResponse_expectCorrectResponse() {
        this.messageAnswersRetrievalTypeStage =
                new MessageAnswersRetrievalTypeStage(LIST_MESSAGE_RESPONSE_WITH_TEXT_CONTENT_WITH_FILE_PATH);

        assertEquals(
                MESSAGE_TEST_RESULT_WITH_FILE_RESULT,
                this.messageAnswersRetrievalTypeStage.retrieve()
        );
    }

    @Test
    void testRetrieve_withImageContentResponse_expectCorrectResponse() {
        this.messageAnswersRetrievalTypeStage =
                new MessageAnswersRetrievalTypeStage(LIST_MESSAGE_RESPONSE_WITH_IMAGE_CONTENT);

        assertEquals(
                MESSAGE_TEST_RESULT_WITHOUT_TEXT,
                this.messageAnswersRetrievalTypeStage.retrieve()
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestVariablesForEmptyMessageResultTestCase")
    void testRetrieve_withNotPresentMessageResponse_expectEmptyMessageResult(ListMessagesResponse listMessagesResponse) {
        this.messageAnswersRetrievalTypeStage =
                new MessageAnswersRetrievalTypeStage(listMessagesResponse);

        this.assertMessageResultIsEmpty();
    }

    @Test
    void testRetrieve_withEmptyMessageResponseContent_expectEmptyMessageResult() {
        this.messageAnswersRetrievalTypeStage =
                new MessageAnswersRetrievalTypeStage(LIST_MESSAGE_RESPONSE_WITH_EMPTY_CONTENT);

        this.assertMessageResultIsEmpty();
    }

    private void assertMessageResultIsEmpty() {
        assertEquals(
                MessageResult.empty(),
                this.messageAnswersRetrievalTypeStage.retrieve()
        );
    }
}