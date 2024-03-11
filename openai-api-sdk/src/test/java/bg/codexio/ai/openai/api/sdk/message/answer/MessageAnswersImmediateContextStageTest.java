package bg.codexio.ai.openai.api.sdk.message.answer;

import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import bg.codexio.ai.openai.api.sdk.message.exception.NonPresentThreadIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static bg.codexio.ai.openai.api.sdk.message.answer.InternalAssertions.LIST_MESSAGE_RESPONSE;
import static bg.codexio.ai.openai.api.sdk.message.answer.InternalAssertions.RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_RESPONSE;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MessageAnswersImmediateContextStageTest {

    private MessageAnswersImmediateContextStage messageAnswersImmediateContextStage;

    private static Stream<Arguments> provideTestVariables() {
        return Stream.of(
                Arguments.of(THREAD_ID),
                Arguments.of(THREAD_RESPONSE)
        );
    }

    @BeforeEach
    void setUp() {
        this.messageAnswersImmediateContextStage =
                new MessageAnswersImmediateContextStage(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                THREAD_ID
        );
    }

    @Test
    public void testAnswers_expectCorrectResponse() {
        when(this.messageAnswersImmediateContextStage.httpExecutor.executeWithPathVariables(any())).thenAnswer(res -> LIST_MESSAGE_RESPONSE);
        assertNotNull(this.messageAnswersImmediateContextStage.answers());
    }

    @Test
    public void testAnswers_withNotPresentThreadId_expectNonPresentThreadIdException() {
        this.messageAnswersImmediateContextStage =
                new MessageAnswersImmediateContextStage(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                null
        );

        assertThrows(
                NonPresentThreadIdException.class,
                () -> this.messageAnswersImmediateContextStage.answers()
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestVariables")
    public void testAnswers_withThreadIdentifier_expectCorrectResponse(Object threadIdentifier) {
        when(this.messageAnswersImmediateContextStage.httpExecutor.executeWithPathVariables(any())).thenAnswer(res -> LIST_MESSAGE_RESPONSE);

        if (threadIdentifier instanceof String threadId) {
            assertNotNull(this.messageAnswersImmediateContextStage.answers(threadId));
        } else if (threadIdentifier instanceof ThreadResponse threadResponse) {
            assertNotNull(this.messageAnswersImmediateContextStage.answers(threadResponse));
        }
    }
}
