package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_ID;
import static bg.codexio.ai.openai.api.sdk.run.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RunnableImmediateContextStageTest {

    private RunnableImmediateContextStage runnableImmediateContextStage;

    @BeforeEach
    void setUp() {
        this.runnableImmediateContextStage = new RunnableImmediateContextStage(
                RUNNABLE_HTTP_EXECUTOR,
                RunnableRequest.builder()
                               .withAssistantId(ASSISTANT_ID),
                THREAD_ID
        );
    }

    @Test
    public void textExecuteRaw_expectCorrectResponse() {
        mockImmediateExecution(this.runnableImmediateContextStage);

        assertEquals(
                RUNNABLE_ID,
                this.runnableImmediateContextStage.executeRaw()
                                                  .id()
        );
    }

    @Test
    public void textExecute_expectCorrectResponse() {
        mockImmediateExecution(this.runnableImmediateContextStage);

        assertEquals(
                RUNNABLE_ID,
                this.runnableImmediateContextStage.execute()
        );
    }


    @Test
    public void testRun_expectCorrectBuilder() {
        mockImmediateExecution(this.runnableImmediateContextStage);

        assertNotNull(this.runnableImmediateContextStage.run());
    }

}
