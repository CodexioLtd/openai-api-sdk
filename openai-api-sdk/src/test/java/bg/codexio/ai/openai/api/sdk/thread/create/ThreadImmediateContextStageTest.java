package bg.codexio.ai.openai.api.sdk.thread.create;

import bg.codexio.ai.openai.api.payload.thread.request.ThreadCreationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static bg.codexio.ai.openai.api.sdk.CommonTestAssertions.THREAD_RESPONSE;
import static bg.codexio.ai.openai.api.sdk.thread.create.InternalAssertions.CREATE_THREAD_HTTP_EXECUTOR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ThreadImmediateContextStageTest {

    private ThreadImmediateContextStage threadImmediateContextStage;

    @BeforeEach
    void setUp() {
        this.threadImmediateContextStage = new ThreadImmediateContextStage(
                CREATE_THREAD_HTTP_EXECUTOR,
                ThreadCreationRequest.builder()
        );
    }

    @Test
    public void testFinishRaw_expectCorrectResponse() {
        this.mockThreadExecution();

        assertEquals(
                THREAD_RESPONSE,
                this.threadImmediateContextStage.finishRaw()
        );
    }

    @Test
    public void testFinish_expectCorrectResponse() {
        this.mockThreadExecution();

        assertEquals(
                THREAD_RESPONSE.id(),
                this.threadImmediateContextStage.finish()
        );
    }

    private void mockThreadExecution() {
        when(this.threadImmediateContextStage.httpExecutor.execute(any())).thenAnswer(res -> THREAD_RESPONSE);
    }
}
