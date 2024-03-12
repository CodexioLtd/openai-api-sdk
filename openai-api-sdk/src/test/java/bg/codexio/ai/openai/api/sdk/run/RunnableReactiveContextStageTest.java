package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_ID;
import static bg.codexio.ai.openai.api.sdk.run.InternalAssertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RunnableReactiveContextStageTest {

    private RunnableReactiveContextStage runnableReactiveContextStage;

    @BeforeEach
    void setUp() {
        this.runnableReactiveContextStage = new RunnableReactiveContextStage(
                RUNNABLE_HTTP_EXECUTOR,
                RunnableRequest.builder()
                               .withAssistantId(ASSISTANT_ID),
                THREAD_ID
        );
    }

    @Test
    public void testExecuteRaw_expectCorrectResponse() {
        mockReactiveExecution(this.runnableReactiveContextStage);

        assertEquals(
                RUNNABLE_ID,
                this.runnableReactiveContextStage.executeRaw()
                                                 .response()
                                                 .block()
                                                 .id()
        );
    }

    @Test
    public void testExecute_expectCorrectResponse() {
        mockReactiveExecution(this.runnableReactiveContextStage);

        assertEquals(
                RUNNABLE_ID,
                this.runnableReactiveContextStage.execute()
                                                 .block()
        );
    }
}