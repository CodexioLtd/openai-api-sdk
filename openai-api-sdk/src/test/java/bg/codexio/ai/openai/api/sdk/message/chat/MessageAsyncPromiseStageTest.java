package bg.codexio.ai.openai.api.sdk.message.chat;

import bg.codexio.ai.openai.api.payload.message.request.MessageRequest;
import bg.codexio.ai.openai.api.payload.message.response.MessageResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static bg.codexio.ai.openai.api.sdk.AsyncCallbackUtils.mockAsyncExecutionWithPathVariable;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.OBJECT_MAPPER;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.message.chat.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

public class MessageAsyncPromiseStageTest {

    private MessageAsyncPromiseStage messageAsyncPromiseStage;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        this.messageAsyncPromiseStage = new MessageAsyncPromiseStage(
                MESSAGE_HTTP_EXECUTOR,
                MessageRequest.builder()
                              .withContent(MESSAGE_CONTENT),
                THREAD_ID
        );

        mockAsyncExecutionWithPathVariable(
                MESSAGE_HTTP_EXECUTOR,
                MESSAGE_RESPONSE,
                OBJECT_MAPPER.writeValueAsString(MESSAGE_RESPONSE)
        );
    }

    @Test
    public void testThen_withFinalizer_shouldBeInvokedCorrectMock() {
        var finalizer = spy(new MessageConsumer());
        this.messageAsyncPromiseStage.then(finalizer);

        verify(finalizer).accept(MESSAGE_RESPONSE);
    }

    @Test
    public void testOnEachLine_withMockConsumer_shouldCallConsumerForEachLine() {
        var callBack = mock(Consumer.class);
        this.messageAsyncPromiseStage.onEachLine(callBack);

        verify(
                callBack,
                times(2)
        ).accept(any());
    }

    @Test
    public void testThen_withOnEachLineAndFinalizer_shouldBeInvokedWithCorrectFileMockAndStringLines() {
        var callBack = mock(Consumer.class);
        var finalizer = spy(new MessageConsumer());

        this.messageAsyncPromiseStage.then(
                callBack,
                finalizer
        );

        assertAll(
                () -> verify(
                        callBack,
                        times(2)
                ).accept(any()),
                () -> verify(finalizer).accept(MESSAGE_RESPONSE)
        );
    }
}

class MessageConsumer
        implements Consumer<MessageResponse> {

    @Override
    public void accept(MessageResponse messageResponse) {

    }
}
