package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_ID;
import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_RESPONSE;
import static bg.codexio.ai.openai.api.sdk.run.InternalAssertions.*;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class RunnableInitializationStageTest {

    private RunnableInitializationStage runnableInitializationStage;

    @BeforeEach
    void setUp() {
        this.runnableInitializationStage = new RunnableInitializationStage(
                RUNNABLE_HTTP_EXECUTOR,
                RunnableRequest.builder(),
                THREAD_ID
        );
    }

    @Test
    void testExecuteRaw_withAssistantResponse_expectCorrectResponse() {
        when(this.runnableInitializationStage.httpExecutor.executeWithPathVariable(
                any(),
                eq(THREAD_ID)
        )).thenAnswer(response -> RUNNABLE_RESPONSE);

        var response =
                this.runnableInitializationStage.executeRaw(ASSISTANT_RESPONSE);

        assertEquals(
                RUNNABLE_RESPONSE,
                response
        );
    }

    @Test
    void testExecuteRaw_withAssistantId_expectCorrectResponse() {
        when(this.runnableInitializationStage.httpExecutor.executeWithPathVariable(
                any(),
                eq(THREAD_ID)
        )).thenAnswer(response -> RUNNABLE_RESPONSE);

        var response =
                this.runnableInitializationStage.executeRaw(ASSISTANT_ID);

        assertEquals(
                RUNNABLE_RESPONSE,
                response
        );
    }

    @Test
    void testExecute_withAssistantResponse_expectCorrectResponse() {
        when(this.runnableInitializationStage.httpExecutor.executeWithPathVariable(
                any(),
                eq(THREAD_ID)
        )).thenAnswer(response -> RUNNABLE_RESPONSE);

        var response =
                this.runnableInitializationStage.execute(ASSISTANT_RESPONSE);

        assertEquals(
                RUNNABLE_ID,
                response
        );
    }

    @Test
    void testExecute_withAssistantId_expectCorrectResponse() {
        when(this.runnableInitializationStage.httpExecutor.executeWithPathVariable(
                any(),
                eq(THREAD_ID)
        )).thenAnswer(response -> RUNNABLE_RESPONSE);

        var response = this.runnableInitializationStage.execute(ASSISTANT_ID);

        assertEquals(
                RUNNABLE_ID,
                response
        );
    }

    @Test
    void testRun_withAssistantResponse_expectCorrectBuilder() {
        when(this.runnableInitializationStage.httpExecutor.executeWithPathVariable(
                any(),
                eq(THREAD_ID)
        )).thenAnswer(response -> RUNNABLE_RESPONSE);

        var nextStage =
                this.runnableInitializationStage.run(ASSISTANT_RESPONSE);

        assertEquals(
                ASSISTANT_ID,
                nextStage.requestBuilder.assistantId()
        );
    }
}