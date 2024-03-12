package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.http.OpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import bg.codexio.ai.openai.api.sdk.message.MessageResult;
import bg.codexio.ai.openai.api.sdk.message.Messages;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static bg.codexio.ai.openai.api.sdk.AsyncCallbackUtils.mockAsyncExecutionWithPathVariables;
import static bg.codexio.ai.openai.api.sdk.AsyncCallbackUtils.prepareCallback;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.*;
import static bg.codexio.ai.openai.api.sdk.run.InternalAssertions.RUNNABLE_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.run.InternalAssertions.mockMessageProcessing;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class RunnableMessageResultTest {

    private RunnableMessageResult runnableMessageResult;

    @BeforeEach
    void setUp() {
        this.runnableMessageResult = new RunnableMessageResult(
                RUNNABLE_HTTP_EXECUTOR,
                RunnableRequest.builder(),
                THREAD_ID
        );
    }

    @Test
    public void testAnswersImmediate_expectCorrectResponse() {
        try (var mockedMessage = mockStatic(Messages.class)) {
            var retrieveMessageHttpExecutorMock =
                    mockMessageProcessing(mockedMessage);

            when(retrieveMessageHttpExecutorMock.executeWithPathVariables(any())).thenAnswer(res -> LIST_MESSAGE_RESPONSE_WITH_TEXT_CONTENT_WITH_FILE_CITATION);

            this.performMessageAssertion(this.runnableMessageResult.answersImmediate());
        }
    }

    @Test
    public void testAnswersAsync_expectCorrectResponse() {
        var callback = prepareCallback(MessageResult.class);
        try (var mockedMessage = mockStatic(Messages.class)) {
            var retrieveListMessagesHttpExecutor =
                    mockMessageProcessing(mockedMessage);

            mockAsyncExecutionWithPathVariables(
                    retrieveListMessagesHttpExecutor,
                    LIST_MESSAGE_RESPONSE_WITH_TEXT_CONTENT_WITH_FILE_CITATION,
                    OBJECT_MAPPER.writeValueAsString(LIST_MESSAGE_RESPONSE_WITH_TEXT_CONTENT_WITH_FILE_CITATION)
            );

            this.runnableMessageResult.answersAsync(callback.callback());

            this.performMessageAssertion(callback.data());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAnswerAsyncSimply_expectCorrectResponse() {
        var consumer = prepareCallback(String.class);

        try (var mockedMessage = mockStatic(Messages.class)) {
            var retrieveListMessagesHttpExecutor =
                    mockMessageProcessing(mockedMessage);

            mockAsyncExecutionWithPathVariables(
                    retrieveListMessagesHttpExecutor,
                    LIST_MESSAGE_RESPONSE_WITH_TEXT_CONTENT_WITH_FILE_CITATION,
                    OBJECT_MAPPER.writeValueAsString(LIST_MESSAGE_RESPONSE_WITH_TEXT_CONTENT_WITH_FILE_CITATION)
            );
            this.runnableMessageResult.answersAsyncSimply(
                    FILE,
                    consumer.callback()
            );

            assertNotNull(consumer.data());

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAnswersReactive_expectCorrectResponse() {
        try (var mockedMessage = mockStatic(Messages.class)) {
            var retrieveListMessagesHttpExecutor =
                    mockMessageProcessing(mockedMessage);

            when(retrieveListMessagesHttpExecutor.executeReactiveWithPathVariables(any())).thenAnswer(res -> new OpenAIHttpExecutor.ReactiveExecution<>(
                    Flux.empty(),
                    Mono.just(LIST_MESSAGE_RESPONSE_WITH_TEXT_CONTENT_WITH_FILE_CITATION)
            ));

            this.performMessageAssertion(this.runnableMessageResult.answersReactive()
                                                                   .block());
        }
    }

    @Test
    public void testAnswersReactiveSimply_expectCorrectResponse() {
        try (var mockedMessage = mockStatic(Messages.class)) {
            var retrieveListMessagesHttpExecutor =
                    mockMessageProcessing(mockedMessage);

            when(retrieveListMessagesHttpExecutor.executeReactiveWithPathVariables(any())).thenAnswer(res -> new OpenAIHttpExecutor.ReactiveExecution<>(
                    Flux.empty(),
                    Mono.just(LIST_MESSAGE_RESPONSE_WITH_TEXT_CONTENT_WITH_FILE_CITATION)
            ));

            assertNotNull(this.runnableMessageResult.answersReactiveSimply(FILE)
                                                    .block());
        }
    }


    private void performMessageAssertion(MessageResult actual) {
        assertEquals(
                MESSAGE_TEST_RESULT_WITHOUT_IMAGE,
                actual
        );
    }
}
