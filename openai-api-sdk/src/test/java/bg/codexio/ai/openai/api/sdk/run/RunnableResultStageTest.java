package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_ID;
import static bg.codexio.ai.openai.api.sdk.run.InternalAssertions.*;
import static bg.codexio.ai.openai.api.sdk.thread.InternalAssertions.THREAD_ID;
import static org.junit.jupiter.api.Assertions.*;

public class RunnableResultStageTest {

    private RunnableResultStage runnableResultStage;

    @BeforeEach
    void setUp() {
        this.runnableResultStage = new RunnableResultStage(
                RUNNABLE_HTTP_EXECUTOR,
                RunnableRequest.builder()
                               .withAssistantId(ASSISTANT_ID),
                THREAD_ID,
                RUNNABLE_RESPONSE
        );
    }

    @Test
    void testWaitForCompletionRaw_expectCorrectResponse() {
        executeWithPathVariables(this.runnableResultStage);
        var response = this.runnableResultStage.waitForCompletionRaw();

        assertEquals(
                RUNNABLE_COMPLETED_STATUS,
                response.status()
        );
    }

    @Test
    void testWaitForCompletionRaw_withRunnableResponse_expectCorrectResponse() {
        executeWithPathVariables(this.runnableResultStage);
        var response =
                this.runnableResultStage.waitForCompletionRaw(RUNNABLE_RESPONSE);

        assertEquals(
                RUNNABLE_COMPLETED_STATUS,
                response.status()
        );
    }

    @Test
    void testWaitForCompletion_expectCorrectBuilder() {
        executeWithPathVariables(this.runnableResultStage);
        var response = this.runnableResultStage.waitForCompletion();

        assertAll(
                () -> assertNotNull(response.httpExecutor),
                () -> assertNotNull(response.requestBuilder),
                () -> assertNotNull(response.threadId)
        );
    }

    @Test
    void testWaitForCompletion_withRunnableResponse_expectCorrectThreadId() {
        executeWithPathVariables(this.runnableResultStage);
        var response =
                this.runnableResultStage.waitForCompletion(RUNNABLE_RESPONSE);

        assertEquals(
                RUNNABLE_RESPONSE.threadId(),
                response
        );
    }

    @Test
    void testFrom_withRunnableId_expectCorrectResponse() {
        executeWithPathVariables(this.runnableResultStage);
        var response = this.runnableResultStage.from(RUNNABLE_ID);

        assertNotNull(response);
    }

    @Test
    void testFrom_withRunnableResponse_expectCorrectResponse() {
        executeWithPathVariables(this.runnableResultStage);
        var response = this.runnableResultStage.from(RUNNABLE_RESPONSE);

        assertNotNull(response);
    }

    @Test
    void testFrom_withRunnableResponseAndEmptyTools_expectCorrectResponse() {
        executeWithPathVariables(this.runnableResultStage);
        var response =
                this.runnableResultStage.from(RUNNABLE_RESPONSE_WITH_EMPTY_TOOLS);

        assertNotNull(response);
    }

    @Test
    void testFrom_withThreadIdAndRunnableId_expectCorrectResponse() {
        executeWithPathVariables(this.runnableResultStage);
        var response = this.runnableResultStage.from(
                THREAD_ID,
                RUNNABLE_ID
        );

        assertNotNull(response);
    }
}
