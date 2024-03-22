package bg.codexio.ai.openai.api.sdk.thread.create;

import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import bg.codexio.ai.openai.api.sdk.thread.ThreadConsumer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static bg.codexio.ai.openai.api.sdk.AsyncCallbackUtils.mockExecution;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.OBJECT_MAPPER;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_RESPONSE;
import static bg.codexio.ai.openai.api.sdk.thread.create.InternalAssertions.CREATE_THREAD_HTTP_EXECUTOR;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ThreadAsyncPromiseStageTest {

    private ThreadAsyncPromiseStage threadAsyncPromiseStage;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        this.threadAsyncPromiseStage = new ThreadAsyncPromiseStage(
                CREATE_THREAD_HTTP_EXECUTOR,
                ThreadCreationRequest.builder()
        );

        mockExecution(
                CREATE_THREAD_HTTP_EXECUTOR,
                THREAD_RESPONSE,
                OBJECT_MAPPER.writeValueAsString(THREAD_RESPONSE)
        );
    }

    @Test
    public void testThen_withFinalizer_shouldBeInvokedCorrectMock() {
        var finalizer = spy(new ThreadConsumer());
        this.threadAsyncPromiseStage.then(finalizer);

        verify(finalizer).accept(THREAD_RESPONSE);
    }

    @Test
    public void testOnEachLine_withMockConsumer_shouldCallConsumerForEachLine() {
        var callBack = mock(Consumer.class);
        this.threadAsyncPromiseStage.onEachLine(callBack);

        verify(
                callBack,
                times(2)
        ).accept(any());
    }

    @Test
    public void testThen_withOnEachLineAndFinalizer_shouldBeInvokedWithCorrectFileMockAndStringLines() {
        var callBack = mock(Consumer.class);
        var finalizer = spy(new ThreadConsumer());

        this.threadAsyncPromiseStage.then(
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

