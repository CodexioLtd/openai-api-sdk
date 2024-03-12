package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import bg.codexio.ai.openai.api.payload.run.response.RunnableResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static bg.codexio.ai.openai.api.sdk.AsyncCallbackUtils.mockAsyncExecutionWithPathVariable;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.OBJECT_MAPPER;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_ID;
import static bg.codexio.ai.openai.api.sdk.run.InternalAssertions.RUNNABLE_HTTP_EXECUTOR;
import static bg.codexio.ai.openai.api.sdk.run.InternalAssertions.RUNNABLE_RESPONSE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RunnableAsyncPromiseStageTest {

    private RunnableAsyncPromiseStage runnableAsyncPromiseStage;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        this.runnableAsyncPromiseStage = new RunnableAsyncPromiseStage(
                RUNNABLE_HTTP_EXECUTOR,
                RunnableRequest.builder()
                               .withAssistantId(ASSISTANT_ID),
                THREAD_ID
        );

        mockAsyncExecutionWithPathVariable(
                RUNNABLE_HTTP_EXECUTOR,
                RUNNABLE_RESPONSE,
                OBJECT_MAPPER.writeValueAsString(RUNNABLE_RESPONSE)
        );
    }

    @Test
    public void testThen_withFinalizer_shouldBeInvokedCorrectMock() {
        var finalizer = spy(new RunnableConsumer());
        this.runnableAsyncPromiseStage.then(finalizer);

        verify(finalizer).accept(RUNNABLE_RESPONSE);
    }

    @Test
    public void testOnEachLine_withMockConsumer_shouldCallConsumerForEachLine() {
        var callBack = mock(Consumer.class);
        this.runnableAsyncPromiseStage.onEachLine(callBack);

        verify(
                callBack,
                times(2)
        ).accept(any());
    }

    @Test
    public void testThen_withOnEachLineAndFinalizer_shouldBeInvokedWithCorrectFileMockAndStringLines() {
        var callBack = mock(Consumer.class);
        var finalizer = spy(new RunnableConsumer());

        this.runnableAsyncPromiseStage.then(
                callBack,
                finalizer
        );

        assertAll(
                () -> verify(
                        callBack,
                        times(2)
                ).accept(any()),
                () -> verify(finalizer).accept(RUNNABLE_RESPONSE)
        );
    }
}

class RunnableConsumer
        implements Consumer<RunnableResponse> {

    @Override
    public void accept(RunnableResponse runnableResponse) {

    }
}

