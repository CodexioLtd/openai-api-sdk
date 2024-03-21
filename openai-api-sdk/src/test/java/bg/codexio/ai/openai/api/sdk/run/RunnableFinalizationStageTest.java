package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.http.OpenAIHttpExecutor;
import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import bg.codexio.ai.openai.api.sdk.message.Messages;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static bg.codexio.ai.openai.api.sdk.AsyncCallbackUtils.*;
import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.*;
import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_ID;
import static bg.codexio.ai.openai.api.sdk.run.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RunnableFinalizationStageTest {

    private RunnableFinalizationStage runnableFinalizationStage;

    @BeforeEach
    void setUp() {
        this.runnableFinalizationStage = new RunnableFinalizationStage(
                RUNNABLE_HTTP_EXECUTOR,
                RunnableRequest.builder()
                               .withAssistantId(ASSISTANT_ID),
                THREAD_ID
        );
    }

    @Test
    public void testImmediate_expectCorrectBuilder() {
        mockImmediateExecution(this.runnableFinalizationStage);

        var nextStage = this.runnableFinalizationStage.immediate();

        assertEquals(
                THREAD_ID,
                nextStage.threadId
        );
    }

    @Test
    public void testAsync_expectCorrectBuilder()
            throws JsonProcessingException {
        var callback = prepareCallback(RunnableResultStage.class);

        mockAsyncExecutionWithPathVariable(
                RUNNABLE_HTTP_EXECUTOR,
                RUNNABLE_RESPONSE,
                OBJECT_MAPPER.writeValueAsString(RUNNABLE_RESPONSE)
        );

        this.runnableFinalizationStage.async(callback.callback());

        assertNotNull(callback.data());
    }

    @Test
    public void testAsyncSimply_expectCorrectResult()
            throws JsonProcessingException {
        var callback = prepareCallback(String.class);

        mockAsyncExecutionWithPathVariable(
                RUNNABLE_HTTP_EXECUTOR,
                RUNNABLE_RESPONSE_WITH_COMPLETED_STATUS,
                OBJECT_MAPPER.writeValueAsString(RUNNABLE_RESPONSE)
        );
        this.mockRunnableStages();

        try (var mockedMessage = mockStatic(Messages.class)) {
            var retrieveListMessagesHttpExecutor =
                    mockMessageProcessing(mockedMessage);

            mockAsyncExecutionWithPathVariables(
                    retrieveListMessagesHttpExecutor,
                    LIST_MESSAGE_RESPONSE_WITH_TEXT_CONTENT_WITH_FILE_CITATION,
                    OBJECT_MAPPER.writeValueAsString(LIST_MESSAGE_RESPONSE_WITH_TEXT_CONTENT_WITH_FILE_CITATION)
            );

            this.runnableFinalizationStage.asyncSimply(
                    FILE,
                    callback.callback()
            );

            assertNotNull(callback.data());
        }
    }

    @Test
    public void testReactive_expectCorrectResponse() {
        mockReactiveExecution(this.runnableFinalizationStage);

        assertEquals(
                RUNNABLE_RESPONSE,
                this.runnableFinalizationStage.reactive()
                                              .block()
        );
    }

    @Test
    public void testReactiveSimply_expectCorrectResponse() {
        when(this.runnableFinalizationStage.httpExecutor.reactive()
                                                        .executeWithPathVariable(
                any(),
                any()
        )).thenAnswer(res -> new OpenAIHttpExecutor.ReactiveExecution<>(
                Flux.empty(),
                Mono.just(RUNNABLE_RESPONSE_WITH_COMPLETED_STATUS)
        ));
        this.mockRunnableStages();

        try (var mockedMessage = mockStatic(Messages.class)) {
            var retrieveListMessagesHttpExecutor =
                    mockMessageProcessing(mockedMessage);

            when(retrieveListMessagesHttpExecutor.retrieveReactive(any())).thenAnswer(res -> new OpenAIHttpExecutor.ReactiveExecution<>(
                    Flux.empty(),
                    Mono.just(LIST_MESSAGE_RESPONSE_WITH_TEXT_CONTENT_WITH_FILE_CITATION)
            ));

            assertEquals(
                    MESSAGE_TEST_RESULT_WITHOUT_IMAGE.message(),
                    this.runnableFinalizationStage.reactiveSimply(FILE)
                                                  .block()
            );
        }
    }

    private void mockRunnableStages() {
        var runnableResultStageMock = mock(RunnableResultStage.class);
        var runnableAdvancedConfigMock =
                mock(RunnableAdvancedConfigurationStage.class);
        var runnableMessageResultStage = new RunnableMessageResult(
                RUNNABLE_HTTP_EXECUTOR,
                RunnableRequest.builder()
                               .withAssistantId(ASSISTANT_ID),
                THREAD_ID
        );
        when(runnableResultStageMock.waitForCompletion()).thenAnswer(res -> runnableResultStageMock);
        when(runnableAdvancedConfigMock.result()).thenAnswer(res -> runnableMessageResultStage);
    }
}
