package bg.codexio.ai.openai.api.sdk.assistant;

import bg.codexio.ai.openai.api.payload.assistant.request.AssistantRequest;
import bg.codexio.ai.openai.api.payload.assistant.response.AssistantResponse;
import bg.codexio.ai.openai.api.payload.assistant.tool.CodeInterpreter;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static bg.codexio.ai.openai.api.sdk.AsyncCallbackUtils.mockExecution;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.OBJECT_MAPPER;
import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AssistantAsyncPromiseStageTest {

    public AssistantAsyncPromiseStage assistantAsyncPromiseStage;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        this.assistantAsyncPromiseStage = new AssistantAsyncPromiseStage(
                ASSISTANT_HTTP_EXECUTOR,
                AssistantRequest.builder()
                                .withModel(ASSISTANT_MODEL_TYPE.name())
                                .addTools(new CodeInterpreter())
                                .withName(ASSISTANT_NAME)
                                .withInstructions(ASSISTANT_INSTRUCTION)
        );

        mockExecution(
                ASSISTANT_HTTP_EXECUTOR,
                ASSISTANT_RESPONSE,
                OBJECT_MAPPER.writeValueAsString(ASSISTANT_RESPONSE)
        );
    }

    @Test
    public void testThen_withCompletableFuture_shouldBeCompletedNormally() {
        var future = this.assistantAsyncPromiseStage.then();

        assertTrue(future.isDone());
    }

    @Test
    public void testThen_withFinalizer_shouldBeInvokedCorrectMock() {
        var finalizer = spy(new AssistantConsumer());
        this.assistantAsyncPromiseStage.then(finalizer);

        verify(finalizer).accept(ASSISTANT_RESPONSE);
    }

    @Test
    public void testOnEachLine_withMockConsumer_shouldCallConsumerForEachLine() {
        var callBack = mock(Consumer.class);
        this.assistantAsyncPromiseStage.onEachLine(callBack);

        verify(
                callBack,
                times(2)
        ).accept(any());
    }

    @Test
    public void testThen_withOnEachLineAndFinalizer_shouldBeInvokedWithCorrectFileMockAndStringLines() {
        var callBack = mock(Consumer.class);
        var finalizer = spy(new AssistantConsumer());

        this.assistantAsyncPromiseStage.then(
                callBack,
                finalizer
        );

        assertAll(
                () -> verify(
                        callBack,
                        times(2)
                ).accept(any()),
                () -> verify(finalizer).accept(ASSISTANT_RESPONSE)
        );
    }
}

class AssistantConsumer
        implements Consumer<AssistantResponse> {

    @Override
    public void accept(AssistantResponse assistantResponse) {

    }
}
