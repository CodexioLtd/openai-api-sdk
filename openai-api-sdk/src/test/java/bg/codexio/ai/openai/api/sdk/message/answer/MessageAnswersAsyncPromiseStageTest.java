package bg.codexio.ai.openai.api.sdk.message.answer;

import bg.codexio.ai.openai.api.payload.message.response.ListMessagesResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static bg.codexio.ai.openai.api.sdk.AsyncCallbackUtils.mockAsyncWithPathVariablesExecution;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.OBJECT_MAPPER;
import static bg.codexio.ai.openai.api.sdk.message.answer.InternalAssertions.LIST_MESSAGE_RESPONSE;
import static bg.codexio.ai.openai.api.sdk.message.answer.InternalAssertions.RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_ID;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MessageAnswersAsyncPromiseStageTest {

    public MessageAnswersAsyncPromiseStage messageAnswersAsyncPromiseStage;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        this.messageAnswersAsyncPromiseStage =
                new MessageAnswersAsyncPromiseStage(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                THREAD_ID
        );

        mockAsyncWithPathVariablesExecution(
                RETRIEVE_LIST_MESSAGES_HTTP_EXECUTOR,
                LIST_MESSAGE_RESPONSE,
                OBJECT_MAPPER.writeValueAsString(LIST_MESSAGE_RESPONSE)
        );
    }

    @Test
    public void testThen_withFinalizer_shouldBeInvokedCorrectMock() {
        var finalizer = spy(new ListMessagesConsumer());
        this.messageAnswersAsyncPromiseStage.then(finalizer);

        verify(finalizer).accept(LIST_MESSAGE_RESPONSE);
    }

    @Test
    public void testOnEachLine_withMockConsumer_shouldCallConsumerForEachLine() {
        var callBack = mock(Consumer.class);
        this.messageAnswersAsyncPromiseStage.onEachLine(callBack);

        verify(
                callBack,
                times(2)
        ).accept(any());
    }

    @Test
    public void testThen_withOnEachLineAndFinalizer_shouldBeInvokedWithCorrectFileMockAndStringLines() {
        var callBack = mock(Consumer.class);
        var finalizer = spy(new ListMessagesConsumer());

        this.messageAnswersAsyncPromiseStage.then(
                callBack,
                finalizer
        );

        assertAll(
                () -> verify(
                        callBack,
                        times(2)
                ).accept(any()),
                () -> verify(finalizer).accept(LIST_MESSAGE_RESPONSE)
        );
    }
}

class ListMessagesConsumer
        implements Consumer<ListMessagesResponse> {

    @Override
    public void accept(ListMessagesResponse messageResponse) {

    }
}