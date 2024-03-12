package bg.codexio.ai.openai.api.sdk.run;

import bg.codexio.ai.openai.api.payload.run.request.RunnableRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_ID;
import static bg.codexio.ai.openai.api.sdk.assistant.InternalAssertions.ASSISTANT_ID;
import static bg.codexio.ai.openai.api.sdk.run.InternalAssertions.RUNNABLE_HTTP_EXECUTOR;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RunnableAsyncContextStageTest {

    private RunnableAsyncContextStage runnableAsyncContextStage;

    @BeforeEach
    void setUp() {
        this.runnableAsyncContextStage = new RunnableAsyncContextStage(
                RUNNABLE_HTTP_EXECUTOR,
                RunnableRequest.builder()
                               .withAssistantId(ASSISTANT_ID),
                THREAD_ID
        );
    }

    @Test
    public void testExecute_expectCorrectBuilder() {
        assertNotNull(this.runnableAsyncContextStage.execute());
    }
}
