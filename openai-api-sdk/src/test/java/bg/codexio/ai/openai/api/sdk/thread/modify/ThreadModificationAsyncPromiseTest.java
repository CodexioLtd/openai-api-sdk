package bg.codexio.ai.openai.api.sdk.thread.modify;

import bg.codexio.ai.openai.api.payload.thread.request.ThreadModificationRequest;
import bg.codexio.ai.openai.api.payload.thread.response.ThreadResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static bg.codexio.ai.openai.api.sdk.AsyncCallbackUtils.mockAsyncExecutionWithPathVariable;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.*;
import static bg.codexio.ai.openai.api.sdk.thread.modify.InternalAssertions.MODIFY_THREAD_HTTP_EXECUTOR;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ThreadModificationAsyncPromiseTest {

    private ThreadModificationAsyncPromiseStage threadModificationAsyncPromiseStage;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        this.threadModificationAsyncPromiseStage =
                new ThreadModificationAsyncPromiseStage(
                MODIFY_THREAD_HTTP_EXECUTOR,
                ThreadModificationRequest.builder(),
                THREAD_ID
        );

        mockAsyncExecutionWithPathVariable(
                MODIFY_THREAD_HTTP_EXECUTOR,
                THREAD_RESPONSE,
                OBJECT_MAPPER.writeValueAsString(THREAD_RESPONSE)
        );
    }

    @Test
    public void testThen_withFinalizer_shouldBeInvokedCorrectMock() {
        var finalizer = spy(new ThreadConsumer());
        this.threadModificationAsyncPromiseStage.then(finalizer);

        verify(finalizer).accept(THREAD_RESPONSE);
    }

    @Test
    public void testOnEachLine_withMockConsumer_shouldCallConsumerForEachLine() {
        var callBack = mock(Consumer.class);
        this.threadModificationAsyncPromiseStage.onEachLine(callBack);

        verify(
                callBack,
                times(2)
        ).accept(any());
    }

    @Test
    public void testThen_withOnEachLineAndFinalizer_shouldBeInvokedWithCorrectFileMockAndStringLines() {
        var callBack = mock(Consumer.class);
        var finalizer = spy(new ThreadConsumer());

        this.threadModificationAsyncPromiseStage.then(
                callBack,
                finalizer
        );

        assertAll(
                () -> verify(
                        callBack,
                        times(2)
                ).accept(any()),
                () -> verify(finalizer).accept(THREAD_RESPONSE)
        );
    }
}

class ThreadConsumer
        implements Consumer<ThreadResponse> {

    @Override
    public void accept(ThreadResponse threadResponse) {

    }
}